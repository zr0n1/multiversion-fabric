package zer0n1.multiversion.protocol.packet;

import java.io.*;

import net.minecraft.item.ItemInstance;
import net.minecraft.network.PacketHandler;
import net.minecraft.packet.AbstractPacket;
import zer0n1.multiversion.protocol.PacketHandlerMultiversion;

public class SlotClicked0x66C2SPacket_P7 extends AbstractPacket
{
    public int windowId;
    public int inventorySlot;
    public int mouseClick;
    public short action;
    public ItemInstance item;

    public SlotClicked0x66C2SPacket_P7() { }

    public SlotClicked0x66C2SPacket_P7(int i, int j, int k, ItemInstance instance, short word0) {
        windowId = i;
        inventorySlot = j;
        mouseClick = k;
        item = instance;
        action = word0;
    }

    public void read(DataInputStream in) {
        try {
            windowId = in.readByte();
            inventorySlot = in.readShort();
            mouseClick = in.readByte();
            action = in.readShort();
            short word0 = in.readShort();
            if (word0 >= 0) {
                byte byte0 = in.readByte();
                short word1 = in.readShort();
                item = new ItemInstance(word0, byte0, word1);
            } else {
                item = null;
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void write(DataOutputStream out) {
        try {
            out.writeByte(windowId);
            out.writeShort(inventorySlot);
            out.writeByte(mouseClick);
            out.writeShort(action);
            if (item == null) {
                out.writeShort(-1);
            } else {
                out.writeShort(item.itemId);
                out.writeByte(item.count);
                out.writeShort(item.getDamage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void apply(PacketHandler handler) {
        PacketHandlerMultiversion.apply(this, handler);
    }

    public int length() {
        return 11;
    }
}
