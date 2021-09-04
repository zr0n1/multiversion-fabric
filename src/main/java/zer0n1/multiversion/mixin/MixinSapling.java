package zer0n1.multiversion.mixin;

import net.minecraft.block.Plant;
import net.minecraft.block.Sapling;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import zer0n1.multiversion.protocol.ProtocolManager;

@Mixin(Sapling.class)
public abstract class MixinSapling extends Plant
{
    protected MixinSapling(int id, int texture) {
        super(id, texture);
    }

    @Inject(method = "getTextureForSide(II)I", at = @At("HEAD"), cancellable = true)
    public void injectGetTextureForSide(int side, int meta, CallbackInfoReturnable<Integer> cir) {
        if(ProtocolManager.version() < 11) {
            cir.setReturnValue(super.getTextureForSide(side, meta));
        }
    }
}
