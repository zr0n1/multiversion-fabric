package zer0n1.multiversion.protocol.packet;

import java.io.*;

import net.minecraft.network.PacketHandler;
import net.minecraft.packet.AbstractPacket;
import zer0n1.multiversion.protocol.PacketHandlerMultiversion;

public class HeldSlotChange0x10Packet_P2 extends AbstractPacket
{
    public int playerEntityId;
    public int itemId;

    public HeldSlotChange0x10Packet_P2() {
    }

    public HeldSlotChange0x10Packet_P2(int i, int j) {
        playerEntityId = i;
        itemId = j;
    }

    public void read(DataInputStream datainputstream) {
        try {
            playerEntityId = datainputstream.readInt();
            itemId = datainputstream.readShort();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void write(DataOutputStream dataoutputstream) {
        try {
            dataoutputstream.writeInt(playerEntityId);
            dataoutputstream.writeShort(itemId);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void apply(PacketHandler handler) {
        PacketHandlerMultiversion.apply(this, handler);
    }

    public int length() {
        return 6;
    }
}