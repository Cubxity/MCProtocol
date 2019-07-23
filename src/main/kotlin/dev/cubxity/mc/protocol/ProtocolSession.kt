package dev.cubxity.mc.protocol

import com.github.steveice10.mc.auth.data.GameProfile
import com.github.steveice10.mc.auth.service.SessionService
import dev.cubxity.mc.protocol.dsl.msg
import dev.cubxity.mc.protocol.events.*
import dev.cubxity.mc.protocol.net.PacketEncryption
import dev.cubxity.mc.protocol.net.pipeline.TcpPacketCompression
import dev.cubxity.mc.protocol.packets.Packet
import dev.cubxity.mc.protocol.packets.PassthroughPacket
import dev.cubxity.mc.protocol.packets.game.server.ServerDisconnectPacket
import dev.cubxity.mc.protocol.packets.game.server.ServerKeepAlivePacket
import dev.cubxity.mc.protocol.packets.game.server.block.ServerBlockActionPacket
import dev.cubxity.mc.protocol.packets.game.server.block.ServerBlockChangePacket
import dev.cubxity.mc.protocol.packets.game.server.block.ServerMultiBlockChangePacket
import dev.cubxity.mc.protocol.packets.game.server.entity.animation.ServerAnimationPacket
import dev.cubxity.mc.protocol.packets.game.server.entity.animation.ServerBlockBreakAnimationPacket
import dev.cubxity.mc.protocol.packets.game.server.entity.spawn.*
import dev.cubxity.mc.protocol.packets.game.server.misc.ServerBossBarPacket
import dev.cubxity.mc.protocol.packets.game.server.player.ServerChatMessagePacket
import dev.cubxity.mc.protocol.packets.game.server.state.ServerDifficultyPacket
import dev.cubxity.mc.protocol.packets.handshake.client.HandshakePacket
import dev.cubxity.mc.protocol.packets.login.client.EncryptionResponsePacket
import dev.cubxity.mc.protocol.packets.login.client.LoginPluginResponsePacket
import dev.cubxity.mc.protocol.packets.login.client.LoginStartPacket
import dev.cubxity.mc.protocol.packets.login.server.*
import dev.cubxity.mc.protocol.packets.status.client.StatusPingPacket
import dev.cubxity.mc.protocol.packets.status.client.StatusQueryPacket
import dev.cubxity.mc.protocol.packets.status.server.StatusPongPacket
import dev.cubxity.mc.protocol.packets.status.server.StatusResponsePacket
import dev.cubxity.mc.protocol.utils.CryptUtil
import io.netty.channel.Channel
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import kotlinx.coroutines.*
import org.objenesis.ObjenesisStd
import org.slf4j.LoggerFactory
import reactor.core.publisher.EmitterProcessor
import reactor.core.publisher.FluxSink
import reactor.core.scheduler.Schedulers
import java.math.BigInteger
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList

/**
 * The main juice
 * @author Cubxity
 * @since 7/20/2019
 */
