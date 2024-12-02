package dev.lone.fastnbt.nms.nbt;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.lone.fastnbt.nms.Implementation;
import dev.lone.fastnbt.nms.Version;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.component.CustomData;
import org.apache.commons.lang.reflect.FieldUtils;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

@Implementation.CyclicDependency(type = ICraftItemStack.class, version = Version.v1_21)
@SuppressWarnings({"unchecked", "DataFlowIssue", "CallToPrintStackTrace", "unused", "deprecation"})
public class CraftItemStack_v1_21 implements ICraftItemStack<ListTag, CompoundTag, CraftItemStack>
{
    public static final Field FIELD_HANDLE;
    /**
     * The handle field is public only on Paper!
     */
    private static final boolean IS_FIELD_HANDLE_PUBLIC;

    static
    {
        FIELD_HANDLE = FieldUtils.getField(CraftItemStack.class, "handle", true);
        IS_FIELD_HANDLE_PUBLIC = Modifier.isPublic(FIELD_HANDLE.getModifiers());
    }

    public static net.minecraft.world.item.ItemStack getHandle(CraftItemStack craftItemStack)
    {
        if(IS_FIELD_HANDLE_PUBLIC)
            return craftItemStack.handle;

        try
        {
            return (net.minecraft.world.item.ItemStack) FIELD_HANDLE.get(craftItemStack);
        }
        catch (IllegalAccessException e)
        {
            new RuntimeException("Error reading handle field of CraftItemStack.", e).printStackTrace();
        }
        return null;
    }

    public static CraftItemStack castToCraftItemStack(ItemStack itemStack)
    {
        return ((CraftItemStack) itemStack);
    }

    @Override
    public CompoundTag newInstance()
    {
        return new CompoundTag();
    }

    @Override
    public CraftItemStack convertToCraft(ItemStack itemStack)
    {
        if (itemStack instanceof CraftItemStack)
            return ((CraftItemStack) itemStack);
        return CraftItemStack.asCraftCopy(itemStack);
    }

    @Override
    public boolean hasNBT(ItemStack itemStack)
    {
        if (!NBT.isInstanceOfCraftItemStack(itemStack))
            return itemStack != null && itemStack.hasItemMeta();
        net.minecraft.world.item.ItemStack handle = getHandle(castToCraftItemStack(itemStack));
        if (handle == null)
            return false;
        return handle.has(DataComponents.CUSTOM_DATA);
    }

    private <T> boolean isDefaultComponentValue(net.minecraft.world.item.ItemStack itemStack, DataComponentType<T> type)
    {
        return itemStack.get(type).equals(itemStack.getPrototype().get(type));
    }

    <T> void simpleMergeComponent(net.minecraft.world.item.ItemStack a, net.minecraft.world.item.ItemStack b, DataComponentType<T> type)
    {
        if(b.has(type) && !isDefaultComponentValue(b, type))
            a.set(type, b.get(type));
    }

