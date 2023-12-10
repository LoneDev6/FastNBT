package dev.lone.fastnbt.nms.nbt.nms;

import org.bukkit.inventory.ItemStack;

public interface ICraftItemStack<NBTLIST, NBTCOMPOUND, CRAFTITEMSTACK extends ItemStack> extends ICompound<ItemStack, NBTLIST, NBTCOMPOUND>
{
    CRAFTITEMSTACK convertToCraft(ItemStack itemStack);
    boolean hasNbt(ItemStack itemStack);
    void merge(ItemStack itemStack, ItemStack otherItem);

    default boolean hasItemMeta(ItemStack itemStack)
    {
        return itemStack.hasItemMeta();
    }
    ItemStack asCraftMirror(ItemStack itemStack);
    Object asNmsCopy(ItemStack itemStack);
    ItemStack compoundToItemStack(NBTCOMPOUND itemStack);
    NBTCOMPOUND itemStackToCompound(ItemStack itemStack);
    ItemStack compoundStrToBukkit(String json);
    default String bukkitItemToCompoundStr(ItemStack bukkitItem)
    {
        return toString(bukkitItem);
    }

    boolean isInstanceOfCraftItemStack(ItemStack bukkitItem);
}
