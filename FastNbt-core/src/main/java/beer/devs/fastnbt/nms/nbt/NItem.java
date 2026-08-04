package beer.devs.fastnbt.nms.nbt;

import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
        if (isConvertedCopy)
        {
            original.setType(type);
            refreshCopy();
            return;
        }
        getItem().setType(type);
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
        save();
    }

    public boolean hasEnchantment(String name)
    {
        if (name == null)
            return false;

        Enchantment enchantment = Enchantment.getByName(name);
        if (enchantment == null && name.indexOf(':') >= 0)
        {
            NamespacedKey key = NamespacedKey.fromString(name);
            if (key != null)
                enchantment = Enchantment.getByKey(key);
        }
        if (enchantment == null)
            return false;

        String key = enchantment.getKey().toString();
        return NBT.dataComponents == null
                ? NBT.item.hasEnchantment(getItem(), key)
                : NBT.dataComponents.hasEnchantment(getItem(), key);
    }

    /**
     * Checks a lore line using Bukkit legacy text formatting (for example {@code \u00a7aText}), not JSON.
     *
     * @param line the complete legacy-formatted line
     * @return true when the item contains the line
     */
    public boolean hasLoreLegacy(String line)
    {
        if (line == null)
            return false;

        return NBT.dataComponents == null
                ? NBT.item.hasLore(getItem(), line)
                : NBT.dataComponents.hasLore(getItem(), line);
    }

    /**
     * @deprecated The argument is Bukkit legacy text, despite the ambiguous method name.
     * Use {@link #hasLoreLegacy(String)} or {@link #hasLoreJson(String)}.
     */
    @Deprecated
    public boolean hasLore(String line)
    {
        return hasLoreLegacy(line);
    }

    /**
     * Checks the custom name using Bukkit legacy text formatting (for example {@code \u00a7aText}), not JSON.
     *
     * @param name the complete legacy-formatted name
     * @return true when the custom name matches
     */
    public boolean hasDisplayNameLegacy(String name)
    {
        if (name == null)
            return false;

        return NBT.dataComponents == null
                ? NBT.item.hasDisplayName(getItem(), name)
                : NBT.dataComponents.hasDisplayName(getItem(), name);
    }

    /**
     * @deprecated The argument is Bukkit legacy text, despite the ambiguous method name.
     * Use {@link #hasDisplayNameLegacy(String)} or {@link #hasCustomNameJson(String)}.
     */
    @Deprecated
    public boolean hasDisplayName(String name)
    {
        return hasDisplayNameLegacy(name);
    }

    /**
     * Applies the server-native item data string. On 1.20.4 and older this is an NBT patch;
     * on 1.20.5 and newer it is a data-component patch.
     *
     * @deprecated The accepted syntax changes with the server version. Use
     * {@link #mergeLegacyNbt(String)} or {@link #mergeComponents(String)}.
     */
    @Deprecated
    public boolean merge(String tag) throws JsonSyntaxException
    {
        if(!isValid(tag))
            return false;
        merge(setNbtByString(tag));
        return true;
    }

    /**
     * Merges a legacy item NBT/SNBT patch on Minecraft 1.20.4 or older.
     *
     * @param snbt the legacy item NBT patch in SNBT syntax
     * @throws UnsupportedOperationException on data-component servers
     */
    public void mergeLegacyNbt(String snbt)
    {
        if (NBT.dataComponents != null)
            throw new UnsupportedOperationException("Legacy item NBT patches require Minecraft 1.20.4 or older.");
        merge(setNbtByString(snbt));
    }

    /**
     * Merges an item data-component patch on Minecraft 1.20.5 or newer.
     *
     * @param components the component patch accepted by the current server
     * @throws UnsupportedOperationException on legacy NBT servers
     */
    public void mergeComponents(String components)
    {
        if (NBT.dataComponents == null)
            throw new UnsupportedOperationException("Item data components require Minecraft 1.20.5 or newer.");
        merge(setNbtByString(components));
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

    /**
     * Validates the server-native item data syntax. The syntax is version-dependent.
     *
     * @deprecated Prefer the explicit merge method for the intended data format.
     */
    @Deprecated
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

    /**
     * Deserializes a complete item from SNBT.
     *
     * @param snbt the serialized item SNBT, not JSON
     * @return the item, or null when it cannot be decoded
     */
    @Nullable
    public static ItemStack itemFromSnbt(String snbt)
    {
        return NBT.item.compoundStrToBukkit(snbt);
    }

    /**
     * Serializes a complete item to SNBT.
     *
     * @param item the item to serialize
     * @return the serialized item SNBT, never JSON
     */
    @Nullable
    public static String itemToSnbt(ItemStack item)
    {
        return NBT.item.bukkitItemToCompoundStr(item);
    }

    /**
     * @deprecated Use {@link #itemFromSnbt(String)}; the input is SNBT, not a generic compound string.
     */
    @Deprecated
    @Nullable
    public static ItemStack compoundStrToBukkitItem(String compoundString)
    {
        return itemFromSnbt(compoundString);
    }

    /**
     * @deprecated Use {@link #itemToSnbt(ItemStack)}; the result is SNBT, not JSON.
     */
    @Deprecated
    @Nullable
    public static String bukkitItemToCompoundStr(ItemStack bukkitItem)
    {
        return itemToSnbt(bukkitItem);
    }

    /**
     * Returns the internal custom-name value. Its runtime type changes with the server version.
     *
     * @deprecated Use {@link #getCustomNameJson()} for a stable Paper/Spigot representation.
     */
    @Deprecated
    @Nullable
    public Object getCustomName()
    {
        if (NBT.dataComponents != null)
            return NBT.dataComponents.getCustomName(getItem());
        NCompound display = getCompound("display");
        return display == null ? null : display.getString("Name");
    }

    /**
     * Returns the custom name as a serialized vanilla JSON text component.
     *
     * @return the JSON component, or null when absent
     */
    @Nullable
    public String getCustomNameJson()
    {
        if (NBT.dataComponents != null)
            return NBT.dataComponents.getCustomNameJson(getItem());
        NCompound display = getCompound("display");
        return display == null ? null : display.getString("Name");
    }

    /**
     * Checks the custom name using JSON component semantics, ignoring insignificant JSON formatting.
     *
     * @param json the serialized vanilla JSON text component
     * @return true when the custom name is equivalent to the supplied component
     */
    public boolean hasCustomNameJson(String json)
    {
        return jsonEquals(getCustomNameJson(), json);
    }

    /**
     * Sets the custom name from a serialized vanilla JSON text component.
     *
     * @param json the serialized vanilla JSON text component
     * @throws JsonSyntaxException when the input is not valid JSON
     */
    public void setCustomNameJson(String json)
    {
        requireJson(json);
        setCustomNameUnchecked(json);
    }

    /**
     * @deprecated The argument is a JSON component. Use {@link #setCustomNameJson(String)}.
     * This method retains the historical legacy-server behavior for compatibility.
     */
    @Deprecated
    public void setCustomName(String compoundString)
    {
        setCustomNameUnchecked(compoundString);
    }

    private void setCustomNameUnchecked(String json)
    {
        if (NBT.dataComponents != null)
            NBT.dataComponents.setCustomName(getItem(), json);
        else
            getOrAddCompound("display").setString("Name", json);
        save();
    }

    /**
     * Returns the internal item-name value. Its runtime type is server implementation-specific.
     *
     * @deprecated Use {@link #getItemNameJson()} for a stable Paper/Spigot representation.
     */
    @Deprecated
    @Nullable
    public Object getItemName()
    {
        return NBT.dataComponents == null ? null : NBT.dataComponents.getItemName(getItem());
    }

    /**
     * Returns the item name as a serialized vanilla JSON text component.
     * Item names exist only on Minecraft 1.20.5 and newer.
     *
     * @return the JSON component, or null when absent or unsupported
     */
    @Nullable
    public String getItemNameJson()
    {
        return NBT.dataComponents == null ? null : NBT.dataComponents.getItemNameJson(getItem());
    }

    /**
     * Sets the item name from a serialized vanilla JSON text component.
     *
     * @param json the serialized vanilla JSON text component
     * @throws JsonSyntaxException when the input is not valid JSON
     * @throws UnsupportedOperationException before Minecraft 1.20.5
     */
    public void setItemNameJson(String json)
    {
        requireJson(json);
        setItemNameUnchecked(json);
    }

    /**
     * @deprecated The argument is a JSON component. Use {@link #setItemNameJson(String)}.
     */
    @Deprecated
    public void setItemName(String compoundString)
    {
        setItemNameUnchecked(compoundString);
    }

    private void setItemNameUnchecked(String json)
    {
        if (NBT.dataComponents == null)
            throw new UnsupportedOperationException("Item names require Minecraft 1.20.5 or newer.");
        NBT.dataComponents.setItemName(getItem(), json);
        save();
    }

    @Deprecated
    public void setAttributeModifier(String attributeName,
                                     int operation,
                                     double amount,
                                     String name,
                                     String slot,
                                     int uuidLeast,
                                     int uuidMost)
    {
        setAttributeModifier(attributeName, AttributeOperation.byId(operation), amount, name, slot, uuidLeast, uuidMost);
    }

    public void setAttributeModifier(String attributeName,
                                     AttributeOperation operation,
                                     double amount,
                                     String name,
                                     String slot,
                                     int uuidLeast,
                                     int uuidMost)
    {
        AttributeName knownAttribute = AttributeName.find(attributeName);
        setAttributeModifierResolved(knownAttribute == null ? attributeName : knownAttribute.getName(), operation,
                amount, name, slot, uuidLeast, uuidMost);
    }

    public void setAttributeModifier(AttributeName attributeName,
                                     AttributeOperation operation,
                                     double amount,
                                     String name,
                                     String slot,
                                     int uuidLeast,
                                     int uuidMost)
    {
        Objects.requireNonNull(attributeName, "attributeName");
        setAttributeModifierResolved(attributeName.getName(), operation, amount, name, slot, uuidLeast, uuidMost);
    }

    private void setAttributeModifierResolved(String resolvedAttributeName,
                                              AttributeOperation operation,
                                              double amount,
                                              String name,
                                              String slot,
                                              int uuidLeast,
                                              int uuidMost)
    {
        Objects.requireNonNull(resolvedAttributeName, "attributeName");
        Objects.requireNonNull(operation, "operation");

        UUID uuid = new UUID(uuidMost, uuidLeast);
        if (NBT.dataComponents == null)
        {
            NCompound attribute = getOrAddList("AttributeModifiers", NBTType.Compound).addCompound();
            attribute.setString("AttributeName", resolvedAttributeName);
            attribute.setInt("Operation", operation.id);
            attribute.setUUID("UUID", uuid);
            attribute.setDouble("Amount", amount);
            attribute.setString("Name", name);
            attribute.setString("Slot", slot);
        }
        else
            NBT.dataComponents.setAttributeModifier(
                    getItem(),
                    resolvedAttributeName,
                    operation.id,
                    amount,
                    name,
                    uuid,
                    getAttributeSlotId(slot)
            );
        save();
    }

    public void setSkull(String name, UUID uuid, String value, String signature)
    {
        setType(Material.PLAYER_HEAD);
        if (NBT.dataComponents == null)
        {
            NCompound owner = getOrAddCompound("SkullOwner");
            owner.setString("Name", name);
            owner.setUUID("Id", uuid);

            NCompound profile = owner.getOrAddCompound("Properties")
                    .getOrAddList("textures", NBTType.Compound)
                    .addCompound();
            profile.setString("Value", value);
            if (signature != null)
                profile.setString("Signature", signature);
        }
        else
            NBT.dataComponents.setSkull(getItem(), name, uuid, value, signature);
        save();
    }

    public void setSkull(String name, String value)
    {
        setSkull(name, UUID.nameUUIDFromBytes(name.getBytes()), value, null);
    }

    private static int getAttributeSlotId(String slot)
    {
        switch (slot)
        {
            case "any": return 0;
            case "mainhand": return 1;
            case "offhand": return 2;
            case "hand": return 3;
            case "feet": return 4;
            case "legs": return 5;
            case "chest": return 6;
            case "head": return 7;
            default: throw new IllegalArgumentException("Unknown slot: " + slot);
        }
    }

    /**
     * Returns a detached copy of the raw lore values without JSON conversion.
     * Entries are JSON strings on legacy servers and version-specific NMS {@code Component}
     * objects on data-component servers. The version adapters support both Paper and Spigot
     * without requiring Paper-only APIs.
     *
     * @return the raw lore copy, or null when lore is absent
     */
    @Nullable
    public List<Object> getLoreRawCopy()
    {
        if (NBT.dataComponents != null)
            return NBT.dataComponents.getLore(getItem());

        NCompound display = getCompound("display");
        if (display == null)
            return null;
        NList lore = display.getList("Lore", NBTType.String);
        if (lore == null)
            return null;

        List<Object> copy = new ArrayList<>(lore.size());
        for (int i = 0; i < lore.size(); i++)
            copy.add(lore.getString(i));
        return copy;
    }

    /**
     * @deprecated Use {@link #getLoreRawCopy()} for raw values or {@link #getLoreJson()}
     * for a version-independent representation.
     */
    @Deprecated
    @Nullable
    public List<Object> getLoreCopy()
    {
        return getLoreRawCopy();
    }

    /**
     * Returns every lore line as a serialized vanilla JSON text component.
     *
     * @return a detached JSON list, or null when lore is absent
     */
    @Nullable
    public List<String> getLoreJson()
    {
        if (NBT.dataComponents != null)
            return NBT.dataComponents.getLoreJson(getItem());

        NCompound display = getCompound("display");
        if (display == null)
            return null;
        NList lore = display.getList("Lore", NBTType.String);
        if (lore == null)
            return null;

        List<String> copy = new ArrayList<>(lore.size());
        for (int i = 0; i < lore.size(); i++)
            copy.add(lore.getString(i));
        return copy;
    }

    /**
     * Checks whether lore contains an equivalent serialized vanilla JSON text component.
     *
     * @param json the serialized vanilla JSON text component
     * @return true when an equivalent line is present
     */
    public boolean hasLoreJson(String json)
    {
        List<String> lore = getLoreJson();
        if (lore == null)
            return false;
        for (String line : lore)
        {
            if (jsonEquals(line, json))
                return true;
        }
        return false;
    }

    /**
     * Replaces lore with serialized vanilla JSON text components.
     *
     * @param lore JSON components, or null to remove lore
     * @throws JsonSyntaxException when a line is not valid JSON
     */
    public void setLoreJson(@Nullable List<String> lore)
    {
        if (lore != null)
        {
            for (String line : lore)
                requireJson(line);
        }

        if (NBT.dataComponents != null)
            NBT.dataComponents.setLoreJson(getItem(), lore);
        else if (lore == null)
        {
            NCompound display = getCompound("display");
            if (display != null)
                display.remove("Lore");
        }
        else
        {
            NList legacyLore = getOrAddCompound("display").addList("Lore", NBTType.String);
            for (String line : lore)
                legacyLore.addString(line);
        }
        save();
    }

    /**
     * Replaces lore without converting its entries. Legacy servers require JSON strings;
     * data-component servers require their version-specific NMS {@code Component} objects.
     * Passing null removes lore.
     *
     * @param lore raw lore values, or null to remove lore
     */
    public void setLoreRaw(@Nullable List<?> lore)
    {
        if (NBT.dataComponents != null)
            NBT.dataComponents.setLore(getItem(), lore);
        else if (lore == null)
        {
            NCompound display = getCompound("display");
            if (display != null)
                display.remove("Lore");
        }
        else
        {
            for (Object line : lore)
            {
                if (!(line instanceof String))
                    throw new IllegalArgumentException("Legacy lore entries must be JSON strings.");
            }

            NList legacyLore = getOrAddCompound("display").addList("Lore", NBTType.String);
            for (Object line : lore)
                legacyLore.addString((String) line);
        }
        save();
    }

    /**
     * @deprecated Use {@link #setLoreRaw(List)} for raw values or {@link #setLoreJson(List)}
     * for a version-independent representation.
     */
    @Deprecated
    public void setLore(@Nullable List<?> lore)
    {
        setLoreRaw(lore);
    }

    private static void requireJson(String json)
    {
        new JsonParser().parse(json);
    }

    private static boolean jsonEquals(String first, String second)
    {
        if (first == null || second == null)
            return false;
        try
        {
            return new JsonParser().parse(first).equals(new JsonParser().parse(second));
        }
        catch (JsonSyntaxException ignored)
        {
            return false;
        }
    }
}
