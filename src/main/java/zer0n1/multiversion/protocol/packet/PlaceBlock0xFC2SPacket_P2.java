package zer0n1.multiversion.protocol.packet;

import java.io.*;

import net.minecraft.network.PacketHandler;
import net.minecraft.packet.AbstractPacket;
import zer0n1.multiversion.protocol.PacketHandlerMultiversion;

public class PlaceBlock0xFC2SPacket_P2 extends AbstractPacket
{
    public int id;
    public int x;
    public int y;
    public int z;
    public int direction;

    public PlaceBlock0xFC2SPacket_P2() { }

    public PlaceBlock0xFC2SPacket_P2(int i, int j, int k, int l, int i1)
    {
        id = i;
        x = j;
        y = k;
        z = l;
        direction = i1;
    }

    public void read(DataInputStream datainputstream) {
        try {
            id = datainputstream.readShort();
            x = datainputstream.readInt();
            y = datainputstream.read();
            z = datainputstream.readInt();
            direction = datainputstream.read();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void write(DataOutputStream dataoutputstream) {
        try {
            dataoutputstream.writeShort(id);
            dataoutputstream.writeInt(x);
            dataoutputstream.write(y);
            dataoutputstream.writeInt(z);
            dataoutputstream.write(direction);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void apply(PacketHandler handler)
    {
        PacketHandlerMultiversion.apply(this, handler);
    }

    public int length() {
        return 12;
    }
}
