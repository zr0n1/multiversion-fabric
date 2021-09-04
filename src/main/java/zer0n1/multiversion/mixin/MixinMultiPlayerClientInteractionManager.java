package zer0n1.multiversion.mixin;

import org.objectweb.asm.Opcodes;
import net.minecraft.client.BaseClientInteractionManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MultiPlayerClientInteractionManager;
import net.minecraft.packet.play.PlayerDigging0xEC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import zer0n1.multiversion.protocol.ProtocolManager;

@Mixin(MultiPlayerClientInteractionManager.class)
public abstract class MixinMultiPlayerClientInteractionManager extends BaseClientInteractionManager
{
    @Shadow private int field_2614;
    @Shadow private boolean field_2615;
    public MixinMultiPlayerClientInteractionManager(Minecraft minecraft) {
        super(minecraft);
    }

    @Inject(method = "method_1716(IIII)Z", at = @At("HEAD"))
    public void injectBlockRemove(int i, int j, int k, int i1, CallbackInfoReturnable<Boolean> cir) {
        if(ProtocolManager.version() < 9) {
            minecraft.getNetworkHandler().sendPacket(new PlayerDigging0xEC2SPacket(3, i, j, k, i1));
        }
    }

    @Inject(method = "method_1721(IIII)V", at = @At(value = "INVOKE", shift = At.Shift.AFTER,
            target = "Lnet/minecraft/client/MultiPlayerClientInteractionManager;method_1997()V"))
    public void injectBlockRemoving(int i, int j, int k, int i1, CallbackInfo ci) {
        if(ProtocolManager.version() < 9) {
            minecraft.getNetworkHandler().sendPacket(new PlayerDigging0xEC2SPacket(1, i, j, k, i1));
        }
    }

    @Inject(method = "cancelBlockRemoving()V", at = @At("HEAD"), cancellable = true)
    public void injectCancelBlockRemoving(CallbackInfo ci) {
        if(ProtocolManager.version() < 9 && field_2615) {
            minecraft.getNetworkHandler().sendPacket(new PlayerDigging0xEC2SPacket(2, 0, 0, 0, 0));
            field_2614 = 0;
        }
    }

}
