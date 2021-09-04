package zer0n1.multiversion.protocol;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityBase;
import net.minecraft.entity.Item;
import net.minecraft.entity.player.AbstractClientPlayer;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.entity.player.PlayerContainer;
import net.minecraft.item.ItemInstance;
import net.minecraft.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketHandler;
import net.minecraft.packet.*;
import net.minecraft.packet.login.LoginRequest0x1Packet;
import net.minecraft.packet.play.*;
import net.minecraft.tileentity.TileEntityBase;
import zer0n1.multiversion.mixin.ClientPlayNetworkHandlerAccessor;
import zer0n1.multiversion.mixin.CraftingAccessor;
import zer0n1.multiversion.protocol.packet.*;

public class PacketHandlerMultiversion
{
    public static void apply(AbstractPacket packet, PacketHandler handler)
    {
        ClientPlayNetworkHandler netHandler = (ClientPlayNetworkHandler)handler;
        if(packet instanceof LoginRequest0x1Packet_P3) {
            onLoginRequest_P3((LoginRequest0x1Packet_P3)packet, netHandler);
        }
        if(packet instanceof LoginRequest0x1Packet_P2) {
            onLoginRequest_P2((LoginRequest0x1Packet_P2)packet, netHandler);
        }
        if(packet instanceof EntityEquipment0x5S2CPacket_P2) {
            onEntityEquipment_P2((EntityEquipment0x5S2CPacket_P2)packet);
        }
        if(packet instanceof EntityEquipment0x5S2CPacket_P7) {
            onEntityEquipment_P7((EntityEquipment0x5S2CPacket_P7)packet, netHandler);
        }
        if(packet instanceof UpdatePlayerHealth0x8S2CPacket_P2){
            onPlayerHealthUpdate_P2((UpdatePlayerHealth0x8S2CPacket_P2)packet, netHandler);
        }
        if(packet instanceof Respawn0x9Packet_P2){
            onRespawn_P2(netHandler);
        }
        if(packet instanceof HeldSlotChange0x10Packet_P2) {
            handlePlayerHeldItemUpdate((HeldSlotChange0x10Packet_P2)packet, netHandler);
        }
        if(packet instanceof AddStack0x11Packet_P2) {
            onAddStack((AddStack0x11Packet_P2)packet);
        }
        if(packet instanceof ItemEntitySpawn0x15Packet_P2) {
            onItemEntitySpawn_P2((ItemEntitySpawn0x15Packet_P2)packet, netHandler);
        }
        if(packet instanceof EntitySpawn0x17S2CPacket_P2) {
            onEntitySpawn((EntitySpawn0x17S2CPacket_P2)packet, netHandler);
        }
        if(packet instanceof MobSpawn0x18S2CPacket_P2) {
            onMobSpawn_P2((MobSpawn0x18S2CPacket_P2)packet, netHandler);
        }
        if(packet instanceof UpdateTileEntity0x3BPacket_P2) {
            onTileEntityUpdate((UpdateTileEntity0x3BPacket_P2)packet, netHandler);
        }
        if(packet instanceof SlotUpdate0x67S2CPacket_P7) {
            onSlotUpdate_P7((SlotUpdate0x67S2CPacket_P7)packet, netHandler);
        }
    }

    public static void onLoginRequest_P2(LoginRequest0x1Packet_P2 packet, PacketHandler handler) {
        LoginRequest0x1Packet packet1 = new LoginRequest0x1Packet(packet.username, packet.protocolVersion);
        packet1.worldSeed = 0;
        packet1.dimensionId = (byte)0;
        handler.onLoginRequest(packet1);
    }

    public static void onLoginRequest_P3(LoginRequest0x1Packet_P3 packet, PacketHandler handler) {
        LoginRequest0x1Packet packet1 = new LoginRequest0x1Packet(packet.username, packet.protocolVersion);
        packet1.worldSeed = packet.worldSeed;
        packet1.dimensionId = packet.dimensionId;
        handler.onLoginRequest(packet1);
    }

    public static void onEntityEquipment_P2(EntityEquipment0x5S2CPacket_P2 packet) {
        Minecraft mc = (Minecraft)FabricLoader.getInstance().getGameInstance();
        AbstractClientPlayer player = mc.player;
        if(packet.type == -1) {
            player.inventory.main = packet.items;
        } else if(packet.type == -2) {
            ((CraftingAccessor)((PlayerContainer)player.container).craftingInv).setContents(packet.items);
        } else if(packet.type == -3) {
            player.inventory.armour = packet.items;
        }
    }

