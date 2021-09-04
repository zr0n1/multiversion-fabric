package zer0n1.multiversion.protocol.packet;

import net.minecraft.network.PacketHandler;
import net.minecraft.packet.AbstractPacket;
import zer0n1.multiversion.protocol.PacketHandlerMultiversion;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class EntityEquipment0x5S2CPacket_P7 extends AbstractPacket
{
    public int entityId;
    public int slot;
    public int itemId;

    public EntityEquipment0x5S2CPacket_P7() {
    }

    public void read(DataInputStream in) {
        try {
            entityId = in.readInt();
            slot = in.readShort();
            itemId = in.readShort();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void write(DataOutputStream out) {
        try {
            out.writeInt(entityId);
            out.writeShort(slot);
            out.writeShort(itemId);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void apply(PacketHandler handler) {
        PacketHandlerMultiversion.apply(this, handler);
    }

    public int length() {
        return 8;
    }
}
