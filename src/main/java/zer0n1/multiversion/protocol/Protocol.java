package zer0n1.multiversion.protocol;

import java.util.*;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.packet.AbstractPacket;
import net.minecraft.packet.handshake.Handshake0x2Packet;
import net.minecraft.packet.login.LoginRequest0x1Packet;
import net.minecraft.packet.misc.Disconnect0xFFPacket;
import net.minecraft.packet.misc.KeepAlive0x0Packet;
import net.minecraft.packet.play.*;
import zer0n1.multiversion.mixin.AbstractPacketAccessor;
import zer0n1.multiversion.protocol.packet.*;

public enum Protocol
{
	BETA_14(14, "Beta 1.7.3", ""),
	BETA_13(13, "Beta 1.6.6", "/beta"),
	BETA_11(11, "Beta 1.5_02", "/beta"),
	BETA_10(10, "Beta 1.4_01", "/beta"),
	BETA_9(9, "Beta 1.3_01", "/beta"),
	BETA_8(8, "Beta 1.2_02", "/beta"),
	BETA_7(7, "Beta 1.1_01", "/beta"),
	ALPHA_6(6, "Alpha v1.2.6", "/alpha6"),
	ALPHA_5(5, "Alpha v1.2.3_04", "/alpha6"),
	ALPHA_3(3, "Alpha v1.2.1_01", "/alpha6"),
	ALPHA_2(2, "Alpha v1.1.2_01", "/alpha2");
	
    Protocol(int i, String s, String s1)
	{
		this.version = i;
		this.name = s;
		this.textures = s1;
	}

