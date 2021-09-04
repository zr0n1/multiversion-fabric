package zer0n1.multiversion.protocol.packet;

import java.io.*;

import net.minecraft.entity.EntityRegistry;
import net.minecraft.entity.Living;
import net.minecraft.network.PacketHandler;
import net.minecraft.packet.AbstractPacket;
import net.minecraft.util.maths.MathHelper;
import zer0n1.multiversion.protocol.PacketHandlerMultiversion;

public class MobSpawn0x18S2CPacket_P2 extends AbstractPacket
{
    public int entityId;
    public byte rawId;
    public int x;
    public int y;
    public int z;
    public byte yaw;
    public byte pitch;

    public MobSpawn0x18S2CPacket_P2() { }

    public MobSpawn0x18S2CPacket_P2(Living entity) {
        entityId = entity.entityId;
        rawId = (byte)EntityRegistry.getId(entity);
        x = MathHelper.floor(entity.x * 32D);
        y = MathHelper.floor(entity.y * 32D);
        z = MathHelper.floor(entity.z * 32D);
        yaw = (byte)(int)((entity.yaw * 256F) / 360F);
        pitch = (byte)(int)((entity.pitch * 256F) / 360F);
    }

    public void read(DataInputStream datainputstream)
    {
        try {
            entityId = datainputstream.readInt();
            rawId = datainputstream.readByte();
            x = datainputstream.readInt();
            y = datainputstream.readInt();
            z = datainputstream.readInt();
            yaw = datainputstream.readByte();
            pitch = datainputstream.readByte();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(DataOutputStream dataoutputstream)
    {
        try {
            dataoutputstream.writeInt(entityId);
            dataoutputstream.writeByte(rawId);
            dataoutputstream.writeInt(x);
            dataoutputstream.writeInt(y);
            dataoutputstream.writeInt(z);
            dataoutputstream.writeByte(yaw);
            dataoutputstream.writeByte(pitch);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void apply(PacketHandler handler) {
        PacketHandlerMultiversion.apply(this, handler);
    }

    public int length() {
        return 19;
    }
}
