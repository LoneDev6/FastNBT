package beer.devs.fastnbt.nms.nbt;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface IDataComponents
{
    default Object getCustomName(ItemStack bukkitItemStack)
    {
        throw new RuntimeException("Not available in this server implementation");
    }

    String getCustomNameJson(ItemStack bukkitItemStack);

    default Object getItemName(ItemStack bukkitItemStack)
    {
        throw new RuntimeException("Not available in this server implementation");
    }

    //TODO hasItemName
    //TODO hasCustomName
    //TODO hasLore

    @Nullable
    List<Object> getLore(ItemStack bukkitItemStack);

    void setLore(ItemStack bukkitItemStack, @Nullable List<?> lore);

    default boolean copyAttributeModifiers(ItemStack bukkitItemStackSource, ItemStack bukkitItemStackDestination)
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

    default void setCustomName(ItemStack bukkitItem, String json)
    {
        throw new RuntimeException("Not available in this server implementation");
    }

    default void setItemName(ItemStack bukkitItem, String json)
    {
        throw new RuntimeException("Not available in this server implementation");
    }

    Object getOrAddCustomDataComponent(ItemStack bukkitItem);
}
