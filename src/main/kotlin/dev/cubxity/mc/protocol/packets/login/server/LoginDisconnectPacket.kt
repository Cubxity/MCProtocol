package dev.cubxity.mc.protocol.packets.login.server

import dev.cubxity.mc.protocol.ProtocolVersion
import dev.cubxity.mc.protocol.entities.Message
import dev.cubxity.mc.protocol.net.NetInput
import dev.cubxity.mc.protocol.net.NetOutput
import dev.cubxity.mc.protocol.packets.Packet

/**
 * @author Cubxity
 * @since 7/21/2019
 */
class LoginDisconnectPacket(var chat: Message) : Packet() {
    //TODO: Serializing into message object

    override fun read(buf: NetInput, target: ProtocolVersion) {
        chat = Message.fromJson(buf.readString())
    }

    override fun write(out: NetOutput, target: ProtocolVersion) {
        out.writeString(chat.toJson())
    }
}