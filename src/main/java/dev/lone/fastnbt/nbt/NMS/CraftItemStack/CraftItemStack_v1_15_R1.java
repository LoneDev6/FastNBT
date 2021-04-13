package dev.lone.fastnbt.nbt.NMS.CraftItemStack;

import dev.lone.fastnbt.nbt.NBT;
import net.minecraft.server.v1_15_R1.NBTTagCompound;
import net.minecraft.server.v1_15_R1.NBTTagList;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class CraftItemStack_v1_15_R1 implements ICraftItemStack<NBTTagList, NBTTagCompound, CraftItemStack>
{
    public static Field field;
    static
    {
        try
        {
            field = CraftItemStack.class.getDeclaredField("handle");
            field.setAccessible(true);
        } catch (NoSuchFieldException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public NBTTagCompound newCompoundInstance()
    {
        return new NBTTagCompound();
    }

    @Override
    public CraftItemStack convert(ItemStack itemStack)
    {
        if (itemStack instanceof CraftItemStack)
            return ((CraftItemStack) itemStack);
        return CraftItemStack.asCraftCopy(itemStack);
    }

    @Override
    public boolean hasNbt(ItemStack itemStack)
    {
        if(!NBT.isInstanceOfCraftItemStack(itemStack))
        {
            if(itemStack != null && itemStack.hasItemMeta())
                return true;
            return false;
        }
        net.minecraft.server.v1_15_R1.ItemStack handle = getHandle(toCraftItemStack(itemStack));
        if(handle == null)
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

    private net.minecraft.server.v1_15_R1.ItemStack getHandle(CraftItemStack craftItemStack)
    {
        try
        {
            return (net.minecraft.server.v1_15_R1.ItemStack) field.get(craftItemStack);
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
        NBT.compound.setByte(getHandle(craftItemStack).getOrCreateTag(), key, param);
    }

    @Override
    public void setShort(ItemStack itemStack, String key, short param)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        NBT.compound.setShort(getHandle(craftItemStack).getOrCreateTag(), key, param);
    }

    @Override
    public void setInt(ItemStack itemStack, String key, int param)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        NBT.compound.setInt(getHandle(craftItemStack).getOrCreateTag(), key, param);
    }

    @Override
    public void setLong(ItemStack itemStack, String key, long param)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        NBT.compound.setLong(getHandle(craftItemStack).getOrCreateTag(), key, param);
    }

    @Override
    public void setUUID(ItemStack itemStack, String key, UUID param)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        NBT.compound.setUUID(getHandle(craftItemStack).getOrCreateTag(), key, param);
    }

    @Override
    public void setFloat(ItemStack itemStack, String key, float param)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        NBT.compound.setFloat(getHandle(craftItemStack).getOrCreateTag(), key, param);
    }

    @Override
    public void setDouble(ItemStack itemStack, String key, double param)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        NBT.compound.setDouble(getHandle(craftItemStack).getOrCreateTag(), key, param);
    }

    @Override
    public void setString(ItemStack itemStack, String key, String param)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        NBT.compound.setString(getHandle(craftItemStack).getOrCreateTag(), key, param);
    }

    @Override
    public void setByteArray(ItemStack itemStack, String key, byte[] param)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        NBT.compound.setByteArray(getHandle(craftItemStack).getOrCreateTag(), key, param);
    }

    @Override
    public void setIntArray(ItemStack itemStack, String key, int[] param)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        NBT.compound.setIntArray(getHandle(craftItemStack).getOrCreateTag(), key, param);
    }

    @Override
    public void setIntegerList(ItemStack itemStack, String key, List<Integer> param)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        NBT.compound.setIntegerList(getHandle(craftItemStack).getOrCreateTag(), key, param);
    }

    @Override
    public void setLongArray(ItemStack itemStack, String key, long[] param)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        NBT.compound.setLongArray(getHandle(craftItemStack).getOrCreateTag(), key, param);
    }

    @Override
    public void setLongList(ItemStack itemStack, String key, List<Long> param)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        NBT.compound.setLongList(getHandle(craftItemStack).getOrCreateTag(), key, param);
    }

    @Override
    public void setBoolean(ItemStack itemStack, String key, boolean param)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        NBT.compound.setBoolean(getHandle(craftItemStack).getOrCreateTag(), key, param);
    }

    @Override
    public boolean hasKey(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        if (!getHandle(craftItemStack).hasTag())
            return false;
        return NBT.compound.hasKey(getHandle(craftItemStack).getTag(), key);
    }

    @Override
    public boolean hasUUID(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        if (!getHandle(craftItemStack).hasTag())
            return false;
        return NBT.compound.hasUUID(getHandle(craftItemStack).getTag(), key);
    }

    @Override
    public @Nullable UUID getUUID(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        return NBT.compound.getUUID(getHandle(craftItemStack).getOrCreateTag(), key);
    }

    @Override
    public byte getByte(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        return NBT.compound.getByte(getHandle(craftItemStack).getOrCreateTag(), key);
    }

    @Override
    public short getShort(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        return NBT.compound.getShort(getHandle(craftItemStack).getOrCreateTag(), key);
    }

    @Override
    public int getInt(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        return NBT.compound.getInt(getHandle(craftItemStack).getOrCreateTag(), key);
    }

    @Override
    public long getLong(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        return NBT.compound.getLong(getHandle(craftItemStack).getOrCreateTag(), key);
    }

    @Override
    public float getFloat(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        return NBT.compound.getFloat(getHandle(craftItemStack).getOrCreateTag(), key);
    }

    @Override
    public double getDouble(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        return NBT.compound.getDouble(getHandle(craftItemStack).getOrCreateTag(), key);
    }

    @Override
    public String getString(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        return NBT.compound.getString(getHandle(craftItemStack).getOrCreateTag(), key);
    }

    @Override
    public byte[] getByteArray(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        return NBT.compound.getByteArray(getHandle(craftItemStack).getOrCreateTag(), key);
    }

    @Override
    public int[] getIntArray(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        return NBT.compound.getIntArray(getHandle(craftItemStack).getOrCreateTag(), key);
    }

    @Override
    public long[] getLongArray(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        return NBT.compound.getLongArray(getHandle(craftItemStack).getOrCreateTag(), key);
    }

    @Override
    public NBTTagCompound getCompound(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        return (NBTTagCompound) NBT.compound.getCompound(getHandle(craftItemStack).getOrCreateTag(), key);
    }

    @Override
    public NBTTagCompound getOrAddCompound(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        return (NBTTagCompound) NBT.compound.getOrAddCompound(getHandle(craftItemStack).getOrCreateTag(), key);
    }

    @Override
    public NBTTagList getList(ItemStack itemStack, String key, int typeID)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        return (NBTTagList) NBT.compound.getList(getHandle(craftItemStack).getOrCreateTag(), key, typeID);
    }

    @Override
    public NBTTagList getOrAddList(ItemStack itemStack, String key, int typeID)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        return (NBTTagList) NBT.compound.getOrAddList(getHandle(craftItemStack).getOrCreateTag(), key, typeID);
    }

    @Override
    public boolean getBoolean(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        return NBT.compound.getBoolean(getHandle(craftItemStack).getOrCreateTag(), key);
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
        return NBT.compound.isEmpty(getHandle(craftItemStack).getTag());
    }

    @Override
    public void remove(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        if (!getHandle(craftItemStack).hasTag())
            return;
        NBT.compound.remove(getHandle(craftItemStack).getTag(), key);
    }

    @Override
    public void merge(NBTTagCompound handle, NBTTagCompound otherHandle)
    {
        handle.a(otherHandle);
    }

    @Override
    public ItemStack asBukkitMirror(ItemStack itemStack)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        return CraftItemStack.asCraftMirror(getHandle(craftItemStack));
    }

    @Override
    public Object asNMSCopy(ItemStack itemStack)
    {
        return CraftItemStack.asNMSCopy(itemStack);
    }

    @Override
    public ItemStack compoundToItemStack(NBTTagCompound compound)
    {
        return CraftItemStack.asCraftMirror(net.minecraft.server.v1_15_R1.ItemStack.a(compound));
    }

    @Override
    public String toString(ItemStack itemStack)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        if (!getHandle(craftItemStack).hasTag())
            return null;
        return NBT.compound.toString(getHandle(craftItemStack).getTag());
    }
}
