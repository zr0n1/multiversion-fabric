package zer0n1.multiversion.protocol.packet;

import java.io.*;
import net.minecraft.entity.Item;
import net.minecraft.network.PacketHandler;
import net.minecraft.packet.AbstractPacket;
import net.minecraft.util.maths.MathHelper;
import zer0n1.multiversion.protocol.PacketHandlerMultiversion;

public class ItemEntitySpawn0x15Packet_P2 extends AbstractPacket
{
    public int entityId;
    public int x;
    public int y;
    public int z;
    public byte rotation;
    public byte pitch;
    public byte roll;
    public int itemId;
    public int count;

    public ItemEntitySpawn0x15Packet_P2() { }

    public ItemEntitySpawn0x15Packet_P2(Item entity) {
        entityId = entity.entityId;
        itemId = entity.item.itemId;
        count = entity.item.count;
        x = MathHelper.floor(entity.x * 32D);
        y = MathHelper.floor(entity.y * 32D);
        z = MathHelper.floor(entity.z * 32D);
        rotation = (byte)(int)(entity.velocityX * 128D);
        pitch = (byte)(int)(entity.velocityY * 128D);
        roll = (byte)(int)(entity.velocityZ * 128D);
    }

    public void read(DataInputStream datainputstream) {
        try {
            entityId = datainputstream.readInt();
            itemId = datainputstream.readShort();
            count = datainputstream.readByte();
            x = datainputstream.readInt();
            y = datainputstream.readInt();
            z = datainputstream.readInt();
            rotation = datainputstream.readByte();
            pitch = datainputstream.readByte();
            roll = datainputstream.readByte();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(DataOutputStream dataoutputstream)
    {
        try {
            dataoutputstream.writeInt(entityId);
            dataoutputstream.writeShort(itemId);
            dataoutputstream.writeByte(count);
            dataoutputstream.writeInt(x);
            dataoutputstream.writeInt(y);
            dataoutputstream.writeInt(z);
            dataoutputstream.writeByte(rotation);
            dataoutputstream.writeByte(pitch);
            dataoutputstream.writeByte(roll);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void apply(PacketHandler handler) {
        PacketHandlerMultiversion.apply(this, handler);
    }

    public int length() {
        return 22;
    }
}
