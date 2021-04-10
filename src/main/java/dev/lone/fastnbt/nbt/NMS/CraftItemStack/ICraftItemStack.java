package dev.lone.fastnbt.nbt.NMS.CraftItemStack;

import dev.lone.fastnbt.nbt.NMS.Compound.ICompound;
import org.bukkit.inventory.ItemStack;

public interface ICraftItemStack<NBTLIST, NBTCOMPOUND, CRAFTITEMSTACK extends ItemStack> extends ICompound<ItemStack, NBTLIST, NBTCOMPOUND>
{
    CRAFTITEMSTACK convert(ItemStack itemStack);
    boolean hasNbt(ItemStack itemStack);
    void merge(ItemStack itemStack, ItemStack otherItem);

    default boolean hasItemMeta(ItemStack itemStack)
    {
        return itemStack.hasItemMeta();
    }
    ItemStack asBukkitMirror(ItemStack itemStack);
    Object asNMSCopy(ItemStack itemStack);
    ItemStack compoundToItemStack(NBTCOMPOUND itemStack);
}
