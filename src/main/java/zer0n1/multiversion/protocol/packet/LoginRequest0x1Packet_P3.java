package zer0n1.multiversion.protocol.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.network.PacketHandler;
import net.minecraft.packet.AbstractPacket;
import zer0n1.multiversion.protocol.PacketHandlerMultiversion;

public class LoginRequest0x1Packet_P3 extends AbstractPacket
{
    public LoginRequest0x1Packet_P3() {
    }

    public LoginRequest0x1Packet_P3(String s, String s1, int i)
    {
        username = s;
        password = s1;
        protocolVersion = i;
    }

    public void read(DataInputStream in) {
        try {
            protocolVersion = in.readInt();
            username = readString(in, 16);
            password = readString(in, 32);
            worldSeed = in.readLong();
            dimensionId = in.readByte();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void write(DataOutputStream out) {
        try {
            out.writeInt(protocolVersion);
            writeString(this.username, out);
            writeString(this.password, out);
            out.writeLong(worldSeed);
            out.writeByte(dimensionId);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void apply(PacketHandler handler) {
    	PacketHandlerMultiversion.apply(this, handler);
    }

    public int length() {
        return 4 + username.length() + password.length() + 4 + 5;
    }

    public int protocolVersion;
    public String username;
    public String password;
    public long worldSeed;
    public byte dimensionId;
}
