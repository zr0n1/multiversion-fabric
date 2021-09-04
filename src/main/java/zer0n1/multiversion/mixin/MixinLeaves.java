package zer0n1.multiversion.mixin;

import net.minecraft.block.Leaves;
import net.minecraft.block.LeavesBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.render.block.FoliageColour;
import net.minecraft.level.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import zer0n1.multiversion.protocol.ProtocolManager;

@Mixin(Leaves.class)
public abstract class MixinLeaves extends LeavesBase
{
    protected MixinLeaves(int id, int texture, Material arg, boolean flag) {
        super(id, texture, arg, flag);
    }

    @Inject(method = "getBaseColour(I)I", at = @At("HEAD"), cancellable = true)
    public void injectGetBaseColor(int i, CallbackInfoReturnable<Integer> cir) {
        if(ProtocolManager.version() == 2) {
            cir.setReturnValue(super.getBaseColour(i));
        }
        if(ProtocolManager.version() < 8) {
            cir.setReturnValue(FoliageColour.method_1083());
        }
    }

    @Inject(method = "getColourMultiplier(Lnet/minecraft/level/BlockView;III)I", at = @At("HEAD"), cancellable = true)
    public void injectGetColourMultiplier(BlockView tileView, int x, int y, int z, CallbackInfoReturnable<Integer> cir) {
        if(ProtocolManager.version() == 2) {
            cir.setReturnValue(super.getColourMultiplier(tileView, x, y, z));
        }
        if(ProtocolManager.version() < 8) {
            tileView.getBiomeSource().getBiomes(x, z, 1, 1);
            double temperature = tileView.getBiomeSource().temperatureNoises[0];
            double rainfall = tileView.getBiomeSource().rainfallNoises[0];
            cir.setReturnValue(FoliageColour.method_1080(temperature, rainfall));
        }
    }
}
