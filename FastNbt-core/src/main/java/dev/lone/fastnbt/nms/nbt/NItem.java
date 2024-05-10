package dev.lone.fastnbt.nms.nbt;

import com.google.gson.JsonSyntaxException;
import dev.lone.fastnbt.nms.Version;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings({"unchecked", "deprecation", "unused"})
public class NItem extends NCompound
{
    private boolean isConvertedCopy;
    private ItemStack original;

    public NItem(@NotNull ItemStack itemStack)
    {
        if (!NBT.isInstanceOfCraftItemStack(itemStack))
        {
            this.handle = NBT.item.convertToCraft(itemStack);
            this.original = itemStack;
            this.isConvertedCopy = true;
        }
        else
            this.handle = itemStack;
        this.handler = NBT.item;
    }

    @Nullable
    public static NItem of(ItemStack itemStack)
    {
        if(itemStack == null)
            return null;
        return new NItem(itemStack);
    }

    public ItemStack getItem()
    {
        return (ItemStack) handle;
    }

    public void setType(Material type)
    {
        original.setType(type);
        refreshCopy();
    }

    /**
     * Items in inventories and in the world are already implementing CraftItemStack NMS class.
     * Other items created with Bukkit API and in some other cases do not implement CraftItemStack,
     * so we have to use a temporary copy of the item and apply the changes later using save() method.
     *
     * @return true if this item wasn't originally a CraftItemStack, otherwise returns false.
     */
    public boolean isConvertedCopy()
    {
        return isConvertedCopy;
    }

    /**
     * Applies changes to the original ItemStack if needed.
     * It only saves changes if the original item isn't a CraftItemStack.
     * Items in inventories and in the world are already implementing CraftItemStack NMS class.
     * Other items created with Bukkit API and in some other cases do not implement CraftItemStack.
     */
    public void save()
    {
        if (isConvertedCopy)
            original.setItemMeta(getItem().getItemMeta());
    }

    /**
     * Refreshes copy if needed. Only if it's a CraftItemStack copy.
     */
    public void refreshCopy()
    {
        if (isConvertedCopy)
            this.handle = NBT.item.convertToCraft(original);
    }

    public static boolean hasNBT(ItemStack itemStack)
    {
        return NBT.item.hasNBT(itemStack);
    }

    public void merge(ItemStack b)
    {
        merge(new NItem(b));
    }

    public void merge(NItem b)
    {
        NBT.item.merge((ItemStack) this.handle, (ItemStack) b.handle);
    }

    public boolean merge(String tag) throws JsonSyntaxException
    {
        if(!isValid(tag))
            return false;
        merge(setNbtByString(tag));
        save();
        return true;
    }

    /**
     * This is a hacky method to let Bukkit handle all the conversion shit.
     * It basically creates a temporary item with the specified NBT tag from a String.
     * Then you can merge the temporary item into your current item.
     *
     * @param nbtTag The String to convert to NBT tag
     * @return A temporary item with the specified NBT tag from a String.
     */
    private ItemStack setNbtByString(String nbtTag)
    {
        ItemStack tmp = new ItemStack(getItem().getType());
        Bukkit.getUnsafe().modifyItemStack(tmp, nbtTag);
        return tmp;
    }

    public static boolean isValid(String component)
    {
        return NBT.compound.isValid(component);
    }

    public static ItemStack asCraftMirror(ItemStack itemStack)
    {
        return NBT.item.asCraftMirror(itemStack);
    }

    @Nullable
    public static ItemStack compoundToBukkitItem(NCompound compound)
    {
        return NBT.item.compoundToItemStack(compound.getInternal());
    }

    @ApiStatus.Internal
    public static Object bukkitItemToNmsItem(ItemStack itemStack)
    {
        return NBT.item.asNmsCopy(itemStack);
    }

    @ApiStatus.Internal
    public static ItemStack nmsCompoundToBukkitItem(Object internalCompound)
    {
        return NBT.item.compoundToItemStack(internalCompound);
    }

    @ApiStatus.Internal
    public static Object bukkitItemToNmsCompound(ItemStack itemStack)
    {
        return NBT.item.itemStackToCompound(itemStack);
    }

    @Nullable
    public static ItemStack compoundStrToBukkitItem(String compoundString)
    {
        return NBT.item.compoundStrToBukkit(compoundString);
    }

    public static String bukkitItemToCompoundStr(ItemStack bukkitItem)
    {
        return NBT.item.bukkitItemToCompoundStr(bukkitItem);
    }

    public Object getCustomName()
    {
        return NBT.dataComponents.getCustomName(getItem());
    }

    public String getCustomNameJson()
    {
        return NBT.dataComponents.getCustomNameJson(getItem());
    }

    public void setCustomName(String compoundString)
    {
        if(Version.isAtLeast(Version.v1_20_4))
            NBT.dataComponents.setCustomName(getItem(), compoundString);
        else
            getOrAddCompound("display").setString("Name", compoundString);
    }

    public Object getItemName()
    {
        return NBT.dataComponents.getItemName(getItem());
    }

    public void setItemName(String compoundString)
    {
        NBT.dataComponents.setItemName(getItem(), compoundString);
    }

    @Nullable
    public List getLore()
    {
        return NBT.dataComponents.getLore(getItem());
    }

    @Deprecated
    public void setDisplayNameCompound(String compoundString)
    {
        setCustomName(compoundString);
    }
}
