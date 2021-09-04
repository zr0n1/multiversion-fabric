package zer0n1.multiversion.protocol.packet;

import net.minecraft.item.ItemInstance;
import net.minecraft.network.PacketHandler;
import net.minecraft.packet.AbstractPacket;
import zer0n1.multiversion.protocol.PacketHandlerMultiversion;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class SlotUpdate0x67S2CPacket_P7 extends AbstractPacket
{
    public int containerId;
    public int slotIndex;
    public ItemInstance stack;

    public SlotUpdate0x67S2CPacket_P7() {
    }

    public void read(DataInputStream in) {
        try {
            containerId = in.readByte();
            slotIndex = in.readShort();
            short id = in.readShort();
            if(id >= 0) {
                byte count = in.readByte();
                byte damage = in.readByte();
                stack = new ItemInstance(id, count, damage);
            } else {
                stack = null;
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void write(DataOutputStream out) {
        try {
            out.writeByte(containerId);
            out.writeShort(slotIndex);
            if(stack == null) {
                out.writeShort(-1);
            } else {
                out.writeShort(stack.itemId);
                out.writeByte(stack.count);
                out.writeByte(stack.getDamage());
            }
        } catch(IOException e) {
            e.printStackTrace();
        }

    }

    public void apply(PacketHandler handler) {
        PacketHandlerMultiversion.apply(this, handler);
    }

    public int length() {
        return 7;
    }
}
