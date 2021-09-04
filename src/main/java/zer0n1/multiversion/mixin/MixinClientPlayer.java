package zer0n1.multiversion.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.Session;
import net.minecraft.entity.Item;
import net.minecraft.entity.player.AbstractClientPlayer;
import net.minecraft.entity.player.ClientPlayer;
import net.minecraft.level.Level;
import net.minecraft.network.ClientPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import zer0n1.multiversion.protocol.ProtocolManager;
import zer0n1.multiversion.protocol.packet.ItemEntitySpawn0x15Packet_P2;

@Mixin(ClientPlayer.class)
public abstract class MixinClientPlayer extends AbstractClientPlayer
{
    @Shadow public ClientPlayNetworkHandler networkHandler;

    public MixinClientPlayer(Minecraft minecraft, Level level, Session session, int dimensionId) {
        super(minecraft, level, session, dimensionId);
    }

    @Inject(method = "spawnItem(Lnet/minecraft/entity/Item;)V", at = @At("HEAD"))
    public void injectSpawnItem(Item item, CallbackInfo ci) {
        if(ProtocolManager.version() < 8) {
            ItemEntitySpawn0x15Packet_P2 packet = new ItemEntitySpawn0x15Packet_P2(item);
            networkHandler.sendPacket(packet);
            item.x = (double)packet.x / 32D;
            item.y = (double)packet.y / 32D;
            item.z = (double)packet.z / 32D;
            item.velocityX = (double)packet.rotation / 128D;
            item.velocityY = (double)packet.pitch / 128D;
            item.velocityZ = (double)packet.roll / 128D;
        }
    }

    @Inject(method = "dropSelectedItem()V", at = @At("HEAD"), cancellable = true)
    public void injectDropSelectedItem(CallbackInfo ci) {
        if(ProtocolManager.version() < 7) {
            super.dropSelectedItem();
            ci.cancel();
        }
    }
}
