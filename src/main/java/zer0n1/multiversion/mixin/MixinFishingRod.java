package zer0n1.multiversion.mixin;

import net.minecraft.entity.FishHook;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.tool.FishingRod;
import net.minecraft.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import zer0n1.multiversion.protocol.ProtocolManager;

@Mixin(FishingRod.class)
public abstract class MixinFishingRod extends ItemBase
{
    protected MixinFishingRod(int id) {
        super(id);
    }

    @Inject(method =
            "use(Lnet/minecraft/item/ItemInstance;Lnet/minecraft/level/Level;Lnet/minecraft/entity/player/PlayerBase;)Lnet/minecraft/item/ItemInstance;",
    at = @At("HEAD"), cancellable = true)
    public void injectUse(ItemInstance item, Level level, PlayerBase player, CallbackInfoReturnable<ItemInstance> cir) {
        if(ProtocolManager.version() < 5) {
            if (player.fishHook != null) {
                int var4 = player.fishHook.method_956();
                item.applyDamage(var4, player);
            } else {
                level.playSound(player, "random.bow", 0.5F, 0.4F / (rand.nextFloat() * 0.4F + 0.8F));
                level.spawnEntity(new FishHook(level, player));
            }
            player.swingHand();
            cir.setReturnValue(item);
        }
    }
}
