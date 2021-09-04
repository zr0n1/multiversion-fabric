package zer0n1.multiversion.mixin;

import net.minecraft.block.Workbench;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import zer0n1.multiversion.protocol.ProtocolManager;

@Mixin(Workbench.class)
public class MixinWorkbench
{
    @Inject(method = "canUse(Lnet/minecraft/level/Level;IIILnet/minecraft/entity/player/PlayerBase;)Z", at = @At("HEAD"))
    public void injectCanUse(Level level, int x, int y, int z, PlayerBase player, CallbackInfoReturnable<Boolean> cir) {
        if(ProtocolManager.version() < 7) {
            player.openCraftingScreen(x, y, z);
        }
    }
}
