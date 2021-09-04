package zer0n1.multiversion.mixin;

import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.Egg;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import zer0n1.multiversion.protocol.ProtocolManager;

@Mixin(Egg.class)
public class MixinEgg
{
    @Inject(method =
            "use(Lnet/minecraft/item/ItemInstance;Lnet/minecraft/level/Level;Lnet/minecraft/entity/player/PlayerBase;)Lnet/minecraft/item/ItemInstance;",
    at = @At("HEAD"), cancellable = true)
    public void injectUse(ItemInstance item, Level level, PlayerBase player, CallbackInfoReturnable<ItemInstance> cir) {
        if(ProtocolManager.version() < 7) {
            cir.setReturnValue(item);
        }
    }
}
