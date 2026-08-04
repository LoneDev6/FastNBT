package beer.devs.fastnbt.nms.nbt;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface ICraftItemStack<ListTag, CompoundTag, CraftItemStack extends ItemStack> extends ICompoundTag<ItemStack, ListTag, CompoundTag>
{
    CraftItemStack convertToCraft(ItemStack itemStack);
    boolean hasNBT(ItemStack itemStack);
    void merge(ItemStack itemStack, ItemStack otherItem);

    default boolean hasEnchantment(ItemStack itemStack, String name)
    {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null)
            return false;
        for (Enchantment enchantment : meta.getEnchants().keySet())
        {
            if (enchantment.getKey().toString().equals(name))
                return true;
        }
        return false;
    }

    default boolean hasLore(ItemStack itemStack, String line)
    {
        ItemMeta meta = itemStack.getItemMeta();
        List<String> lore = meta == null ? null : meta.getLore();
        return lore != null && lore.contains(line);
    }

    default boolean hasDisplayName(ItemStack itemStack, String name)
    {
        ItemMeta meta = itemStack.getItemMeta();
        return meta != null && meta.hasDisplayName() && meta.getDisplayName().equals(name);
    }

    default boolean hasItemMeta(ItemStack itemStack)
    {
        return itemStack.hasItemMeta();
    }
    ItemStack asCraftMirror(ItemStack itemStack);
    Object asNmsCopy(ItemStack itemStack);
    @Nullable ItemStack compoundToItemStack(CompoundTag itemStack);
    CompoundTag itemStackToCompound(ItemStack itemStack);

    /** Deserializes a complete item from SNBT, not JSON. */
    @Nullable ItemStack compoundStrToBukkit(String snbt);

    /** Serializes a complete item to SNBT, not JSON. */
    default String bukkitItemToCompoundStr(ItemStack bukkitItem)
    {
        return toString(bukkitItem);
    }

    boolean isInstanceOfCraftItemStack(ItemStack bukkitItem);
}
