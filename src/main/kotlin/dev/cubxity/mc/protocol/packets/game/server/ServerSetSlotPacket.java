/*
 * Copyright (c) 2018 - 2019 Cubixity, superblaubeere27 and KodingKing1
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package dev.cubxity.mc.protocol.packets.game.server;

import dev.cubxity.mc.protocol.ProtocolVersion;
import dev.cubxity.mc.protocol.data.obj.Slot;
import dev.cubxity.mc.protocol.net.NetInput;
import dev.cubxity.mc.protocol.net.NetOutput;
import dev.cubxity.mc.protocol.packets.Packet;
import org.jetbrains.annotations.NotNull;

public class ServerSetSlotPacket extends Packet {
    private byte windowId;
    private short slotId;
    private Slot slotData;

    public ServerSetSlotPacket(byte windowId, short slotId, Slot slotData) {
        this.windowId = windowId;
        this.slotId = slotId;
        this.slotData = slotData;
    }

    public ServerSetSlotPacket() {
    }

    @Override
    public void read(@NotNull NetInput buf, @NotNull ProtocolVersion target) {
        windowId = buf.readByte();
        slotId = buf.readShort();
        slotData = buf.readSlot();
    }

    @Override
    public void write(@NotNull NetOutput out, @NotNull ProtocolVersion target) {
        out.writeByte(windowId);
        out.writeShort(slotId);
        out.writeSlot(slotData);
    }
}