    @Override
    public void merge(ItemStack itemStack, ItemStack otherItem)
    {
        CraftItemStack craftItemStack = castToCraftItemStack(itemStack);
        CraftItemStack other = castToCraftItemStack(otherItem);

        net.minecraft.world.item.ItemStack a = getHandle(craftItemStack);
        net.minecraft.world.item.ItemStack b = getHandle(other);

        // Make sure to merge the properties, not replace them completely.
        if(a.has(DataComponents.CUSTOM_DATA) && b.has(DataComponents.CUSTOM_DATA))
            a.get(DataComponents.CUSTOM_DATA).getUnsafe().merge(b.get(DataComponents.CUSTOM_DATA).getUnsafe());

        simpleMergeComponent(a, b, DataComponents.MAX_STACK_SIZE);
        simpleMergeComponent(a, b, DataComponents.MAX_DAMAGE);
        simpleMergeComponent(a, b, DataComponents.DAMAGE);
        simpleMergeComponent(a, b, DataComponents.UNBREAKABLE);
        simpleMergeComponent(a, b, DataComponents.CUSTOM_NAME);
        simpleMergeComponent(a, b, DataComponents.ITEM_NAME);
        simpleMergeComponent(a, b, DataComponents.LORE);
        simpleMergeComponent(a, b, DataComponents.RARITY);
        simpleMergeComponent(a, b, DataComponents.ENCHANTMENTS);
        simpleMergeComponent(a, b, DataComponents.CAN_PLACE_ON);
        simpleMergeComponent(a, b, DataComponents.CAN_BREAK);
        simpleMergeComponent(a, b, DataComponents.ATTRIBUTE_MODIFIERS);
        simpleMergeComponent(a, b, DataComponents.CUSTOM_MODEL_DATA);
        simpleMergeComponent(a, b, DataComponents.HIDE_ADDITIONAL_TOOLTIP);
        simpleMergeComponent(a, b, DataComponents.HIDE_TOOLTIP);
        simpleMergeComponent(a, b, DataComponents.REPAIR_COST);
        simpleMergeComponent(a, b, DataComponents.CREATIVE_SLOT_LOCK);
        simpleMergeComponent(a, b, DataComponents.ENCHANTMENT_GLINT_OVERRIDE);
        simpleMergeComponent(a, b, DataComponents.INTANGIBLE_PROJECTILE);
        simpleMergeComponent(a, b, DataComponents.FOOD);
        simpleMergeComponent(a, b, DataComponents.FIRE_RESISTANT);
        simpleMergeComponent(a, b, DataComponents.TOOL);
        simpleMergeComponent(a, b, DataComponents.STORED_ENCHANTMENTS);
        simpleMergeComponent(a, b, DataComponents.DYED_COLOR);
        simpleMergeComponent(a, b, DataComponents.MAP_COLOR);
        simpleMergeComponent(a, b, DataComponents.MAP_ID);
        simpleMergeComponent(a, b, DataComponents.MAP_DECORATIONS);
        simpleMergeComponent(a, b, DataComponents.MAP_POST_PROCESSING);
        simpleMergeComponent(a, b, DataComponents.CHARGED_PROJECTILES);
        simpleMergeComponent(a, b, DataComponents.BUNDLE_CONTENTS);
        simpleMergeComponent(a, b, DataComponents.POTION_CONTENTS);
        simpleMergeComponent(a, b, DataComponents.SUSPICIOUS_STEW_EFFECTS);
        simpleMergeComponent(a, b, DataComponents.WRITABLE_BOOK_CONTENT);
        simpleMergeComponent(a, b, DataComponents.WRITTEN_BOOK_CONTENT);
        simpleMergeComponent(a, b, DataComponents.TRIM);
        simpleMergeComponent(a, b, DataComponents.DEBUG_STICK_STATE);
        simpleMergeComponent(a, b, DataComponents.ENTITY_DATA);
        simpleMergeComponent(a, b, DataComponents.BUCKET_ENTITY_DATA);
        simpleMergeComponent(a, b, DataComponents.BLOCK_ENTITY_DATA);
        simpleMergeComponent(a, b, DataComponents.INSTRUMENT);
        simpleMergeComponent(a, b, DataComponents.OMINOUS_BOTTLE_AMPLIFIER);
        simpleMergeComponent(a, b, DataComponents.RECIPES);
        simpleMergeComponent(a, b, DataComponents.LODESTONE_TRACKER);
        simpleMergeComponent(a, b, DataComponents.FIREWORK_EXPLOSION);
        simpleMergeComponent(a, b, DataComponents.FIREWORKS);
        simpleMergeComponent(a, b, DataComponents.PROFILE);
        simpleMergeComponent(a, b, DataComponents.NOTE_BLOCK_SOUND);
        simpleMergeComponent(a, b, DataComponents.BANNER_PATTERNS);
        simpleMergeComponent(a, b, DataComponents.BASE_COLOR);
        simpleMergeComponent(a, b, DataComponents.POT_DECORATIONS);
        simpleMergeComponent(a, b, DataComponents.CONTAINER);
        simpleMergeComponent(a, b, DataComponents.BLOCK_STATE);
        simpleMergeComponent(a, b, DataComponents.BEES);
        simpleMergeComponent(a, b, DataComponents.LOCK);
        simpleMergeComponent(a, b, DataComponents.CONTAINER_LOOT);
        simpleMergeComponent(a, b, DataComponents.JUKEBOX_PLAYABLE);
    }

