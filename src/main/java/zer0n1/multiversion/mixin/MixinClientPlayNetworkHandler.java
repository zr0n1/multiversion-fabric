package zer0n1.multiversion.mixin;

import net.minecraft.entity.EntityBase;
import net.minecraft.entity.player.ClientPlayer;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketHandler;
import net.minecraft.packet.*;
import net.minecraft.packet.login.LoginRequest0x1Packet;
import net.minecraft.packet.play.*;
import net.minecraft.server.network.ClientConnection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import zer0n1.multiversion.protocol.ProtocolManager;
import zer0n1.multiversion.protocol.packet.*;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class MixinClientPlayNetworkHandler extends PacketHandler
{
    @Shadow private boolean disconnected;
    @Shadow private ClientConnection netHandler;

    @Inject(method = "sendPacket(Lnet/minecraft/packet/AbstractPacket;)V", at = @At("HEAD"), cancellable = true)
    public void injectSendPacket(AbstractPacket packet, CallbackInfo ci) {
        AbstractPacket packet1 = null;
        int version = ProtocolManager.version();
        System.out.println(packet.toString());
        System.out.println(packet.getPacketID());
        System.out.println(version);

        if(packet instanceof LoginRequest0x1Packet) {
            LoginRequest0x1Packet login = (LoginRequest0x1Packet)packet;
            if(version < 11 && version > 2) {
                packet1 = new LoginRequest0x1Packet_P3(login.username, "Password", version);
            } else if(version == 2) {
                packet1 = new LoginRequest0x1Packet_P2(login.username, "Password", 2);
            } else if(version > 10) {
                login.protocolVersion = version;
                packet1 = login;
            }
        }
        if(version > 12) {
            return;
        }

        if(packet instanceof EntityInteract0x7C2SPacket && version < 4) {
            ci.cancel();
            return;
        }

        if(packet instanceof Respawn0x9C2SPacket && version < 13) {
            packet1 = new Respawn0x9Packet_P2();
        }

        if(packet instanceof PlayerDigging0xEC2SPacket && version < 9) {
            PlayerDigging0xEC2SPacket id0xE = (PlayerDigging0xEC2SPacket)packet;
            if(id0xE.status == 2 && !(id0xE.x == 0 && id0xE.y == 0 && id0xE.z == 0 && id0xE.direction == 0)) {
                ci.cancel();
                return;
            }
        }

        if(packet instanceof PlaceBlock0xFC2SPacket && version < 8) {
            PlaceBlock0xFC2SPacket id0xF = (PlaceBlock0xFC2SPacket)packet;
            int id = id0xF.stack != null ? id0xF.stack.itemId : -1;
            packet1 = new PlaceBlock0xFC2SPacket_P2(id, id0xF.x, id0xF.y, id0xF.z, id0xF.direction);
        }

        if(packet instanceof HeldSlotChange0x10C2SPacket && version < 3) {
            HeldSlotChange0x10C2SPacket id0x10 = (HeldSlotChange0x10C2SPacket)packet;
            packet1 = new HeldSlotChange0x10Packet_P2(0, id0x10.slotIndex);
        }

        if(packet instanceof EntityAction0x13C2SPacket && version < 8) {
            EntityAction0x13C2SPacket id0x13 = (EntityAction0x13C2SPacket) packet;
            EntityBase entity = method_1645(id0x13.entityId);
            if(entity instanceof ClientPlayer && (id0x13.action == 1 || id0x13.action == 2)) {
                packet1 = new Animation0x12C2SPacket(entity, id0x13.action == 1 ? 104 : 105);
            }
        }

        if(packet instanceof SlotClicked0x66C2SPacket && version < 11 && version > 6) {
            SlotClicked0x66C2SPacket id0x66 = (SlotClicked0x66C2SPacket)packet;
            packet1 = new SlotClicked0x66C2SPacket_P7(id0x66.containerId, id0x66.slotIndex, id0x66.rightClick, id0x66.stack, id0x66.actionType);
        }

        if(packet instanceof CloseContainer0x65C2SPacket && version < 7) {
            ci.cancel();
            return;
        }

        if(packet instanceof Transaction0x6AS2CPacket && version < 7) {
            ci.cancel();
            return;
        }

        if(packet instanceof UpdateSign0x82C2SPacket && version < 7) {
            ci.cancel();
            return;
        }

        if(!disconnected && packet1 != null) {
            netHandler.addPacketToQueue(packet1);
            ci.cancel();
        }
    }

    @Inject(method = "onAnimation(Lnet/minecraft/packet/play/Animation0x12C2SPacket;)V", locals = LocalCapture.CAPTURE_FAILHARD, at = @At("TAIL"), cancellable = true)
    public void injectOnAnimation(Animation0x12C2SPacket packet, CallbackInfo ci, EntityBase var2) {
        if(var2 != null) {
            if (packet.animationType == 104) {
                PlayerBase player = (PlayerBase)var2;
                ((EntityBaseAccessor) player).invokeSetFlag(1, true);
            } else if (packet.animationType == 105) {
                PlayerBase player = (PlayerBase)var2;
                ((EntityBaseAccessor) player).invokeSetFlag(1, false);
            }
        }
    }

    @Shadow abstract EntityBase method_1645(int id);
}
