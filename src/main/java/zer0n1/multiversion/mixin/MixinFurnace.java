package zer0n1.multiversion.mixin;

import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Furnace;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.level.BlockView;
import net.minecraft.level.Level;
import net.minecraft.tileentity.TileEntityFurnace;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import zer0n1.multiversion.protocol.ProtocolManager;

@Mixin(Furnace.class)
public abstract class MixinFurnace extends BlockWithEntity
{
    protected MixinFurnace(int i, Material arg) {
        super(i, arg);
    }

    @Inject(method = "canUse(Lnet/minecraft/level/Level;IIILnet/minecraft/entity/player/PlayerBase;)Z", at = @At("HEAD"))
    public void injectCanUse(Level level, int x, int y, int z, PlayerBase player, CallbackInfoReturnable<Boolean> cir) {
        if(ProtocolManager.version() < 7) {
            player.openFurnaceScreen((TileEntityFurnace)level.getTileEntity(x, y, z));
        }
    }

    @Inject(method = "getTextureForSide(Lnet/minecraft/level/BlockView;IIII)I", at = @At("HEAD"), cancellable = true)
    public void injectGetTextureForSide(BlockView tileView, int x, int y, int z, int meta, CallbackInfoReturnable<Integer> cir) {
        if(meta == 1 || meta == 0 && ProtocolManager.version() < 8) {
            cir.setReturnValue(1);
        }
    }
    @Inject(method = "getTextureForSide(I)I", at = @At("HEAD"), cancellable = true)
    public void injectGetTextureForSide(int side, CallbackInfoReturnable<Integer> cir) {
        if(side == 1 || side == 0 && ProtocolManager.version() < 8) {
            cir.setReturnValue(1);
        }
    }
}
