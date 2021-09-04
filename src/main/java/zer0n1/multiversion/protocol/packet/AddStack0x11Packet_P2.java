package zer0n1.multiversion.protocol.packet;

import java.io.*;

import net.minecraft.network.PacketHandler;
import net.minecraft.packet.AbstractPacket;
import zer0n1.multiversion.protocol.PacketHandlerMultiversion;

public class AddStack0x11Packet_P2 extends AbstractPacket
{
    public int id;
    public int count;
    public int durability;

    public AddStack0x11Packet_P2() { }

    public void read(DataInputStream datainputstream) {
        try {
            id = datainputstream.readShort();
            count = datainputstream.readByte();
            durability = datainputstream.readShort();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void write(DataOutputStream dataoutputstream) {
        try {
            dataoutputstream.writeShort(id);
            dataoutputstream.writeByte(count);
            dataoutputstream.writeShort(durability);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void apply(PacketHandler handler) {
        PacketHandlerMultiversion.apply(this, handler);
    }

    public int length() {
        return 5;
    }
}
