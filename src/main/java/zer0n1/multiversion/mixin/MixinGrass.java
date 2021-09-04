package zer0n1.multiversion.mixin;

import net.minecraft.block.BlockBase;
import net.minecraft.block.Grass;
import net.minecraft.block.material.Material;
import net.minecraft.level.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import zer0n1.multiversion.protocol.ProtocolManager;

@Mixin(Grass.class)
public abstract class MixinGrass extends BlockBase
{
    protected MixinGrass(int id, Material material) {
        super(id, material);
    }

    @Inject(method = "getColourMultiplier(Lnet/minecraft/level/BlockView;III)I", at = @At("HEAD"), cancellable = true)
    public void injectGetColourMultiplier(BlockView tileView, int x, int y, int z, CallbackInfoReturnable<Integer> cir) {
        if(ProtocolManager.version() == 2) {
            cir.setReturnValue(super.getColourMultiplier(tileView, x, y, z));
        }
    }
}
