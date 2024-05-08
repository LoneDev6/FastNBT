package dev.lone.fastnbt.nms.nbt;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.lone.fastnbt.nms.Implementation;
import dev.lone.fastnbt.nms.Version;
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
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Implementation.CyclicDependency(type = ICraftItemStack.class, version = Version.v1_20_6)
@SuppressWarnings({"unchecked", "DataFlowIssue", "CallToPrintStackTrace", "unused", "deprecation"})
public class CraftItemStack_v1_20_6 implements ICraftItemStack<ListTag, CompoundTag, CraftItemStack>
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

    @Override
    public void merge(ItemStack itemStack, ItemStack otherItem)
    {
        CraftItemStack craftItemStack = castToCraftItemStack(itemStack);
        CraftItemStack other = castToCraftItemStack(otherItem);
        getHandle(craftItemStack).applyComponents(other.handle.getComponents());
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
            return data.getUnsafe().tags.keySet();
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
