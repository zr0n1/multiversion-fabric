package zer0n1.multiversion.protocol;

import java.io.File;

import net.minecraft.client.Minecraft;

public class ProtocolManager
{
	public ProtocolManager() {
		// set to beta 1.7.3 by default
		protocol = Protocol.BETA_14;
	}
	
	public void setProtocol(Protocol p) {
		// set fancy grass to false if alpha and version graphics are enabled
	//	BlockRenderer.field_67 = (p.version < 11 && mc.gameSettings.versionGraphics) ? false : mc.options.fancyGraphics;
		if(protocol == p) {
			return;
		}
		p.putPackets();
		protocol = p;
	//	RecipeRegistry.getInstance().loadRecipes();
	//	SmeltingRecipeRegistry.smelting().loadRecipes();
	}
	
	public static void setMinecraft(Minecraft minecraft)
	{
		instance.mc = minecraft;
		addSound("newsound/random/old_door_open.ogg", minecraft);
		addSound("newsound/random/old_door_close.ogg", minecraft);
		addSound("newsound/random/old_bow.ogg", minecraft);
	}
	
	public static void addSound(String s, Minecraft minecraft)
	{
		File mcDir = Minecraft.getGameDirectory();
		File file = new File(mcDir, "resources/" + s);
		if(file.exists())
		{
			minecraft.loadSoundFromDir(s, file);
			System.out.println("Loaded sound: " + s);
		} else
		{
			System.out.println("Failed to load sound: " + s);
		}
	}
	
	public static Protocol protocol() {
		return instance.protocol;
	}

	public static int version() {
		return instance.protocol.version;
	}
	
	public Protocol protocol;
	public static final ProtocolManager instance = new ProtocolManager();
	public Minecraft mc;
}