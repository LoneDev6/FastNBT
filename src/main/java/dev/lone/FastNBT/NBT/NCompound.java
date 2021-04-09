package dev.lone.FastNBT.NBT;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class NCompound<T>
{
    T handle;

    public NCompound(T handle)
    {
        this.handle = handle;
    }

    public void setByte(String key, byte param)
    {
        NBT.compound().setByte(handle, key, param);
    }

    public void setShort(String key, short param)
    {
        NBT.compound().setShort(handle, key, param);
    }

    public void setInt(String key, int param)
    {
        NBT.compound().setInt(handle, key, param);
    }

    public void setLong(String key, long param)
    {
        NBT.compound().setLong(handle, key, param);
    }

    public void setUUID(String key, UUID param)
    {
        NBT.compound().setUUID(handle, key, param);
    }

    public void setFloat(String key, float param)
    {
        NBT.compound().setFloat(handle, key, param);
    }

    public void setDouble(String key, double param)
    {
        NBT.compound().setDouble(handle, key, param);
    }

    public void setString(String key, String param)
    {
        NBT.compound().setString(handle, key, param);
    }

    public void setByteArray(String key, byte[] param)
    {
        NBT.compound().setByteArray(handle, key, param);
    }

    public void setIntArray(String key, int[] param)
    {
        NBT.compound().setIntArray(handle, key, param);
    }

    public void setIntegerList(String key, List<Integer> param)
    {
        NBT.compound().setIntegerList(handle, key, param);
    }

    public void setLongArray(String key, long[] param)
    {
        NBT.compound().setLongArray(handle, key, param);
    }

    public void setLongList(String key, List<Long> param)
    {
        NBT.compound().setLongList(handle, key, param);
    }

    public void setBoolean(String key, boolean param)
    {
        NBT.compound().setBoolean(handle, key, param);
    }

    public boolean hasKey(String key)
    {
        return NBT.compound().hasKey(handle, key);
    }

    public boolean hasUUID(String key)
    {
        return NBT.compound().hasUUID(handle, key);
    }

    @Nullable
    public UUID getUUID(String key)
    {
        return NBT.compound().getUUID(handle, key);
    }

    public byte getByte(String key)
    {
        return NBT.compound().getByte(handle, key);
    }

    public short getShort(String key)
    {
        return NBT.compound().getShort(handle, key);
    }

    public int getInt(String key)
    {
        return NBT.compound().getInt(handle, key);
    }

    public long getLong(String key)
    {
        return NBT.compound().getLong(handle, key);
    }

    public float getFloat(String key)
    {
        return NBT.compound().getFloat(handle, key);
    }

    public double getDouble(String key)
    {
        return NBT.compound().getDouble(handle, key);
    }

    public String getString(String key)
    {
        return NBT.compound().getString(handle, key);
    }

    public byte[] getByteArray(String key)
    {
        return NBT.compound().getByteArray(handle, key);
    }

    public int[] getIntArray(String key)
    {
        return NBT.compound().getIntArray(handle, key);
    }

    public long[] getLongArray(String key)
    {
        return NBT.compound().getLongArray(handle, key);
    }

    @Nullable
    public Object getCompound(String key)
    {
        return NBT.compound().getCompound(handle, key);
    }

    public Object getOrAddCompound(String key)
    {
        return NBT.compound().getOrAddCompound(handle, key);
    }

    @Nullable
    public Object getList(String key, int typeID)
    {
        return NBT.compound().getList(handle, key, typeID);
    }

    public Object getOrAddList(String key, int typeID)
    {
        return NBT.compound().getOrAddList(handle, key, typeID);
    }

    public boolean getBoolean(String key)
    {
        return NBT.compound().getBoolean(handle, key);
    }

    public boolean isEmpty()
    {
        return NBT.compound().isEmpty(handle);
    }

    public void remove(String key)
    {
        NBT.compound().remove(handle, key);
    }

    public String toString()
    {
        return NBT.compound().toString();
    }
}
