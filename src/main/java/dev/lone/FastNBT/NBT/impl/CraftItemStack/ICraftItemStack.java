package dev.lone.FastNBT.NBT.impl.CraftItemStack;

import dev.lone.FastNBT.NBT.impl.NBT.ICompound;
import org.bukkit.inventory.ItemStack;

public interface ICraftItemStack<NBTLIST, NBTCOMPOUND, CRAFTITEMSTACK extends ItemStack> extends ICompound<ItemStack, NBTLIST, NBTCOMPOUND>
{
    CRAFTITEMSTACK convert(ItemStack itemStack);
}
