package beer.devs.fastnbt.nms.nbt;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public interface ICraftItemStack<ListTag, CompoundTag, CraftItemStack extends ItemStack> extends ICompoundTag<ItemStack, ListTag, CompoundTag>
{
    CraftItemStack convertToCraft(ItemStack itemStack);
    default ItemStack readForEdit(ItemStack itemStack)
    {
        return convertToCraft(itemStack);
    }

    default ItemStack copyForEdit(ItemStack itemStack)
    {
        return convertToCraft(new ItemStack(itemStack));
    }

    Object editTag(ItemStack craftItem, boolean create);
    /** Publishes the private draft; callers must not expose its live tags afterwards. */
    default void applyEdit(ItemStack original, ItemStack draft)
    {
        if (!original.setItemMeta(draft.getItemMeta()))
            throw new IllegalStateException("Cannot apply edited item metadata");
    }
    void modifyCustomData(ItemStack itemStack, Consumer<NCompound> modifier);
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
