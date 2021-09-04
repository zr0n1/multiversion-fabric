package zer0n1.multiversion.protocol.packet;

import java.io.*;

import net.minecraft.item.ItemInstance;
import net.minecraft.network.PacketHandler;
import net.minecraft.packet.AbstractPacket;
import zer0n1.multiversion.protocol.PacketHandlerMultiversion;

public class EntityEquipment0x5S2CPacket_P2 extends AbstractPacket
{
    public int type;
    public ItemInstance items[];

    public EntityEquipment0x5S2CPacket_P2() {
    }

    public EntityEquipment0x5S2CPacket_P2(int i, ItemInstance items[]) {
        type = i;
        items = new ItemInstance[items.length];
        for(int j = 0; j < items.length; j++)
        {
            this.items[j] = items[j] != null ? items[j].copy() : null;
        }

    }

    public void read(DataInputStream in) {
        try {
            type = in.readInt();
            short word0 = in.readShort();
            items = new ItemInstance[word0];
            for (int i = 0; i < word0; i++) {
                short word1 = in.readShort();
                if (word1 >= 0) {
                    byte byte0 = in.readByte();
                    short word2 = in.readShort();
                    items[i] = new ItemInstance(word1, byte0, word2);
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void write(DataOutputStream dataoutputstream) {
        try {
            dataoutputstream.writeInt(type);
            dataoutputstream.writeShort(items.length);
            for (int i = 0; i < items.length; i++) {
                if (items[i] == null) {
                    dataoutputstream.writeShort(-1);
                } else {
                    dataoutputstream.writeShort((short) items[i].itemId);
                    dataoutputstream.writeByte((byte) items[i].count);
                    dataoutputstream.writeShort((short) items[i].getDamage());
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void apply(PacketHandler handler) {
        PacketHandlerMultiversion.apply(this, handler);
    }

    public int length() {
        return 6 + items.length * 5;
    }
}