    @Override
    public void setByte(ItemStack itemStack, String key, byte param)
    {
        CraftItemStack craftItemStack = castToCraftItemStack(itemStack);
        net.minecraft.world.item.ItemStack handle = getHandle(craftItemStack);
        CustomData data = handle.get(DataComponents.CUSTOM_DATA);
        if (data == null)
            handle.set(DataComponents.CUSTOM_DATA, CustomData.of(new CompoundTag()));
        handle.get(DataComponents.CUSTOM_DATA).getUnsafe().putByte(key, param);
    }

    @Override
    public void setShort(ItemStack itemStack, String key, short param)
    {
        CraftItemStack craftItemStack = castToCraftItemStack(itemStack);
        net.minecraft.world.item.ItemStack handle = getHandle(craftItemStack);
        CustomData data = handle.get(DataComponents.CUSTOM_DATA);
        if (data == null)
            handle.set(DataComponents.CUSTOM_DATA, CustomData.of(new CompoundTag()));
        handle.get(DataComponents.CUSTOM_DATA).getUnsafe().putShort(key, param);
    }

    @Override
    public void setInt(ItemStack itemStack, String key, int param)
    {
        CraftItemStack craftItemStack = castToCraftItemStack(itemStack);
        net.minecraft.world.item.ItemStack handle = getHandle(craftItemStack);
        CustomData data = handle.get(DataComponents.CUSTOM_DATA);
        if (data == null)
            handle.set(DataComponents.CUSTOM_DATA, CustomData.of(new CompoundTag()));
        handle.get(DataComponents.CUSTOM_DATA).getUnsafe().putInt(key, param);
    }

    @Override
    public void setLong(ItemStack itemStack, String key, long param)
    {
        CraftItemStack craftItemStack = castToCraftItemStack(itemStack);
        net.minecraft.world.item.ItemStack handle = getHandle(craftItemStack);
        CustomData data = handle.get(DataComponents.CUSTOM_DATA);
        if (data == null)
            handle.set(DataComponents.CUSTOM_DATA, CustomData.of(new CompoundTag()));
        handle.get(DataComponents.CUSTOM_DATA).getUnsafe().putLong(key, param);
    }

    @Override
    public void setUUID(ItemStack itemStack, String key, UUID param)
    {
        CraftItemStack craftItemStack = castToCraftItemStack(itemStack);
        net.minecraft.world.item.ItemStack handle = getHandle(craftItemStack);
        CustomData data = handle.get(DataComponents.CUSTOM_DATA);
        if (data == null)
            handle.set(DataComponents.CUSTOM_DATA, CustomData.of(new CompoundTag()));
        handle.get(DataComponents.CUSTOM_DATA).getUnsafe().putUUID(key, param);
    }

    @Override
    public void setFloat(ItemStack itemStack, String key, float param)
    {
        CraftItemStack craftItemStack = castToCraftItemStack(itemStack);
        net.minecraft.world.item.ItemStack handle = getHandle(craftItemStack);
        CustomData data = handle.get(DataComponents.CUSTOM_DATA);
        if (data == null)
            handle.set(DataComponents.CUSTOM_DATA, CustomData.of(new CompoundTag()));
        handle.get(DataComponents.CUSTOM_DATA).getUnsafe().putFloat(key, param);
    }

    @Override
    public void setDouble(ItemStack itemStack, String key, double param)
    {
        CraftItemStack craftItemStack = castToCraftItemStack(itemStack);
        net.minecraft.world.item.ItemStack handle = getHandle(craftItemStack);
        CustomData data = handle.get(DataComponents.CUSTOM_DATA);
        if (data == null)
            handle.set(DataComponents.CUSTOM_DATA, CustomData.of(new CompoundTag()));
        handle.get(DataComponents.CUSTOM_DATA).getUnsafe().putDouble(key, param);
    }

