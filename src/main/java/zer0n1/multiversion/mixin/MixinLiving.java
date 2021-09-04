package zer0n1.multiversion.mixin;

import net.minecraft.util.maths.MathHelper;
import net.minecraft.block.BlockBase;
import net.minecraft.entity.EntityBase;
import net.minecraft.entity.Living;
import net.minecraft.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import zer0n1.multiversion.protocol.ProtocolManager;

@Mixin(Living.class)
public abstract class MixinLiving extends EntityBase
{
    public MixinLiving(Level level) {
        super(level);
    }

    @Inject(method = "method_932()Z", cancellable = true, at = @At("HEAD"))
    public void injectIsOnLadder(CallbackInfoReturnable<Boolean> cir) {
        if(ProtocolManager.version() < 11) {
            int blockX = MathHelper.floor(this.x);
            int blockY = MathHelper.floor(this.boundingBox.minY);
            int blockZ = MathHelper.floor(this.z);
            boolean b = (level.getTileId(blockX, blockY, blockZ) == BlockBase.LADDER.id || level.getTileId(blockX, blockY + 1, blockZ) == BlockBase.LADDER.id);
            cir.setReturnValue(b);
        }
    }
}
