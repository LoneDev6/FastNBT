package dev.lone.fastnbt.nms.nbt.impl;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import org.jetbrains.annotations.Nullable;
import dev.lone.fastnbt.nms.Implementation;
import dev.lone.fastnbt.nms.Version;
import dev.lone.fastnbt.nms.nbt.Nbt;
import dev.lone.fastnbt.nms.nbt.nms.ICraftItemStack;
import net.minecraft.server.v1_16_R3.MojangsonParser;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import net.minecraft.server.v1_16_R3.NBTTagList;
import org.apache.commons.lang.reflect.FieldUtils;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Implementation.CyclicDependency(type = ICraftItemStack.class, version = Version.v1_16_R3)
@SuppressWarnings({"unchecked", "DataFlowIssue", "CallToPrintStackTrace", "unused"})
public class CraftItemStack_v1_16_R3 implements ICraftItemStack<NBTTagList, NBTTagCompound, CraftItemStack>
{
    public static Field FIELD_HANDLE = FieldUtils.getField(CraftItemStack.class, "handle", true);

    @Override
    public NBTTagCompound newCompoundInstance()
    {
        return new NBTTagCompound();
    }

    @Override
    public CraftItemStack convertToCraft(ItemStack itemStack)
    {
        if (itemStack instanceof CraftItemStack)
            return ((CraftItemStack) itemStack);
        return CraftItemStack.asCraftCopy(itemStack);
    }

    @Override
    public boolean hasNbt(ItemStack itemStack)
    {
        if (!Nbt.isInstanceOfCraftItemStack(itemStack))
            return itemStack != null && itemStack.hasItemMeta();
        net.minecraft.server.v1_16_R3.ItemStack handle = getHandle(toCraftItemStack(itemStack));
        if (handle == null)
            return false;
        return handle.hasTag();
    }