    @Override
    public void setString(ItemStack itemStack, String key, String param)
    {
        CraftItemStack craftItemStack = castToCraftItemStack(itemStack);
        net.minecraft.world.item.ItemStack handle = getHandle(craftItemStack);
        CustomData data = handle.get(DataComponents.CUSTOM_DATA);
        if (data == null)
            handle.set(DataComponents.CUSTOM_DATA, CustomData.of(new CompoundTag()));
        handle.get(DataComponents.CUSTOM_DATA).getUnsafe().putString(key, param);
    }

    @Override
    public void setByteArray(ItemStack itemStack, String key, byte[] param)
    {
        CraftItemStack craftItemStack = castToCraftItemStack(itemStack);
        net.minecraft.world.item.ItemStack handle = getHandle(craftItemStack);
        CustomData data = handle.get(DataComponents.CUSTOM_DATA);
        if (data == null)
            handle.set(DataComponents.CUSTOM_DATA, CustomData.of(new CompoundTag()));
        handle.get(DataComponents.CUSTOM_DATA).getUnsafe().putByteArray(key, param);
    }

    @Override
    public void setIntArray(ItemStack itemStack, String key, int[] param)
    {
        CraftItemStack craftItemStack = castToCraftItemStack(itemStack);
        net.minecraft.world.item.ItemStack handle = getHandle(craftItemStack);
        CustomData data = handle.get(DataComponents.CUSTOM_DATA);
        if (data == null)
            handle.set(DataComponents.CUSTOM_DATA, CustomData.of(new CompoundTag()));
        handle.get(DataComponents.CUSTOM_DATA).getUnsafe().putIntArray(key, param);
    }

    @Override
    public void setIntegerList(ItemStack itemStack, String key, List<Integer> param)
    {
        CraftItemStack craftItemStack = castToCraftItemStack(itemStack);
        net.minecraft.world.item.ItemStack handle = getHandle(craftItemStack);
        CustomData data = handle.get(DataComponents.CUSTOM_DATA);
        if (data == null)
            handle.set(DataComponents.CUSTOM_DATA, CustomData.of(new CompoundTag()));
        handle.get(DataComponents.CUSTOM_DATA).getUnsafe().putIntArray(key, param);
    }

    @Override
    public void setLongArray(ItemStack itemStack, String key, long[] param)
    {
        CraftItemStack craftItemStack = castToCraftItemStack(itemStack);
        net.minecraft.world.item.ItemStack handle = getHandle(craftItemStack);
        CustomData data = handle.get(DataComponents.CUSTOM_DATA);
        if (data == null)
            handle.set(DataComponents.CUSTOM_DATA, CustomData.of(new CompoundTag()));
        handle.get(DataComponents.CUSTOM_DATA).getUnsafe().putLongArray(key, param);
    }

    @Override
    public void setLongList(ItemStack itemStack, String key, List<Long> param)
    {
        CraftItemStack craftItemStack = castToCraftItemStack(itemStack);
        net.minecraft.world.item.ItemStack handle = getHandle(craftItemStack);
        CustomData data = handle.get(DataComponents.CUSTOM_DATA);
        if (data == null)
            handle.set(DataComponents.CUSTOM_DATA, CustomData.of(new CompoundTag()));
        handle.get(DataComponents.CUSTOM_DATA).getUnsafe().putLongArray(key, param);
    }

    @Override
    public void setBoolean(ItemStack itemStack, String key, boolean param)
    {
        CraftItemStack craftItemStack = castToCraftItemStack(itemStack);
        net.minecraft.world.item.ItemStack handle = getHandle(craftItemStack);
        CustomData data = handle.get(DataComponents.CUSTOM_DATA);
        if (data == null)
            handle.set(DataComponents.CUSTOM_DATA, CustomData.of(new CompoundTag()));
        handle.get(DataComponents.CUSTOM_DATA).getUnsafe().putBoolean(key, param);
    }

    @Override
    public boolean hasKey(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = castToCraftItemStack(itemStack);
        CustomData data = getHandle(craftItemStack).get(DataComponents.CUSTOM_DATA);
        if (data == null)
            return false;
        return data.contains(key);
    }

    @Override
    public boolean hasUUID(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = castToCraftItemStack(itemStack);
        CustomData data = getHandle(craftItemStack).get(DataComponents.CUSTOM_DATA);
        if (data == null)
            return false;
        return data.getUnsafe().hasUUID(key);
    }

