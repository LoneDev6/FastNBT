package dev.lone.fastnbt.nms.nbt;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public interface ICraftItemStack<ListTag, CompoundTag, CraftItemStack extends ItemStack> extends ICompoundTag<ItemStack, ListTag, CompoundTag>
{
    CraftItemStack convertToCraft(ItemStack itemStack);
    boolean hasNBT(ItemStack itemStack);
    void merge(ItemStack itemStack, ItemStack otherItem);

    default boolean hasItemMeta(ItemStack itemStack)
    {
        return itemStack.hasItemMeta();
    }
    ItemStack asCraftMirror(ItemStack itemStack);
    Object asNmsCopy(ItemStack itemStack);
    @Nullable ItemStack compoundToItemStack(CompoundTag itemStack);
    CompoundTag itemStackToCompound(ItemStack itemStack);
    @Nullable ItemStack compoundStrToBukkit(String json);
    default String bukkitItemToCompoundStr(ItemStack bukkitItem)
    {
        return toString(bukkitItem);
    }

    boolean isInstanceOfCraftItemStack(ItemStack bukkitItem);
}