	public void putPackets() {
    	reloadPackets();
		if(version > 11) {
			return;
		}

		putPacket(9, true, true, Respawn0x9Packet_P2.class);
		putPacket(23, true, false, EntitySpawn0x17S2CPacket_P2.class);

		if(version < 11) {
			putPacket(1, true, true, LoginRequest0x1Packet_P3.class);
			if(version > 6) {
				putPacket(102, false, true, SlotClicked0x66C2SPacket_P7.class);
			}
		}

		if(version < 8) {
			putPacket(5, true, false, EntityEquipment0x5S2CPacket_P7.class);
			putPacket(21, true, true, ItemEntitySpawn0x15Packet_P2.class);
			putPacket(24, true, false, MobSpawn0x18S2CPacket_P2.class);
		}

		if(version == 7) {
			putPacket(103, true, false, SlotUpdate0x67S2CPacket_P7.class);
		}

		if(version < 7) {
			putPacket(15, false, true, PlaceBlock0xFC2SPacket_P2.class);
			putPacket(16, true, true, HeldSlotChange0x10Packet_P2.class);
			putPacket(17, true, true, AddStack0x11Packet_P2.class);
			putPacket(59, true, true, UpdateTileEntity0x3BPacket_P2.class);
		}

		if(version == 2) {
			putPacket(1, true, true, LoginRequest0x1Packet_P2.class);
		}

		/*
		// beta 1.5_02
		if(version == 11) {
			replacePacket(9, true, true, Respawn0x9Packet_P2.class);
			replacePacket(23, true, false, EntitySpawn0x17S2CPacket_P2.class);
		}

		// protocol 8-10, beta 1.2-beta 1.4
		if(version > 7 && version < 11) {
			replacePacket(1, true, true, LoginRequest0x1Packet_P3.class);
			replacePacket(9, true, true, Respawn0x9Packet_P2.class);
			replacePacket(23, true, false, EntitySpawn0x17S2CPacket_P2.class);
			replacePacket(102, false, true, SlotClicked0x66C2SPacket_P7.class);
		}

		// beta 1.1
		if(version == 7) {
			replacePacket(1, true, true, LoginRequest0x1Packet_P3.class);
			replacePacket(5, true, false, EntityEquipment0x5S2CPacket_P7.class);
			replacePacket(9, true, true, Respawn0x9Packet_P2.class);
			replacePacket(21, true, true, ItemEntitySpawn0x15Packet_P2.class);
			replacePacket(23, true, false, EntitySpawn0x17S2CPacket_P2.class);
			replacePacket(24, true, false, MobSpawn0x18S2CPacket_P2.class);
			replacePacket(102, false, true, SlotClicked0x66C2SPacket_P7.class);
			replacePacket(103, true, false, SlotUpdate0x67S2CPacket_P7.class);
		}

		// protocol 3-6, alpha v1.2.x
		if(version > 2 && version < 7) {
			replacePacket(1, true, true, LoginRequest0x1Packet_P3.class);
			replacePacket(5, true, false, EntityEquipment0x5S2CPacket_P2.class);
			replacePacket(8, true, false, UpdatePlayerHealth0x8S2CPacket_P2.class);
			replacePacket(9, true, true, Respawn0x9Packet_P2.class);
			replacePacket(15, false, true, PlaceBlock0xFC2SPacket_P2.class);
			replacePacket(16, true, true, HeldSlotChange0x10Packet_P2.class);
			replacePacket(17, true, true, AddStack0x11Packet_P2.class);
			replacePacket(21, true, true, ItemEntitySpawn0x15Packet_P2.class);
			replacePacket(23, true, false, EntitySpawn0x17S2CPacket_P2.class);
			replacePacket(24, true, false, MobSpawn0x18S2CPacket_P2.class);
			replacePacket(59, true, true, UpdateTileEntity0x3BPacket_P2.class);
		}

		// alpha v1.1.2_01
		if(version == 2) {
			replacePacket(1, true, true, LoginRequest0x1Packet_P2.class);
			replacePacket(5, true, false, EntityEquipment0x5S2CPacket_P2.class);
			replacePacket(8, true, false, UpdatePlayerHealth0x8S2CPacket_P2.class);
			replacePacket(9, true, true, Respawn0x9Packet_P2.class);
			replacePacket(15, false, true, PlaceBlock0xFC2SPacket_P2.class);
			replacePacket(16, true, true, HeldSlotChange0x10Packet_P2.class);
			replacePacket(17, true, true, AddStack0x11Packet_P2.class);
			replacePacket(21, true, true, ItemEntitySpawn0x15Packet_P2.class);
			replacePacket(23, true, false, EntitySpawn0x17S2CPacket_P2.class);
			replacePacket(24, true, false, MobSpawn0x18S2CPacket_P2.class);
			replacePacket(59, true, true, UpdateTileEntity0x3BPacket_P2.class);
		}
		 */
	}

	public void putPacket(int id, boolean serverToClient, boolean clientToServer, Class<? extends AbstractPacket> class1) {
		HashMap idToClass = new HashMap();
		idToClass.putAll(AbstractPacketAccessor.getIdToClass());
		HashMap classToId = new HashMap();
		classToId.putAll(AbstractPacketAccessor.getClassToId());
		HashSet serverToClientPackets = new HashSet();
		serverToClientPackets.addAll(AbstractPacketAccessor.getServerToClientPackets());
		HashSet clientToServerPackets = new HashSet();
		clientToServerPackets.addAll(AbstractPacketAccessor.getClientToServerPackets());
		if(serverToClient && !serverToClientPackets.contains(id)) {
			serverToClientPackets.add(id);
		} else if(!serverToClient && serverToClientPackets.contains(id)) {
			serverToClientPackets.remove(id);
		}
		if(clientToServer && !clientToServerPackets.contains(id)) {
			clientToServerPackets.add(id);
		} else if(!clientToServer && clientToServerPackets.contains(id)) {
			clientToServerPackets.remove(id);
		}
		idToClass.put(id, class1);
		classToId.put(class1, id);
		AbstractPacketAccessor.setIdToClass(idToClass);
		AbstractPacketAccessor.setClassToId(classToId);
		AbstractPacketAccessor.setServerToClientPackets(serverToClientPackets);
		AbstractPacketAccessor.setClientToServerPackets(clientToServerPackets);
	}
    
