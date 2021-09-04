package zer0n1.multiversion.protocol.packet;

import java.io.*;

import net.minecraft.network.PacketHandler;
import net.minecraft.packet.AbstractPacket;
import zer0n1.multiversion.protocol.PacketHandlerMultiversion;

public class UpdatePlayerHealth0x8S2CPacket_P2 extends AbstractPacket
{
    public int health;

    public UpdatePlayerHealth0x8S2CPacket_P2() { }

    public void read(DataInputStream in) {
        try {
            health = in.readByte();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void write(DataOutputStream out) {
        try {
            out.writeByte(health);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void apply(PacketHandler handler) {
        PacketHandlerMultiversion.apply(this, handler);
    }

    public int length() {
        return 1;
    }
}
