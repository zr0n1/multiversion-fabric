package zer0n1.multiversion.mixin;

import net.minecraft.block.Chest;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.inventory.InventoryBase;
import net.minecraft.level.Level;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import zer0n1.multiversion.protocol.ProtocolManager;

@Mixin(Chest.class)
public class MixinChest
{
    @Inject(method = "canUse(Lnet/minecraft/level/Level;IIILnet/minecraft/entity/player/PlayerBase;)Z", locals = LocalCapture.CAPTURE_FAILHARD,
            at = @At(value = "FIELD", target = "Lnet/minecraft/level/Level;isClient:Z", opcode = Opcodes.GETFIELD, shift = At.Shift.BEFORE))
    public void injectCanUse(Level level, int x, int y, int z, PlayerBase player, CallbackInfoReturnable<Boolean> cir, Object var6) {
        if(ProtocolManager.version() < 7) {
            player.openChestScreen((InventoryBase)var6);
        }
    }
}