    @Override
    public void merge(ItemStack itemStack, ItemStack otherItem)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        CraftItemStack other = toCraftItemStack(otherItem);
        this.merge(getHandle(craftItemStack).getOrCreateTag(), getHandle(other).getOrCreateTag());
    }

    private net.minecraft.server.v1_16_R3.ItemStack getHandle(org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack craftItemStack)
    {
        try
        {
            return (net.minecraft.server.v1_16_R3.ItemStack) FIELD_HANDLE.get(craftItemStack);
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private CraftItemStack toCraftItemStack(ItemStack itemStack)
    {
        return ((CraftItemStack) itemStack);
    }

    @Override
    public void setByte(ItemStack itemStack, String key, byte param)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        Nbt.compound.setByte(getHandle(craftItemStack).getOrCreateTag(), key, param);
    }

    @Override
    public void setShort(ItemStack itemStack, String key, short param)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        Nbt.compound.setShort(getHandle(craftItemStack).getOrCreateTag(), key, param);
    }

    @Override
    public void setInt(ItemStack itemStack, String key, int param)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        Nbt.compound.setInt(getHandle(craftItemStack).getOrCreateTag(), key, param);
    }

    @Override
    public void setLong(ItemStack itemStack, String key, long param)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        Nbt.compound.setLong(getHandle(craftItemStack).getOrCreateTag(), key, param);
    }

    @Override
    public void setUUID(ItemStack itemStack, String key, UUID param)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        Nbt.compound.setUUID(getHandle(craftItemStack).getOrCreateTag(), key, param);
    }

    @Override
    public void setFloat(ItemStack itemStack, String key, float param)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        Nbt.compound.setFloat(getHandle(craftItemStack).getOrCreateTag(), key, param);
    }

    @Override
    public void setDouble(ItemStack itemStack, String key, double param)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        Nbt.compound.setDouble(getHandle(craftItemStack).getOrCreateTag(), key, param);
    }

    @Override
    public void setString(ItemStack itemStack, String key, String param)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        Nbt.compound.setString(getHandle(craftItemStack).getOrCreateTag(), key, param);
    }

    @Override
    public void setByteArray(ItemStack itemStack, String key, byte[] param)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        Nbt.compound.setByteArray(getHandle(craftItemStack).getOrCreateTag(), key, param);
    }

    @Override
    public void setIntArray(ItemStack itemStack, String key, int[] param)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        Nbt.compound.setIntArray(getHandle(craftItemStack).getOrCreateTag(), key, param);
    }

    @Override
    public void setIntegerList(ItemStack itemStack, String key, List<Integer> param)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        Nbt.compound.setIntegerList(getHandle(craftItemStack).getOrCreateTag(), key, param);
    }

    @Override
    public void setLongArray(ItemStack itemStack, String key, long[] param)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        Nbt.compound.setLongArray(getHandle(craftItemStack).getOrCreateTag(), key, param);
    }

    @Override
    public void setLongList(ItemStack itemStack, String key, List<Long> param)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        Nbt.compound.setLongList(getHandle(craftItemStack).getOrCreateTag(), key, param);
    }

    @Override
    public void setBoolean(ItemStack itemStack, String key, boolean param)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        Nbt.compound.setBoolean(getHandle(craftItemStack).getOrCreateTag(), key, param);
    }

    @Override
    public boolean hasKey(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        if (!getHandle(craftItemStack).hasTag())
            return false;
        return Nbt.compound.hasKey(getHandle(craftItemStack).getTag(), key);
    }

    @Override
    public boolean hasUUID(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        if (!getHandle(craftItemStack).hasTag())
            return false;
        return Nbt.compound.hasUUID(getHandle(craftItemStack).getTag(), key);
    }

    @Override
    public @Nullable UUID getUUID(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        return Nbt.compound.getUUID(getHandle(craftItemStack).getOrCreateTag(), key);
    }

    @Override
    public byte getByte(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        return Nbt.compound.getByte(getHandle(craftItemStack).getOrCreateTag(), key);
    }

    @Override
    public short getShort(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        return Nbt.compound.getShort(getHandle(craftItemStack).getOrCreateTag(), key);
    }

    @Override
    public int getInt(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        return Nbt.compound.getInt(getHandle(craftItemStack).getOrCreateTag(), key);
    }

    @Override
    public long getLong(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        return Nbt.compound.getLong(getHandle(craftItemStack).getOrCreateTag(), key);
    }

    @Override
    public float getFloat(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        return Nbt.compound.getFloat(getHandle(craftItemStack).getOrCreateTag(), key);
    }

    @Override
    public double getDouble(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        return Nbt.compound.getDouble(getHandle(craftItemStack).getOrCreateTag(), key);
    }

    @Override
    public String getString(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        return Nbt.compound.getString(getHandle(craftItemStack).getOrCreateTag(), key);
    }

    @Override
    public byte[] getByteArray(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        return Nbt.compound.getByteArray(getHandle(craftItemStack).getOrCreateTag(), key);
    }

    @Override
    public int[] getIntArray(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        return Nbt.compound.getIntArray(getHandle(craftItemStack).getOrCreateTag(), key);
    }

    @Override
    public long[] getLongArray(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        return Nbt.compound.getLongArray(getHandle(craftItemStack).getOrCreateTag(), key);
    }

    @Override
    public NBTTagCompound getCompound(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        return (NBTTagCompound) Nbt.compound.getCompound(getHandle(craftItemStack).getOrCreateTag(), key);
    }

    @Override
    public NBTTagCompound getOrAddCompound(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        return (NBTTagCompound) Nbt.compound.getOrAddCompound(getHandle(craftItemStack).getOrCreateTag(), key);
    }

    @Override
    public NBTTagList getList(ItemStack itemStack, String key, int typeID)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        return (NBTTagList) Nbt.compound.getList(getHandle(craftItemStack).getOrCreateTag(), key, typeID);
    }

    @Override
    public NBTTagList getOrAddList(ItemStack itemStack, String key, int typeID)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        return (NBTTagList) Nbt.compound.getOrAddList(getHandle(craftItemStack).getOrCreateTag(), key, typeID);
    }

    @Override
    public void putTag(ItemStack itemStack, String key, Object value)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        Nbt.compound.putTag(getHandle(craftItemStack).getOrCreateTag(), key, value);
    }

    @Override
    public boolean getBoolean(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        return Nbt.compound.getBoolean(getHandle(craftItemStack).getOrCreateTag(), key);
    }

    @Override
    public Set<String> getKeys(ItemStack itemStack)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        return getHandle(craftItemStack).getOrCreateTag().getKeys();
    }

    @Override
    public boolean isEmpty(ItemStack itemStack)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        if (!getHandle(craftItemStack).hasTag())
            return true;
        return Nbt.compound.isEmpty(getHandle(craftItemStack).getTag());
    }

    @Override
    public void remove(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        if (!getHandle(craftItemStack).hasTag())
            return;
        Nbt.compound.remove(getHandle(craftItemStack).getTag(), key);
    }

    @Override
    public void merge(NBTTagCompound handle, NBTTagCompound otherHandle)
    {
        handle.a(otherHandle);
    }

    @Override
    public ItemStack asCraftMirror(ItemStack itemStack)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        return getHandle(craftItemStack).asBukkitMirror();
    }

    @Override
    public net.minecraft.server.v1_16_R3.ItemStack asNmsCopy(ItemStack itemStack)
    {
        return CraftItemStack.asNMSCopy(itemStack);
    }

    @Override
    public ItemStack compoundToItemStack(NBTTagCompound compound)
    {
        return net.minecraft.server.v1_16_R3.ItemStack.fromCompound(compound).asBukkitMirror();
    }

    @Override
    public NBTTagCompound itemStackToCompound(ItemStack itemStack)
    {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        net.minecraft.server.v1_16_R3.ItemStack nmsCopy = asNmsCopy(itemStack);
        nmsCopy.save(nbtTagCompound);
        return nbtTagCompound;
    }

    @Override
    public ItemStack compoundStrToBukkit(String json)
    {
        try
        {
            return compoundToItemStack(MojangsonParser.parse(json));
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
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        net.minecraft.server.v1_16_R3.ItemStack handle = getHandle(craftItemStack);
        if (handle == null || !handle.hasTag())
            return null;

        NBTTagCompound nbt = new NBTTagCompound();
        handle.save(nbt);
        return Nbt.compound.toString(nbt);
    }
}