    public String textures() {
		Minecraft mc = (Minecraft) FabricLoader.getInstance().getGameInstance();
    	/*
    	if(mc.texturePackManager.texturePack instanceof DefaultTexturePack && mc.gameSettings.versionGraphics)
    	{
    		return textures;
    	} else
    	 */
    	return "";
    }

    public void reloadPackets() {
    	AbstractPacketAccessor.setIdToClass(new HashMap());
    	AbstractPacketAccessor.setClassToId(new HashMap());
    	AbstractPacketAccessor.setServerToClientPackets(new HashSet());
    	AbstractPacketAccessor.setClientToServerPackets((new HashSet()));

		AbstractPacketAccessor.register(0, true, true, KeepAlive0x0Packet.class);
		AbstractPacketAccessor.register(1, true, true, LoginRequest0x1Packet.class);
		AbstractPacketAccessor.register(2, true, true, Handshake0x2Packet.class);
		AbstractPacketAccessor.register(3, true, true, ChatMessage0x3Packet.class);
		AbstractPacketAccessor.register(4, true, false, TimeUpdate0x4S2CPacket.class);
		AbstractPacketAccessor.register(5, true, false, EntityEquipment0x5S2CPacket.class);
		AbstractPacketAccessor.register(6, true, false, SpawnPosition0x6S2CPacket.class);
		AbstractPacketAccessor.register(7, false, true, EntityInteract0x7C2SPacket.class);
		AbstractPacketAccessor.register(8, true, false, UpdatePlayerHealth0x8S2CPacket.class);
		AbstractPacketAccessor.register(9, true, true, Respawn0x9C2SPacket.class);
		AbstractPacketAccessor.register(10, true, true, BaseOnGround0xAC2SPacket.class);
		AbstractPacketAccessor.register(11, true, true, PlayerPosition0xBC2SPacket.class);
		AbstractPacketAccessor.register(12, true, true, PlayerLook0xCC2SPacket.class);
		AbstractPacketAccessor.register(13, true, true, PlayerPositionAndLook0xDC2SPacket.class);
		AbstractPacketAccessor.register(14, false, true, PlayerDigging0xEC2SPacket.class);
		AbstractPacketAccessor.register(15, false, true, PlaceBlock0xFC2SPacket.class);
		AbstractPacketAccessor.register(16, false, true, HeldSlotChange0x10C2SPacket.class);
		AbstractPacketAccessor.register(17, true, false, UseBed0x11S2CPacket.class);
		AbstractPacketAccessor.register(18, true, true, Animation0x12C2SPacket.class);
		AbstractPacketAccessor.register(19, false, true, EntityAction0x13C2SPacket.class);
		AbstractPacketAccessor.register(20, true, false, PlayerSpawn0x14S2CPacket.class);
		AbstractPacketAccessor.register(21, true, false, ItemEntitySpawn0x15S2CPacket.class);
		AbstractPacketAccessor.register(22, true, false, CollectItem0x16C2SPacket.class);
		AbstractPacketAccessor.register(23, true, false, EntitySpawn0x17S2CPacket.class);
		AbstractPacketAccessor.register(24, true, false, MobSpawn0x18S2CPacket.class);
		AbstractPacketAccessor.register(25, true, false, Painting0x19S2CPacket.class);
		AbstractPacketAccessor.register(27, false, true, StanceUpdate0x1BC2SPacket.class);
		AbstractPacketAccessor.register(28, true, false, EntityVelocity0x1CS2CPacket.class);
		AbstractPacketAccessor.register(29, true, false, EntityDeath0x1DS2CPacket.class);
		AbstractPacketAccessor.register(30, true, false, BaseEntityPacket0x1ES2CPacket.class);
		AbstractPacketAccessor.register(31, true, false, EntityRelativeMove0x1FS2CPacket.class);
		AbstractPacketAccessor.register(32, true, false, EntityLook0x20S2CPacket.class);
		AbstractPacketAccessor.register(33, true, false, EntityLookAndRelativeMove0x21S2CPacket.class);
		AbstractPacketAccessor.register(34, true, false, EntityTeleport0x22S2CPacket.class);
		AbstractPacketAccessor.register(38, true, false, EntityStatus0x26S2CPacket.class);
		AbstractPacketAccessor.register(39, true, false, RideEntity0x27S2CPacket.class);
		AbstractPacketAccessor.register(40, true, false, EntityMetadata0x28S2CPacket.class);
		AbstractPacketAccessor.register(50, true, false, PreChunk0x32S2CPacket.class);
		AbstractPacketAccessor.register(51, true, false, MapChunk0x33S2CPacket.class);
		AbstractPacketAccessor.register(52, true, false, MultiBlockChange0x34S2CPacket.class);
		AbstractPacketAccessor.register(53, true, false, BlockChange0x35S2CPacket.class);
		AbstractPacketAccessor.register(54, true, false, Note0x36S2CPacket.class);
		AbstractPacketAccessor.register(60, true, false, CreateExplosion0x3CS2CPacket.class);
		AbstractPacketAccessor.register(61, true, false, PlaySound0x3DS2CPacket.class);
		AbstractPacketAccessor.register(70, true, false, InvalidateState0x46S2CPacket.class);
		AbstractPacketAccessor.register(71, true, false, LightningStrik0x47S2CPacket.class);
		AbstractPacketAccessor.register(100, true, false, OpenContainer0x64S2CPacket.class);
		AbstractPacketAccessor.register(101, true, true, CloseContainer0x65C2SPacket.class);
		AbstractPacketAccessor.register(102, false, true, SlotClicked0x66C2SPacket.class);
		AbstractPacketAccessor.register(103, true, false, SlotUpdate0x67S2CPacket.class);
		AbstractPacketAccessor.register(104, true, false, InventoryUpdate0x68S2CPacket.class);
		AbstractPacketAccessor.register(105, true, false, UpdateFurnaceProgress0x69S2CPacket.class);
		AbstractPacketAccessor.register(106, true, true, Transaction0x6AS2CPacket.class);
		AbstractPacketAccessor.register(130, true, true, UpdateSign0x82C2SPacket.class);
		AbstractPacketAccessor.register(131, true, false, MapData0x83S2CPacket.class);
		AbstractPacketAccessor.register(200, true, false, IncreaseStat0xC8S2CPacket.class);
		AbstractPacketAccessor.register(255, true, true, Disconnect0xFFPacket.class);
	}

