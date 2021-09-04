package zer0n1.multiversion.mixin;

import net.minecraft.block.BlockBase;
import net.minecraft.entity.EntityBase;
import net.minecraft.entity.Item;
import net.minecraft.entity.animal.AnimalBase;
import net.minecraft.entity.animal.Sheep;
import net.minecraft.entity.player.ClientPlayer;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import zer0n1.multiversion.protocol.ProtocolManager;
import zer0n1.multiversion.protocol.packet.ItemEntitySpawn0x15Packet_P2;

@Mixin(Sheep.class)
public abstract class MixinSheep extends AnimalBase
{
    public MixinSheep(Level arg) {
        super(arg);
    }

    @Inject(method = "damage(Lnet/minecraft/entity/EntityBase;I)Z", at = @At("HEAD"), cancellable = true)
    public void injectDamage(EntityBase target, int amount, CallbackInfoReturnable<Boolean> cir) {
        if(ProtocolManager.version() < 8 && entity != null && entity instanceof ClientPlayer && !isSheared()) {
            setSheared(true);
            method_1312();
            level.playSound(this, getHurtSound(), getSoundVolume(), (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
            int i1 = 2 + rand.nextInt(3);
            for(int j = 0; j < i1; j++)
            {
                Item item = new Item(level, x, y + 1.0, z, new ItemInstance(BlockBase.WOOL));
                item.velocityX += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
                item.velocityY += rand.nextFloat() * 0.05F;
                item.velocityZ += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
                item.pickupDelay = 10;
                ((ClientPlayer) entity).networkHandler.sendPacket(new ItemEntitySpawn0x15Packet_P2(item));
            }
            cir.setReturnValue(true);
        }
    }

    @Shadow public abstract void setSheared(boolean flag);
    @Shadow public abstract boolean isSheared();
}