    public static void onEntityEquipment_P7(EntityEquipment0x5S2CPacket_P7 packet, PacketHandler handler) {
        EntityEquipment0x5S2CPacket packet1 = new EntityEquipment0x5S2CPacket();
        packet1.entityId = packet.entityId;
        packet1.slot = packet.slot;
        packet1.itemId = packet.itemId;
        packet1.itemDamage = 0;
        handler.onEntityEquipment(packet1);
    }

    public static void onSlotUpdate_P7(SlotUpdate0x67S2CPacket_P7 packet, PacketHandler handler) {
        SlotUpdate0x67S2CPacket packet1 = new SlotUpdate0x67S2CPacket();
        packet1.containerId = packet.containerId;
        packet1.slotIndex = packet.slotIndex;
        packet1.stack = packet.stack;
        handler.onSlotUpdate(packet1);
    }

    public static void onPlayerHealthUpdate_P2(UpdatePlayerHealth0x8S2CPacket_P2 packet, PacketHandler handler) {
        UpdatePlayerHealth0x8S2CPacket packet1 = new UpdatePlayerHealth0x8S2CPacket();
        packet1.health = packet.health;
        handler.onPlayerHealthUpdate(packet1);
    }

    public static void onRespawn_P2(PacketHandler handler) {
        handler.onRespawn(new Respawn0x9C2SPacket((byte)0));
    }

    public static void handlePlayerHeldItemUpdate(HeldSlotChange0x10Packet_P2 packet, ClientPlayNetworkHandler handler) {
        EntityBase entity = ((ClientPlayNetworkHandlerAccessor)handler).getEntityById(packet.playerEntityId);
        if(entity == null) {
            return;
        }
        PlayerBase player = (PlayerBase)entity;
        int i = packet.itemId;
        if(i == 0) {
            player.inventory.main[player.inventory.selectedHotbarSlot] = null;
        } else {
            player.inventory.main[player.inventory.selectedHotbarSlot] = new ItemInstance(i, 1, 0);
        }
    }

    public static void onAddStack(AddStack0x11Packet_P2 packet) {
        Minecraft mc = (Minecraft)FabricLoader.getInstance().getGameInstance();
        mc.player.inventory.addStack(new ItemInstance(packet.id, packet.count, packet.durability));
    }

    public static void onItemEntitySpawn_P2(ItemEntitySpawn0x15Packet_P2 packet, ClientPlayNetworkHandler handler) {
        double d = (double)packet.x / 32D;
        double d1 = (double)packet.y / 32D;
        double d2 = (double)packet.z / 32D;
        Item entity = new Item(null, d, d1, d2, new ItemInstance(packet.itemId, packet.count, 0));
        entity.velocityX = (double)packet.rotation / 128D;
        entity.velocityY = (double)packet.pitch / 128D;
        entity.velocityZ = (double)packet.roll / 128D;
        entity.clientX = packet.x;
        entity.clientY = packet.y;
        entity.clientZ = packet.z;
        ((ClientPlayNetworkHandlerAccessor)handler).getLevel().method_1495(packet.entityId, entity);
    }

    public static void onEntitySpawn(EntitySpawn0x17S2CPacket_P2 packet, ClientPlayNetworkHandler handler)
    {
        EntitySpawn0x17S2CPacket packet1 = new EntitySpawn0x17S2CPacket();
        packet1.entityId = packet.entityId;
        packet1.x = packet.x;
        packet1.y = packet.y;
        packet1.z = packet.z;
        packet1.type = packet.type;
        packet1.flag = 0;
        handler.onEntitySpawn(packet1);
    }

    public static void onMobSpawn_P2(MobSpawn0x18S2CPacket_P2 packet, PacketHandler handler)
    {
        MobSpawn0x18S2CPacket packet1 = new MobSpawn0x18S2CPacket();
        packet1.entityId = packet.entityId;
        packet1.rawId = packet.rawId;
        packet1.x = packet.x;
        packet1.y = packet.y;
        packet1.z = packet.z;
        packet1.yaw = packet.yaw;
        packet1.pitch = packet.pitch;
        handler.onMobSpawn(packet1);
    }

    public static void onTileEntityUpdate(UpdateTileEntity0x3BPacket_P2 packet, ClientPlayNetworkHandler handler)
    {
        if(packet.entityNBT.getInt("x") != packet.x) {
            return;
        }
        if(packet.entityNBT.getInt("y") != packet.y) {
            return;
        }
        if(packet.entityNBT.getInt("z") != packet.z) {
            return;
        }
        TileEntityBase tile = ((ClientPlayNetworkHandlerAccessor)handler).getLevel().getTileEntity(packet.x, packet.y, packet.z);
        if(tile != null) {
            try {
                tile.readIdentifyingData(packet.entityNBT);
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            ((ClientPlayNetworkHandlerAccessor)handler).getLevel().method_202(packet.x, packet.y, packet.z, packet.x, packet.y, packet.z);
        }
    }
}
