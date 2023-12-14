package dev.lone.fastnbt.nms.nbt;

import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.UUID;

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
        if(!isValidJson(tag))
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

    public static boolean isValidJson(String json)
    {
        try
        {
            return new JsonParser().parse(json).getAsJsonObject() != null;
        }
        catch (Throwable ignored) {}
        return false;
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

    public void setDisplayNameCompound(String compoundString)
    {
        getOrAddCompound("display").setString("Name", compoundString);
    }

    public void setLoreCompounds(List<String> compoundStrings)
    {
        NCompound display = getOrAddCompound("display");
        NList lore = display.getOrAddList("Lore", NBTType.String);
        for (String compound : compoundStrings)
            lore.addString(compound);
    }

    public void setAttributeModifier(String attributeName,
                                     int operation,
                                     double amount,
                                     String name,
                                     String slot,
                                     int uuidLeast,
                                     int uuidMost)
    {
        NList attributes = getOrAddList("AttributeModifiers", NBTType.Compound);
        NCompound attribute = attributes.addCompound();
        attribute.setString("AttributeName", attributeName);
        attribute.setInt("Operation", operation);
        attribute.setInt("UUIDLeast", uuidLeast);
        attribute.setInt("UUIDMost", uuidMost);
        attribute.setDouble("Amount", amount);
        attribute.setString("Name", name);
        attribute.setString("Slot", slot);
    }

    public void setEnchantment(String id, short level)
    {
        NList compoundList = getOrAddList("Enchantments", NBTType.Compound);
        setEnchantment(compoundList, id, level);
    }

    public void addEnchantments(Map<String, Short> enchantments)
    {
        addEnchantments0(enchantments);
    }

    public void setEnchantments(Map<String, Short> enchantments)
    {
        removeEnchantments();
        addEnchantments0(enchantments);
    }

    public void removeEnchantments()
    {
        remove("Enchantments");
    }

    public void removeEnchantment(String id)
    {
        if(!hasKey("Enchantments"))
            return;

        NList compoundList = getOrAddList("Enchantments", NBTType.Compound);
        if(!compoundList.isEmpty())
        {
            for (int i = 0; i < compoundList.size(); i++)
            {
                NCompound enchant = compoundList.getOrAddCompound(i);
                if(!enchant.getString("id").equals(id))
                    continue;

                compoundList.remove(i);
                return;
            }
        }
    }

    private void addEnchantments0(Map<String, Short> enchantments)
    {
        NList compoundList = getOrAddList("Enchantments", NBTType.Compound);
        for (Map.Entry<String, Short> entry : enchantments.entrySet())
        {
            String id = entry.getKey();
            Short level = entry.getValue();
            setEnchantment(compoundList, id, level);
        }
    }

    private void setEnchantment(NList compoundList, String id, Short level)
    {
        if(!compoundList.isEmpty())
        {
            for (int i = 0; i < compoundList.size(); i++)
            {
                NCompound enchant = compoundList.getOrAddCompound(i);
                if(!enchant.getString("id").equals(id))
                    continue;
                if(enchant.getShort("lvl") == level)
                    return;

                enchant.setShort("lvl", level);
                return;
            }
        }

        NCompound enchant = new NCompound();
        compoundList.addCompound(enchant);
        enchant.setString("id", id);
        enchant.setShort("lvl", level);
    }

    public void setSkull(String name, UUID uuid, String value, String signature)
    {
        setType(Material.PLAYER_HEAD);
        NCompound owner = getOrAddCompound("SkullOwner");
        owner.setString("Name", name);
        owner.setUUID("Id", uuid);

        NList textures = owner.getOrAddCompound("Properties").getOrAddList("textures", NBTType.Compound);
        NCompound profile = new NCompound();
        textures.addCompound(profile);
        profile.setString("Value", value);
        profile.setString("Signature", signature);
    }

    public void setSkull(String name, String value)
    {
        setType(Material.PLAYER_HEAD);
        NCompound owner = getOrAddCompound("SkullOwner");
        owner.setString("Name", name);
        owner.setUUID("Id", UUID.nameUUIDFromBytes(name.getBytes()));

        NList textures = owner.getOrAddCompound("Properties").getOrAddList("textures", NBTType.Compound);
        NCompound profile = new NCompound();
        textures.addCompound(profile);
        profile.setString("Value", value);
    }

    @Nullable
    public String getSkullTextureName()
    {
        NCompound compound = getSkullOwnerCompound();
        if(compound == null)
            return null;
        String name = compound.getString("Name");
        if("".equals(name))
            return null;
        return name;
    }

    @Nullable
    public UUID getSkullTextureUUID()
    {
        NCompound compound = getSkullOwnerCompound();
        if(compound == null)
            return null;
        return compound.getUUID("Id");
    }

    @Nullable
    public String getSkullTextureSignature()
    {
        NCompound compound = getSkullTexturesCompound();
        if(compound == null)
            return null;
        String signature = compound.getString("Signature");
        if("".equals(signature))
            return null;
        return signature;
    }

    @Nullable
    public String getSkullTextureValue()
    {
        NCompound compound = getSkullTexturesCompound();
        if(compound == null)
            return null;
        String value = compound.getString("Value");
        if("".equals(value))
            return null;
        return value;
    }

    @Nullable
    private NCompound getSkullOwnerCompound()
    {
        if(!hasKey("SkullOwner"))
            return null;
        return getCompound("SkullOwner");
    }

    @Nullable
    private NCompound getSkullTexturesCompound()
    {
        NCompound compound = getSkullOwnerCompound();
        if(compound == null)
            return null;
        NCompound properties = compound.getCompound("Properties");
        if(properties == null)
            return null;
        NList textures = properties.getList("textures", NBTType.Compound);
        if(textures == null)
            return null;
        if(textures.isEmpty())
            return null;
        return textures.getOrAddCompound(0);
    }
}