class ProtocolSession @JvmOverloads constructor(
    val side: Side,
    val channel: Channel,
    val incomingVersion: ProtocolVersion = ProtocolVersion.V1_14_4,
    val outgoingVersion: ProtocolVersion = ProtocolVersion.V1_14_4
) : SimpleChannelInboundHandler<Packet>(), CoroutineScope {

    override val coroutineContext = Dispatchers.Default + Job()

    private val objenesis = ObjenesisStd()

    val logger = LoggerFactory.getLogger(this::class.java)

    /**
     * Packet encryption
     * https://wiki.vg/Protocol_Encryption
     */
    var encryption: PacketEncryption? = null

    /**
     * Protocol compression
     * https://wiki.vg/Protocol#With_compression
     */
    var compressionThreshold: Int? = null
        set(value) {
            if (subProtocol == SubProtocol.LOGIN)
                with(channel.pipeline()) {
                    if (value != null) {
                        if (get("compression") == null)
                            addBefore("codec", "compression", TcpPacketCompression(this@ProtocolSession))
                    } else if (get("compression") != null)
                        remove("compression")
                }
            field = value
        }

    /**
     * If the server and client default handler should skip auth
     */
    var offline: Boolean = false

    /**
     * Session username
     */
    lateinit var username: String

    /**
     * Session profile
     */
    lateinit var profile: GameProfile

    var keyPair = CryptUtil.generateKeyPair()

    /**
     * Map of registered incoming packets
     */
    val incomingPackets = mutableMapOf<Int, Class<out Packet>>()

    /**
     * Map of registered outgoing packets
     */
    val outgoingPackets = mutableMapOf<Int, Class<out Packet>>()

    /**
     * Sub protocol state
     * - https://wiki.vg/Protocol#Handshaking
     * - https://wiki.vg/Protocol#Status
     * - https://wiki.vg/Protocol#Login
     * - https://wiki.vg/Protocol#Play
     */
    var subProtocol = SubProtocol.HANDSHAKE

    val processor = EmitterProcessor.create<Event>()
    val scheduler = Schedulers.newSingle("Protocol-PacketManager", true)
    val sink = processor.sink(FluxSink.OverflowStrategy.BUFFER)

    /**
     * Do not use this unless it's required
     * The listeners will be called in the thread that [channelRead0] is called from
     * @see on
     */
    val syncListeners = CopyOnWriteArrayList<(Packet) -> Unit>()

    /**
     * Applies all default settings
     */
    fun applyDefaults() {
        registerDefaults()
        when (side) {
            Side.CLIENT -> defaultClientHandler()
            Side.SERVER -> defaultServerHandler()
        }
    }

    fun defaultServerHandler() {
        val verifyToken = ByteArray(4)
        Random().nextBytes(verifyToken)
        syncListeners += {
            when (it) {
                is HandshakePacket -> {
                    subProtocol = when (it.intent) {
                        HandshakePacket.Intent.LOGIN -> SubProtocol.LOGIN
                        HandshakePacket.Intent.STATUS -> SubProtocol.STATUS
                    }
                    registerDefaults()
                }
                is LoginStartPacket -> send(EncryptionRequestPacket("", keyPair.public, verifyToken))
                is EncryptionResponsePacket -> {
                    if (!Arrays.equals(it.getVerifyToken(keyPair.private), verifyToken)) {
                        disconnect("Invalid nonce")
                        channel.disconnect()
                    } else
                        if (offline) {
                            encryption = PacketEncryption(it.getSecretKey(keyPair.private))
                        } else {
                            launch {
                                try {
                                    val profile = SessionService().getProfileByServer(
                                        username,
                                        BigInteger(
                                            CryptUtil.getServerIdHash(
                                                "",
                                                keyPair.public,
                                                it.getSecretKey(keyPair.private)
                                            )
                                        ).toString(16)
                                    )
                                    if (profile == null)
                                        disconnect("Failed to verify username.")
                                    else {
                                        if (compressionThreshold != null) {
                                            compressionThreshold = compressionThreshold
                                            // This will add the compression stuff to the pipeline
                                        }
                                        this@ProtocolSession.profile = profile
                                        send(LoginSuccessPacket(profile.id.toString(), profile.name))
                                        launch {
                                            var lastPing: Long
                                            while (channel.isOpen) {
                                                lastPing = System.currentTimeMillis()
                                                send(ServerKeepAlivePacket(lastPing))
                                                delay(2000)
                                            }
                                        }
                                    }
                                } catch (ex: Exception) {
                                    disconnect("Failed to authenticate")
                                }
                            }
                        }
                }
//                is SetCompressionPacket -> compressionThreshold = it.threshold server sends these smh
//                is LoginSuccessPacket -> subProtocol = SubProtocol.GAME
            }
        }
    }

    fun defaultClientHandler() {
        val addr = channel.remoteAddress()

    }

    /**
     * Registers default packets for current [subProtocol]
     * @param clear to clear current registered packets in [incomingPackets] and [outgoingPackets]
     */
    @JvmOverloads
    fun registerDefaults(clear: Boolean = true) {
        if (clear) {
            incomingPackets.clear()
            outgoingPackets.clear()
        }
        // from client
        val client = when (side) {
            Side.CLIENT -> outgoingPackets
            Side.SERVER -> incomingPackets
        }
        // from server
        val server = when (side) {
            Side.CLIENT -> incomingPackets
            Side.SERVER -> outgoingPackets
        }

        when (subProtocol) {
            SubProtocol.HANDSHAKE -> client[0x00] = HandshakePacket::class.java
            SubProtocol.STATUS -> {
                server[0x00] = StatusResponsePacket::class.java
                server[0x01] = StatusPongPacket::class.java

                client[0x00] = StatusQueryPacket::class.java
                client[0x01] = StatusPingPacket::class.java
            }
            SubProtocol.LOGIN -> {
                server[0x00] = LoginDisconnectPacket::class.java
                server[0x01] = EncryptionRequestPacket::class.java
                server[0x02] = LoginSuccessPacket::class.java
                server[0x03] = SetCompressionPacket::class.java
                server[0x04] = LoginPluginRequestPacket::class.java

                client[0x00] = LoginStartPacket::class.java
                client[0x01] = EncryptionResponsePacket::class.java
                client[0x02] = LoginPluginResponsePacket::class.java
            }
            SubProtocol.GAME -> {
                server[0x00] = ServerSpawnObjectPacket::class.java
                server[0x01] = ServerSpawnExperienceOrbPacket::class.java
                server[0x02] = ServerSpawnGlobalEntityPacket::class.java
                server[0x03] = ServerSpawnMobPacket::class.java
                server[0x04] = ServerSpawnPaintingPacket::class.java
                server[0x05] = ServerSpawnPlayerPacket::class.java

                server[0x06] = ServerAnimationPacket::class.java
                // TODO: Statistics packet
                server[0x08] = ServerBlockBreakAnimationPacket::class.java
                // TODO: Update Block Entity packet
                server[0x0A] = ServerBlockActionPacket::class.java
                server[0x0B] = ServerBlockChangePacket::class.java

                server[0x0C] = ServerBossBarPacket::class.java
                server[0x0D] = ServerDifficultyPacket::class.java
                server[0x0E] = ServerChatMessagePacket::class.java
                server[0x0F] = ServerMultiBlockChangePacket::class.java

                server[0x1B] = ServerDisconnectPacket::class.java
                server[0x21] = ServerKeepAlivePacket::class.java
            }
        }
    }

    /**
     * Prints every packet received
     * This is used for debugging
     * NOTE: [logger]'s level is required to be at DEBUG
     */
    fun wiretap(): ProtocolSession {
        on<PacketReceivedEvent<Packet>>()
            .subscribe { logger.debug("[$side]: ${it.javaClass.simpleName}") }
        return this
    }

    inline fun <reified T : Event> on() =
        processor.publishOn(scheduler)
            .ofType(T::class.java)

    fun createOutgoingPacketById(id: Int): Packet {
        val p = outgoingPackets[id] ?: return PassthroughPacket(id)
        return objenesis
            .getInstantiatorOf(p)
            .newInstance()
    }

    fun createIncomingPacketById(id: Int): Packet {
        val p = incomingPackets[id] ?: return PassthroughPacket(id)
        return objenesis
            .getInstantiatorOf(p)
            .newInstance()
    }

    fun getOutgoingId(packet: Packet) =
        outgoingPackets.keys.elementAtOrElse(outgoingPackets.values.indexOf(packet::class.java)) { (packet as? PassthroughPacket)?.id }

    fun getIncomingId(packet: Packet) =
        incomingPackets.keys.elementAtOrElse(incomingPackets.values.indexOf(packet::class.java)) { (packet as? PassthroughPacket)?.id }

    fun send(packet: Packet) {
        val e = PacketSendingEvent(packet)
        sink.next(e)
        //TODO: Fix the issue with this being non-blocking
        if (!e.isCancelled)
            channel.writeAndFlush(packet)
                .addListener { sink.next(PacketSentEvent(packet)) }
    }

    fun disconnect(reason: String) {
        if (channel.isOpen) {
            val m = msg(reason)
            send(if (subProtocol == SubProtocol.LOGIN) LoginDisconnectPacket(m) else ServerDisconnectPacket(m))
            channel.disconnect()
        }
    }

    override fun channelActive(ctx: ChannelHandlerContext) {
        sink.next(ConnectedEvent(ctx))
    }

    override fun channelInactive(ctx: ChannelHandlerContext) {
        sink.next(DisconnectedEvent(ctx))
    }

    override fun channelRead0(ctx: ChannelHandlerContext, packet: Packet) {
        syncListeners.forEach { it(packet) }
        sink.next(PacketReceivedEvent(packet))
    }

    enum class Side {
        CLIENT,
        SERVER
    }

    enum class SubProtocol {
        HANDSHAKE,
        STATUS,
        LOGIN,
        GAME
    }
}