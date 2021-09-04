package zer0n1.multiversion.mixin;

import net.minecraft.entity.EntityBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(EntityBase.class)
public interface EntityBaseAccessor
{
    @Invoker("method_1326")
    void invokeSetFlag(int i, boolean flag);
}
