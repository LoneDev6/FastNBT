package dev.lone.FastNBT.NBT;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class NItem
{
    ItemStack itemStack;

    public NItem(ItemStack itemStack)
    {
        if (itemStack.getClass().getSuperclass() == Object.class)
            this.itemStack = NBT.item().convert(itemStack);
        this.itemStack = itemStack;
    }

    public void setByte(String key, byte param)
    {
        NBT.item().setByte(itemStack, key, param);
    }

    public void setShort(String key, short param)
    {
        NBT.item().setShort(itemStack, key, param);
    }

    public void setInt(String key, int param)
    {
        NBT.item().setInt(itemStack, key, param);
    }

    public void setLong(String key, long param)
    {
        NBT.item().setLong(itemStack, key, param);
    }

    public void setUUID(String key, UUID param)
    {
        NBT.item().setUUID(itemStack, key, param);
    }

    public void setFloat(String key, float param)
    {
        NBT.item().setFloat(itemStack, key, param);
    }

    public void setDouble(String key, double param)
    {
        NBT.item().setDouble(itemStack, key, param);
    }

    public void setString(String key, String param)
    {
        NBT.item().setString(itemStack, key, param);
    }

    public void setByteArray(String key, byte[] param)
    {
        NBT.item().setByteArray(itemStack, key, param);
    }

    public void setIntArray(String key, int[] param)
    {
        NBT.item().setIntArray(itemStack, key, param);
    }

    public void setIntegerList(String key, List<Integer> param)
    {
        NBT.item().setIntegerList(itemStack, key, param);
    }

    public void setLongArray(String key, long[] param)
    {
        NBT.item().setLongArray(itemStack, key, param);
    }

    public void setLongList(String key, List<Long> param)
    {
        NBT.item().setLongList(itemStack, key, param);
    }

    public void setBoolean(String key, boolean param)
    {
        NBT.item().setBoolean(itemStack, key, param);
    }

    public boolean hasKey(String key)
    {
        return NBT.item().hasKey(itemStack, key);
    }

    public boolean hasUUID(String key)
    {
        return NBT.item().hasUUID(itemStack, key);
    }

    @Nullable
    public UUID getUUID(String key)
    {
        return NBT.item().getUUID(itemStack, key);
    }

    public byte getByte(String key)
    {
        return NBT.item().getByte(itemStack, key);
    }

    public short getShort(String key)
    {
        return NBT.item().getShort(itemStack, key);
    }

    public int getInt(String key)
    {
        return NBT.item().getInt(itemStack, key);
    }

    public long getLong(String key)
    {
        return NBT.item().getLong(itemStack, key);
    }

    public float getFloat(String key)
    {
        return NBT.item().getFloat(itemStack, key);
    }

    public double getDouble(String key)
    {
        return NBT.item().getDouble(itemStack, key);
    }

    public String getString(String key)
    {
        return NBT.item().getString(itemStack, key);
    }

    public byte[] getByteArray(String key)
    {
        return NBT.item().getByteArray(itemStack, key);
    }

    public int[] getIntArray(String key)
    {
        return NBT.item().getIntArray(itemStack, key);
    }

    public long[] getLongArray(String key)
    {
        return NBT.item().getLongArray(itemStack, key);
    }

    @Nullable
    public Object getCompound(String key)
    {
        return NBT.item().getCompound(itemStack, key);
    }

    public Object getOrAddCompound(String key)
    {
        return NBT.item().getOrAddCompound(itemStack, key);
    }

    @Nullable
    public Object getList(String key, int typeID)
    {
        return NBT.item().getList(itemStack, key, typeID);
    }

    public Object getOrAddList(String key, int typeID)
    {
        return NBT.item().getOrAddList(itemStack, key, typeID);
    }

    public boolean getBoolean(String key)
    {
        return NBT.item().getBoolean(itemStack, key);
    }

    public boolean isEmpty()
    {
        return NBT.item().isEmpty(itemStack);
    }

    public void remove(String key)
    {
        NBT.item().remove(itemStack, key);
    }

    public String toString()
    {
        return NBT.item().toString();
    }
}
