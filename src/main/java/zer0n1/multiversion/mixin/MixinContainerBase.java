package zer0n1.multiversion.mixin;

import net.minecraft.container.ContainerBase;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import zer0n1.multiversion.protocol.ProtocolManager;

@Mixin(ContainerBase.class)
public class MixinContainerBase
{
    @Inject(method = "clickSlot(IIZLnet/minecraft/entity/player/PlayerBase;)Lnet/minecraft/item/ItemInstance;", at = @At("HEAD"))
    public void injectClickSlot(int i, int j, boolean flag, PlayerBase arg, CallbackInfoReturnable<ItemInstance> cir) {
        if(ProtocolManager.version() < 11) {
            flag = false;
        }
    }
}
