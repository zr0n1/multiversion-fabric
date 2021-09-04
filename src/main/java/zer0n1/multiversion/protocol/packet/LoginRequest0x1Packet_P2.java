package zer0n1.multiversion.protocol.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.network.PacketHandler;
import net.minecraft.packet.AbstractPacket;
import zer0n1.multiversion.protocol.PacketHandlerMultiversion;

public class LoginRequest0x1Packet_P2 extends AbstractPacket
{
    public int protocolVersion;
    public String username;
    public String password;

    public LoginRequest0x1Packet_P2() {
    }

    public LoginRequest0x1Packet_P2(String s, String s1, int i)
    {
        username = s;
        password = s1;
        protocolVersion = i;
    }

    public void read(DataInputStream datainputstream) {
        try {
            protocolVersion = datainputstream.readInt();
            username = datainputstream.readUTF();
            password = datainputstream.readUTF();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void write(DataOutputStream dataoutputstream) {
        try {
            dataoutputstream.writeInt(protocolVersion);
            dataoutputstream.writeUTF(username);
            dataoutputstream.writeUTF(password);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void apply(PacketHandler handler) {
        PacketHandlerMultiversion.apply(this, handler);
    }

    public int length() {
        return 4 + username.length() + password.length() + 4;
    }
}
