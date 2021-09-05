package zer0n1.multiversion.mixin;

import net.minecraft.packet.AbstractPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import zer0n1.multiversion.protocol.ProtocolManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

@Mixin(AbstractPacket.class)
public class MixinAbstractPacket
{
    @Inject(method = "writeString(Ljava/lang/String;Ljava/io/DataOutputStream;)V", cancellable = true,
            at = @At(value = "INVOKE", target = "Ljava/io/DataOutputStream;writeShort(I)V", shift = At.Shift.BEFORE))
    private static void injectWriteString(String string, DataOutputStream stream, CallbackInfo ci) throws IOException {
        if(ProtocolManager.version() < 11) {
            stream.writeUTF(string);
            ci.cancel();
        }
    }
    @Inject(method = "readString(Ljava/io/DataInputStream;I)Ljava/lang/String;", at = @At("HEAD"), cancellable = true)
    private static void injectReadString(DataInputStream stream, int maxLength, CallbackInfoReturnable<String> cir) throws IOException {
        if(ProtocolManager.version() < 11) {
            cir.setReturnValue(stream.readUTF());
        }
    }
}
