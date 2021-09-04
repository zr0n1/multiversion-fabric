package zer0n1.multiversion.mixin;

import net.minecraft.inventory.Crafting;
import net.minecraft.item.ItemInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Crafting.class)
public interface CraftingAccessor
{
    @Accessor("contents")
    void setContents(ItemInstance[] items);
}