    /*
    public void addRecipes(CraftingManager cm)
    {
    	int p = this.protocol;
    	if(p >= 6)
    	{
            cm.addRecipe(new ItemStack(Item.pocketSundial, 1), new Object[] {
                    " # ", "#X#", " # ", Character.valueOf('#'), Item.ingotGold, Character.valueOf('X'), Item.redstone
                });
    	}
        if(p >= 8)
        {
        	cm.addRecipe(new ItemStack(Block.dispenser, 1), new Object[] {
        			"###", "#X#", "#R#", Character.valueOf('#'), Block.cobblestone, Character.valueOf('X'), Item.bow, Character.valueOf('R'), Item.redstone
        	});
        	cm.addRecipe(new ItemStack(Block.torchWood, 4), new Object[] {
        			"X", "#", Character.valueOf('X'), new ItemStack(Item.coal, 1, 1), Character.valueOf('#'), Item.stick
        	});
        	cm.addRecipe(new ItemStack(Item.cake, 1), new Object[] {
        			"AAA", "BEB", "CCC", Character.valueOf('A'), Item.bucketMilk, Character.valueOf('B'), Item.sugar, Character.valueOf('C'), Item.wheat, Character.valueOf('E'), 
        			Item.egg
        	});
        	cm.addRecipe(new ItemStack(Item.sugar, 1), new Object[] {
        			"#", Character.valueOf('#'), Item.reed
        	});
        	cm.addRecipe(new ItemStack(Block.musicBlock, 1), new Object[] {
        			"###", "#X#", "###", Character.valueOf('#'), Block.planks, Character.valueOf('X'), Item.redstone
        	});
        }
        if(p >= 9)
        {
        	cm.addRecipe(new ItemStack(Item.bed, 1), new Object[] {
        			"###", "XXX", Character.valueOf('#'), Block.cloth, Character.valueOf('X'), Block.planks
        	});
        	cm.addRecipe(new ItemStack(Item.redstoneRepeater, 1), new Object[] {
        			"#X#", "III", Character.valueOf('#'), Block.torchRedstoneActive, Character.valueOf('X'), Item.redstone, Character.valueOf('I'), Block.stone
        	});
        	cm.addRecipe(new ItemStack(Block.stairSingle, 3, 3), new Object[] {
        			"###", Character.valueOf('#'), Block.cobblestone
        	});
        	cm.addRecipe(new ItemStack(Block.stairSingle, 3, 1), new Object[] {
        			"###", Character.valueOf('#'), Block.sandStone
        	});
        	cm.addRecipe(new ItemStack(Block.stairSingle, 3, 2), new Object[] {
        			"###", Character.valueOf('#'), Block.planks
        	});
        }
        if(p >= 11)
        {
        	cm.addRecipe(new ItemStack(Block.railPowered, 6), new Object[] {
        			"X X", "X#X", "XRX", Character.valueOf('X'), Item.ingotGold, Character.valueOf('R'), Item.redstone, Character.valueOf('#'), Item.stick
        	});
        	cm.addRecipe(new ItemStack(Block.railDetector, 6), new Object[] {
        			"X X", "X#X", "XRX", Character.valueOf('X'), Item.ingotIron, Character.valueOf('R'), Item.redstone, Character.valueOf('#'), Block.pressurePlateStone
        	});
        }
        if(p >= 13)
        {
        	cm.addRecipe(new ItemStack(Block.trapdoor, 2), new Object[] {
        			"###", "###", Character.valueOf('#'), Block.planks
        	});
        	cm.addRecipe(new ItemStack(Item.mapItem, 1), new Object[] {
        			"###", "#X#", "###", Character.valueOf('#'), Item.paper, Character.valueOf('X'), Item.compass
        	});
        }
        if(p == 14)
        {
        	cm.addRecipe(new ItemStack(Block.pistonBase, 1), new Object[] {
        			"TTT", "#X#", "#R#", Character.valueOf('#'), Block.cobblestone, Character.valueOf('X'), Item.ingotIron, Character.valueOf('R'), Item.redstone, Character.valueOf('T'), 
        			Block.planks
        	});
        	cm.addRecipe(new ItemStack(Block.pistonStickyBase, 1), new Object[] {
        			"S", "P", Character.valueOf('S'), Item.slimeBall, Character.valueOf('P'), Block.pistonBase
        	});
        }
    }
    */

    public boolean alpha() {
    	return this.version < 7;
    }
	
	public int version;
	public String name;
	private String textures;
	public static Set<Protocol> alphaList = new HashSet<Protocol>();
	public static Set<Protocol> betaList = new HashSet<Protocol>();
	public static Set<Protocol> protocolList = new HashSet<Protocol>();
	public static HashMap<Integer, Protocol> versionMap = new HashMap<Integer, Protocol>();
	static
	{
		betaList.add(BETA_14);
		betaList.add(BETA_13);
		betaList.add(BETA_11);
		betaList.add(BETA_10);
		betaList.add(BETA_9);
		betaList.add(BETA_8);
		betaList.add(BETA_7);
		alphaList.add(ALPHA_6);
		alphaList.add(ALPHA_5);
		alphaList.add(ALPHA_3);
		alphaList.add(ALPHA_2);
		protocolList.addAll(alphaList);
		protocolList.addAll(betaList);
		for(Protocol v : protocolList)
		{
			versionMap.put(v.version, v);
		}
	}
}