    @Override
    public @Nullable UUID getUUID(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = castToCraftItemStack(itemStack);
        CustomData data = getHandle(craftItemStack).get(DataComponents.CUSTOM_DATA);
        if (data == null)
            return null;
        return data.getUnsafe().getUUID(key);
    }

    @Override
    public byte getByte(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = castToCraftItemStack(itemStack);
        CustomData data = getHandle(craftItemStack).get(DataComponents.CUSTOM_DATA);
        if (data == null)
            return 0;
        return data.getUnsafe().getByte(key);
    }

    @Override
    public short getShort(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = castToCraftItemStack(itemStack);
        CustomData data = getHandle(craftItemStack).get(DataComponents.CUSTOM_DATA);
        if (data == null)
            return 0;
        return data.getUnsafe().getShort(key);
    }

    @Override
    public int getInt(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = castToCraftItemStack(itemStack);
        CustomData data = getHandle(craftItemStack).get(DataComponents.CUSTOM_DATA);
        if (data == null)
            return 0;
        return data.getUnsafe().getInt(key);
    }

    @Override
    public long getLong(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = castToCraftItemStack(itemStack);
        CustomData data = getHandle(craftItemStack).get(DataComponents.CUSTOM_DATA);
        if (data == null)
            return 0;
        return data.getUnsafe().getLong(key);
    }

    @Override
    public float getFloat(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = castToCraftItemStack(itemStack);
        CustomData data = getHandle(craftItemStack).get(DataComponents.CUSTOM_DATA);
        if (data == null)
            return 0;
        return data.getUnsafe().getFloat(key);
    }

    @Override
    public double getDouble(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = castToCraftItemStack(itemStack);
        CustomData data = getHandle(craftItemStack).get(DataComponents.CUSTOM_DATA);
        if (data == null)
            return 0;
        return data.getUnsafe().getDouble(key);
    }

    @Override
    public String getString(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = castToCraftItemStack(itemStack);
        CustomData data = getHandle(craftItemStack).get(DataComponents.CUSTOM_DATA);
        if (data == null)
            return null;
        return data.getUnsafe().getString(key);
    }

    @Override
    public byte[] getByteArray(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = castToCraftItemStack(itemStack);
        CustomData data = getHandle(craftItemStack).get(DataComponents.CUSTOM_DATA);
        if (data == null)
            return null;
        return data.getUnsafe().getByteArray(key);
    }

    @Override
    public int[] getIntArray(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = castToCraftItemStack(itemStack);
        CustomData data = getHandle(craftItemStack).get(DataComponents.CUSTOM_DATA);
        if (data == null)
            return null;
        return data.getUnsafe().getIntArray(key);
    }

    @Override
    public long[] getLongArray(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = castToCraftItemStack(itemStack);
        CustomData data = getHandle(craftItemStack).get(DataComponents.CUSTOM_DATA);
        if (data == null)
            return null;
        return data.getUnsafe().getLongArray(key);
    }

    @Override
    public CompoundTag getCompound(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = castToCraftItemStack(itemStack);
        CustomData data = getHandle(craftItemStack).get(DataComponents.CUSTOM_DATA);
        if (data == null)
            return null;
        return data.getUnsafe().getCompound(key);
    }

    @Override
    public CompoundTag getOrAddCompound(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = castToCraftItemStack(itemStack);
        if (!getHandle(craftItemStack).has(DataComponents.CUSTOM_DATA))
            getHandle(craftItemStack).set(DataComponents.CUSTOM_DATA, CustomData.of(new CompoundTag()));
        return (CompoundTag) NBT.compound.getOrAddCompound(getHandle(craftItemStack).get(DataComponents.CUSTOM_DATA).getUnsafe(), key);
    }

    @Override
    public ListTag getList(ItemStack itemStack, String key, int typeID)
    {
        CraftItemStack craftItemStack = castToCraftItemStack(itemStack);
        CustomData data = getHandle(craftItemStack).get(DataComponents.CUSTOM_DATA);
        if (data == null)
            return null;
        return (ListTag) NBT.compound.getList(data.getUnsafe(), key, typeID);
    }

