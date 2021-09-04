package zer0n1.multiversion.protocol.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import net.minecraft.network.PacketHandler;
import net.minecraft.packet.AbstractPacket;
import zer0n1.multiversion.protocol.PacketHandlerMultiversion;

public class Respawn0x9Packet_P2 extends AbstractPacket
{

    public Respawn0x9Packet_P2() { }

    public void apply(PacketHandler handler) {
        PacketHandlerMultiversion.apply(this, handler);
    }

    public void read(DataInputStream datainputstream) { }

    public void write(DataOutputStream dataoutputstream) { }

    public int length() {
        return 0;
    }
}
