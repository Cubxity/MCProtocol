package dev.cubxity.mc.protocol.packets.versions

import dev.cubxity.mc.protocol.packets.Packet
import dev.cubxity.mc.protocol.packets.PacketVersion
import dev.cubxity.mc.protocol.packets.game.client.ClientChatMessagePacket
import dev.cubxity.mc.protocol.packets.game.client.ClientKeepAlivePacket
import dev.cubxity.mc.protocol.packets.game.client.ClientStatusPacket
import dev.cubxity.mc.protocol.packets.game.server.*
import dev.cubxity.mc.protocol.packets.game.server.entity.player.ServerPlayerAbilitiesPacket
import dev.cubxity.mc.protocol.packets.game.client.player.ClientPlayerPacket
import dev.cubxity.mc.protocol.packets.game.server.entity.*
import dev.cubxity.mc.protocol.packets.game.server.entity.player.ServerPlayerPositionLookPacket
import dev.cubxity.mc.protocol.packets.game.server.entity.player.ServerSetExperiencePacket
import dev.cubxity.mc.protocol.packets.game.server.entity.player.ServerUpdateHealthPacket
import dev.cubxity.mc.protocol.packets.game.server.entity.spawn.*
import dev.cubxity.mc.protocol.packets.game.server.world.ServerSpawnPositionPacket
import dev.cubxity.mc.protocol.packets.game.server.world.ServerTimeUpdatePacket
import dev.cubxity.mc.protocol.packets.game.server.world.block.ServerBlockChangePacket
import dev.cubxity.mc.protocol.packets.game.server.world.block.ServerMultiBlockChangePacket
import dev.cubxity.mc.protocol.packets.login.client.EncryptionResponsePacket
import dev.cubxity.mc.protocol.packets.login.client.LoginPluginResponsePacket
import dev.cubxity.mc.protocol.packets.login.client.LoginStartPacket
import dev.cubxity.mc.protocol.packets.login.server.*

class PacketVersion_1_14_4 : PacketVersion {

    override val clientPlay: Map<Int, Class<out Packet>> = mapOf(
        0x03 to ClientChatMessagePacket::class.java,
        0x04 to ClientStatusPacket::class.java,
        0x0F to ClientKeepAlivePacket::class.java,
        0x14 to ClientPlayerPacket::class.java
    )

    override val serverPlay: Map<Int, Class<out Packet>> = mapOf(
        0x00 to ServerSpawnObjectPacket::class.java,
        0x01 to ServerSpawnExperienceOrbPacket::class.java,
        0x02 to ServerSpawnGlobalEntityPacket::class.java,
        0x03 to ServerSpawnMobPacket::class.java,
        0x04 to ServerSpawnPaintingPacket::class.java,
        0x05 to ServerSpawnPlayerPacket::class.java,
        0x0B to ServerBlockChangePacket::class.java,
        0x0D to ServerDifficultyPacket::class.java,
        0x0E to ServerChatPacket::class.java,
        0x0F to ServerMultiBlockChangePacket::class.java,
        0x10 to ServerTabCompletePacket::class.java,
        0x1A to ServerDisconnectPacket::class.java,
        0x18 to ServerPluginMessagePacket::class.java,
        0x20 to ServerKeepAlivePacket::class.java,
        0x28 to ServerEntityRelativeMovePacket::class.java,
        0x29 to ServerEntityLookAndRelativeMovePacket::class.java,
        0x2A to ServerEntityLookPacket::class.java,
        0x25 to ServerJoinGamePacket::class.java,
        0x31 to ServerPlayerAbilitiesPacket::class.java,
        0x35 to ServerPlayerPositionLookPacket::class.java,
        0x37 to ServerDestroyEntitiesPacket::class.java,
        0x3B to ServerEntityHeadLookPacket::class.java,
        0x43 to ServerEntityMetadataPacket::class.java,
        0x45 to ServerEntityVelocityPacket::class.java,
        0x47 to ServerSetExperiencePacket::class.java,
        0x48 to ServerUpdateHealthPacket::class.java,
        0x4D to ServerSpawnPositionPacket::class.java,
        0x4E to ServerTimeUpdatePacket::class.java,
        0x56 to ServerEntityTeleportPacket::class.java
    )

    override val clientLogin: Map<Int, Class<out Packet>> = mapOf(
        0x00 to LoginStartPacket::class.java,
        0x01 to EncryptionResponsePacket::class.java,
        0x02 to LoginPluginResponsePacket::class.java
    )

    override val serverLogin: Map<Int, Class<out Packet>> = mapOf(
        0x00 to LoginDisconnectPacket::class.java,
        0x01 to EncryptionRequestPacket::class.java,
        0x02 to LoginSuccessPacket::class.java,
        0x03 to SetCompressionPacket::class.java,
        0x04 to LoginPluginRequestPacket::class.java
    )
}