    @Override
    public ListTag getOrAddList(ItemStack itemStack, String key, int typeID)
    {
        CraftItemStack craftItemStack = castToCraftItemStack(itemStack);
        CustomData data = getHandle(craftItemStack).get(DataComponents.CUSTOM_DATA);
        if (data == null)
            getHandle(craftItemStack).set(DataComponents.CUSTOM_DATA, CustomData.of(new CompoundTag()));
        return (ListTag) NBT.compound.getOrAddList(data.getUnsafe(), key, typeID);
    }

    @Override
    public void putTag(ItemStack itemStack, String key, Object value)
    {
        CraftItemStack craftItemStack = castToCraftItemStack(itemStack);
        CustomData data = getHandle(craftItemStack).get(DataComponents.CUSTOM_DATA);
        if (data == null)
            getHandle(craftItemStack).set(DataComponents.CUSTOM_DATA, CustomData.of(new CompoundTag()));
        NBT.compound.putTag(data.getUnsafe(), key, value);
    }

    @Override
    public boolean getBoolean(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = castToCraftItemStack(itemStack);
        net.minecraft.world.item.ItemStack handle = getHandle(craftItemStack);
        CustomData data = handle.get(DataComponents.CUSTOM_DATA);
        if (data == null)
            return false;
        return NBT.compound.getBoolean(data.getUnsafe(), key);
    }

    @Override
    public Set<String> getKeys(ItemStack itemStack)
    {
        CraftItemStack craftItemStack = castToCraftItemStack(itemStack);
        CustomData data = getHandle(craftItemStack).get(DataComponents.CUSTOM_DATA);
        if (data != null)
            return data.getUnsafe().getAllKeys();
        return null;
    }

    @Override
    public boolean isEmpty(ItemStack itemStack)
    {
        CraftItemStack craftItemStack = castToCraftItemStack(itemStack);
        net.minecraft.world.item.ItemStack handle = getHandle(craftItemStack);
        if(handle.getComponents().isEmpty())
            return true;
        CustomData data = handle.get(DataComponents.CUSTOM_DATA);
        if (data == null)
            return true;
        return data.isEmpty();
    }

    @Override
    public void remove(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = castToCraftItemStack(itemStack);
        CustomData data = getHandle(craftItemStack).get(DataComponents.CUSTOM_DATA);
        if (data == null)
            return;
        data.getUnsafe().remove(key);
    }

    @Override
    public void merge(CompoundTag handle, CompoundTag otherHandle)
    {
        handle.merge(otherHandle);
    }

    @Override
    public ItemStack asCraftMirror(ItemStack itemStack)
    {
        CraftItemStack craftItemStack = castToCraftItemStack(itemStack);
        return CraftItemStack.asCraftMirror(getHandle(craftItemStack));
    }

    @Override
    public net.minecraft.world.item.ItemStack asNmsCopy(ItemStack itemStack)
    {
        return CraftItemStack.asNMSCopy(itemStack);
    }

    @Override
    public ItemStack compoundToItemStack(CompoundTag compound)
    {
        Optional<net.minecraft.world.item.ItemStack> parsed = net.minecraft.world.item.ItemStack.parse(MinecraftServer.getServer().registryAccess(), compound);
        return CraftItemStack.asBukkitCopy(parsed.get());
    }

    @Override
    public CompoundTag itemStackToCompound(ItemStack itemStack)
    {
        net.minecraft.world.item.ItemStack nmsCopy = asNmsCopy(itemStack);
        return (CompoundTag) nmsCopy.save(MinecraftServer.getServer().registryAccess());
    }

    @Override
    public ItemStack compoundStrToBukkit(String json)
    {
        try
        {
            return compoundToItemStack(TagParser.parseTag(json));
        }
        catch (CommandSyntaxException ignored) {}
        return null;
    }

    @Override
    public boolean isInstanceOfCraftItemStack(ItemStack bukkitItem)
    {
        return bukkitItem instanceof CraftItemStack;
    }

    @Override
    public String toString(ItemStack itemStack)
    {
        return NBT.compound.toString(itemStackToCompound(itemStack));
    }
}
