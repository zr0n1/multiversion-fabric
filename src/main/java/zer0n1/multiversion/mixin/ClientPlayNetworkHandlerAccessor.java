package zer0n1.multiversion.mixin;

import net.minecraft.client.level.ClientLevel;
import net.minecraft.entity.EntityBase;
import net.minecraft.network.ClientPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ClientPlayNetworkHandler.class)
public interface ClientPlayNetworkHandlerAccessor
{
    @Accessor("level") void setLevel(ClientLevel level);

    @Accessor("level") ClientLevel getLevel();

    @Invoker("method_1645") EntityBase getEntityById(int id);
}
