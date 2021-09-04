package zer0n1.multiversion.protocol.packet;

import java.io.*;

import net.minecraft.network.PacketHandler;
import net.minecraft.packet.AbstractPacket;
import zer0n1.multiversion.protocol.PacketHandlerMultiversion;

public class EntitySpawn0x17S2CPacket_P2 extends AbstractPacket
{
    public int entityId;
    public int x;
    public int y;
    public int z;
    public int type;

    public EntitySpawn0x17S2CPacket_P2() { }

    public void read(DataInputStream in) {
        try {
            entityId = in.readInt();
            type = in.readByte();
            x = in.readInt();
            y = in.readInt();
            z = in.readInt();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void write(DataOutputStream out) {
        try {
            out.writeInt(entityId);
            out.writeByte(type);
            out.writeInt(x);
            out.writeInt(y);
            out.writeInt(z);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void apply(PacketHandler handler) {
        PacketHandlerMultiversion.apply(this, handler);
    }

    public int length() {
        return 17;
    }
}
