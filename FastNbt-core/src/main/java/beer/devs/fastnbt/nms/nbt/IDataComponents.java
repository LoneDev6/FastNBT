package beer.devs.fastnbt.nms.nbt;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public interface IDataComponents
{
    /**
     * Returns the server-internal custom-name component.
     *
     * @deprecated Use {@link #getCustomNameJson(ItemStack)} outside version adapters.
     */
    @Deprecated
    default Object getCustomName(ItemStack bukkitItemStack)
    {
        throw new RuntimeException("Not available in this server implementation");
    }

    /** Returns the custom name as a serialized vanilla JSON text component. */
    @Nullable
    default String getCustomNameJson(ItemStack bukkitItemStack)
    {
        throw new RuntimeException("Not available in this server implementation");
    }

    /**
     * Returns the server-internal item-name component.
     *
     * @deprecated Use {@link #getItemNameJson(ItemStack)} outside version adapters.
     */
    @Deprecated
    default Object getItemName(ItemStack bukkitItemStack)
    {
        throw new RuntimeException("Not available in this server implementation");
    }

    /** Returns the item name as a serialized vanilla JSON text component. */
    @Nullable
    default String getItemNameJson(ItemStack bukkitItemStack)
    {
        throw new RuntimeException("Not available in this server implementation");
    }

    default boolean hasEnchantment(ItemStack bukkitItemStack, String name)
    {
        ItemMeta meta = bukkitItemStack.getItemMeta();
        if (meta == null)
            return false;
        for (Enchantment enchantment : meta.getEnchants().keySet())
        {
            if (enchantment.getKey().toString().equals(name))
                return true;
        }
        return false;
    }

    default boolean hasLore(ItemStack bukkitItemStack, String line)
    {
        ItemMeta meta = bukkitItemStack.getItemMeta();
        List<String> lore = meta == null ? null : meta.getLore();
        return lore != null && lore.contains(line);
    }

    default boolean hasDisplayName(ItemStack bukkitItemStack, String name)
    {
        ItemMeta meta = bukkitItemStack.getItemMeta();
        return meta != null && meta.hasDisplayName() && meta.getDisplayName().equals(name);
    }

    //TODO hasItemName

    /** Returns a detached list containing the server-internal lore components. */
    @Nullable
    List<Object> getLore(ItemStack bukkitItemStack);

    /** Returns lore as detached serialized vanilla JSON text components. */
    @Nullable
    default List<String> getLoreJson(ItemStack bukkitItemStack)
    {
        throw new RuntimeException("Not available in this server implementation");
    }

    /** Sets lore using server-internal component objects, or removes it when null. */
    void setLore(ItemStack bukkitItemStack, @Nullable List<?> lore);

    /** Sets lore from serialized vanilla JSON text components, or removes it when null. */
    default void setLoreJson(ItemStack bukkitItemStack, @Nullable List<String> lore)
    {
        throw new RuntimeException("Not available in this server implementation");
    }

    default boolean copyAttributeModifiers(ItemStack bukkitItemStackSource, ItemStack bukkitItemStackDestination)
    {
        throw new RuntimeException("Not available in this server implementation");
    }

    default void setAttributeModifier(ItemStack bukkitItemStack,
                                      String attributeName,
                                      int operation,
                                      double amount,
                                      String modifierName,
                                      UUID uuid,
                                      int slotId)
    {
        throw new RuntimeException("Not available in this server implementation");
    }

    default void setSkull(ItemStack bukkitItemStack, String name, UUID uuid, String value, @Nullable String signature)
    {
        throw new RuntimeException("Not available in this server implementation");
    }

    default boolean copyLore(ItemStack bukkitItemStackSource, ItemStack bukkitItemStackDestination)
    {
        throw new RuntimeException("Not available in this server implementation");
    }

    default void appendLoreJson(ItemStack bukkitItem, List<String> endLinesJson)
    {
        throw new RuntimeException("Not available in this server implementation");
    }

    /** Sets the custom name from a serialized vanilla JSON text component. */
    default void setCustomName(ItemStack bukkitItem, String json)
    {
        throw new RuntimeException("Not available in this server implementation");
    }

    /** Sets the item name from a serialized vanilla JSON text component. */
    default void setItemName(ItemStack bukkitItem, String json)
    {
        throw new RuntimeException("Not available in this server implementation");
    }

    Object getOrAddCustomDataComponent(ItemStack bukkitItem);
}
