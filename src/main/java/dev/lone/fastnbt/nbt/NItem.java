package dev.lone.fastnbt.nbt;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public class NItem extends NCompound<ItemStack>
{
    private boolean isConvertedCopy;
    @Nullable
    private ItemStack original;

    public NItem(ItemStack itemStack)
    {
        if (!NBT.isInstanceOfCraftItemStack(itemStack))
        {
            this.handle = NBT.item.convert(itemStack);
            this.original = itemStack;
            this.isConvertedCopy = true;
        }
        else
            this.handle = itemStack;
        this.handler = NBT.item;
    }

    public ItemStack getItem()
    {
        return handle;
    }

    /**
     * Items in inventories and in the world are already implementing
     * CraftItemStack NMS class.
     * Other items created with Bukkit API and in some other cases
     * do not implement the needed CraftItemStack, so we have to
     * use a temporary copy of the item and apply the changes later.
     */
    public boolean isConvertedCopy()
    {
        return isConvertedCopy;
    }

    /**
     * Applies changes to the original ItemStack if needed.
     */
    public void save()
    {
        if(isConvertedCopy && original != null)
            this.original.setItemMeta(this.handle.getItemMeta());
    }

    /**
     * Refreshes copy if needed. Only if (isConvertedCopy == true)
     */
    public void refreshCopy()
    {
        if(isConvertedCopy)
            this.handle = NBT.item.convert(original);
    }

    public static boolean hasNBT(ItemStack itemStack)
    {
        return NBT.item.hasNbt(itemStack);
    }

    public void merge(ItemStack b)
    {
        this.merge(new NItem(b));
    }
    public void merge(NItem b)
    {
        this.merge(b);
    }

    public static ItemStack asBukkitMirror(ItemStack itemStack)
    {
        return NBT.item.asBukkitMirror(itemStack);
    }

    public static Object asNMSCopy(ItemStack itemStack)
    {
        return NBT.item.asNMSCopy(itemStack);
    }

    public static ItemStack compoundToItemStack(NCompound compound)
    {
        return NBT.item.compoundToItemStack(compound.getInternal());
    }

    public static ItemStack compoundToItemStack(Object internalCompound)
    {
        return NBT.item.compoundToItemStack(internalCompound);
    }
}
