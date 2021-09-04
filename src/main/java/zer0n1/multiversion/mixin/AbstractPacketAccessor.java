package zer0n1.multiversion.mixin;

import net.minecraft.packet.AbstractPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Mixin(AbstractPacket.class)
public interface AbstractPacketAccessor
{
	@Accessor("idToClass")
	static Map getIdToClass() {
		throw new AssertionError();
	}
	@Accessor("idToClass")
	static void setIdToClass(Map map) {
		throw new AssertionError();
	}
	@Accessor("classToId")
	static Map getClassToId() {
		throw new AssertionError();
	}
	@Accessor("classToId")
	static void setClassToId(Map map) {
		throw new AssertionError();
	}
	@Accessor("serverToClientPackets")
	static Set getServerToClientPackets() {
		throw new AssertionError();
	}
	@Accessor("serverToClientPackets")
	static void setServerToClientPackets(Set set) {
		throw new AssertionError();
	}
	@Accessor("clientToServerPackets")
	static Set getClientToServerPackets() {
		throw new AssertionError();
	}
	@Accessor("clientToServerPackets")
	static void setClientToServerPackets(Set set) {
		throw new AssertionError();
	}
	@Invoker("register")
	static void register(int id, boolean serverToClient, boolean clientToServer, Class packetClass) {
		throw new AssertionError();
	}
}
