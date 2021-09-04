package zer0n1.multiversion.protocol.packet;

import java.io.*;
import java.util.zip.GZIPOutputStream;

import net.minecraft.network.PacketHandler;
import net.minecraft.packet.AbstractPacket;
import net.minecraft.tileentity.TileEntityBase;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.io.NBTIO;
import zer0n1.multiversion.protocol.PacketHandlerMultiversion;

public class UpdateTileEntity0x3BPacket_P2 extends AbstractPacket
{
    public int x;
    public int y;
    public int z;
    public byte entityData[];
    public CompoundTag entityNBT;

    public UpdateTileEntity0x3BPacket_P2() {
        levelPacket = true;
    }

    public UpdateTileEntity0x3BPacket_P2(int i, int j, int k, TileEntityBase entity) {
        levelPacket = true;
        x = i;
        y = j;
        z = k;
        entityNBT = new CompoundTag();
        entity.writeIdentifyingData(entityNBT);
        try {
            entityData = writeGzippedToBytes(entityNBT);
        } catch(IOException ioexception) {
            ioexception.printStackTrace();
        }
    }

    public void read(DataInputStream datainputstream)
    {
        try {
            x = datainputstream.readInt();
            y = datainputstream.readShort();
            z = datainputstream.readInt();
            int i = datainputstream.readShort() & 0xffff;
            entityData = new byte[i];
            datainputstream.readFully(entityData);
            entityNBT = NBTIO.readGzipped(new ByteArrayInputStream(entityData));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void write(DataOutputStream dataoutputstream)
    {
        try {
            dataoutputstream.writeInt(x);
            dataoutputstream.writeShort(y);
            dataoutputstream.writeInt(z);
            dataoutputstream.writeShort((short) entityData.length);
            dataoutputstream.write(entityData);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] writeGzippedToBytes(CompoundTag tag) throws IOException {
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(new GZIPOutputStream(bytearrayoutputstream));
        try {
            NBTIO.writeTag(tag, out);
        } finally {
            out.close();
        }
        return bytearrayoutputstream.toByteArray();
    }

    public void apply(PacketHandler handler) {
        PacketHandlerMultiversion.apply(this, handler);
    }

    public int length() {
        return entityData.length + 2 + 10;
    }
}
