package zer0n1.multiversion.mixin;

import net.minecraft.block.BlockBase;
import net.minecraft.block.MaterialBlock;
import net.minecraft.block.material.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import zer0n1.multiversion.protocol.ProtocolManager;

@Mixin(MaterialBlock.class)
public abstract class MixinMaterialBlock extends BlockBase
{
    protected MixinMaterialBlock(int id, Material material) {
        super(id, material);
    }

    @Inject(method = "getTextureForSide(I)I", at = @At("HEAD"), cancellable = true)
    public void injectGetTextureForSide(int side, CallbackInfoReturnable<Integer> cir) {
        if(ProtocolManager.version() == 2) {
            if(side == 0) {
                cir.setReturnValue(texture + 32);
            } else if(side == 1) {
                cir.setReturnValue(texture);
            } else {
                cir.setReturnValue(texture + 16);
            }
        }
    }
}
