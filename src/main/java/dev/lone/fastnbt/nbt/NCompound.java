package dev.lone.fastnbt.nbt;

import dev.lone.fastnbt.nbt.NMS.Compound.ICompound;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class NCompound<T>
{
    ICompound handler;
    T handle;

    public NCompound() { }

    public NCompound(T handle)
    {
        this.handle = handle;
        this.handler = NBT.compound();
    }

    public void setByte(String key, byte param)
    {
        handler.setByte(handle, key, param);
    }

    public void setShort(String key, short param)
    {
        handler.setShort(handle, key, param);
    }

    public void setInt(String key, int param)
    {
        handler.setInt(handle, key, param);
    }

    public void setLong(String key, long param)
    {
        handler.setLong(handle, key, param);
    }

    public void setUUID(String key, UUID param)
    {
        handler.setUUID(handle, key, param);
    }

    public void setFloat(String key, float param)
    {
        handler.setFloat(handle, key, param);
    }

    public void setDouble(String key, double param)
    {
        handler.setDouble(handle, key, param);
    }

    public void setString(String key, String param)
    {
        handler.setString(handle, key, param);
    }

    public void setByteArray(String key, byte[] param)
    {
        handler.setByteArray(handle, key, param);
    }

    public void setIntArray(String key, int[] param)
    {
        handler.setIntArray(handle, key, param);
    }

    public void setIntegerList(String key, List<Integer> param)
    {
        handler.setIntegerList(handle, key, param);
    }

    public void setLongArray(String key, long[] param)
    {
        handler.setLongArray(handle, key, param);
    }

    public void setLongList(String key, List<Long> param)
    {
        handler.setLongList(handle, key, param);
    }

    public void setBoolean(String key, boolean param)
    {
        handler.setBoolean(handle, key, param);
    }

    public boolean hasKey(String key)
    {
        return handler.hasKey(handle, key);
    }

    public boolean hasUUID(String key)
    {
        return handler.hasUUID(handle, key);
    }

    @Nullable
    public UUID getUUID(String key)
    {
        return handler.getUUID(handle, key);
    }

    public byte getByte(String key)
    {
        return handler.getByte(handle, key);
    }

    public short getShort(String key)
    {
        return handler.getShort(handle, key);
    }

    public int getInt(String key)
    {
        return handler.getInt(handle, key);
    }

    public long getLong(String key)
    {
        return handler.getLong(handle, key);
    }

    public float getFloat(String key)
    {
        return handler.getFloat(handle, key);
    }

    public double getDouble(String key)
    {
        return handler.getDouble(handle, key);
    }

    public String getString(String key)
    {
        return handler.getString(handle, key);
    }

    public byte[] getByteArray(String key)
    {
        return handler.getByteArray(handle, key);
    }

    public int[] getIntArray(String key)
    {
        return handler.getIntArray(handle, key);
    }

    public long[] getLongArray(String key)
    {
        return handler.getLongArray(handle, key);
    }

    @Nullable
    public Object getCompound(String key)
    {
        return handler.getCompound(handle, key);
    }

    public NCompound getOrAddCompound(String key)
    {
        return new NCompound(handler.getOrAddCompound(handle, key));
    }

    @Nullable
    public Object getList(String key, NBTTypeId type)
    {
        return handler.getList(handle, key, type.id);
    }

    public NTagList getOrAddList(String key, NBTTypeId type)
    {
        return new NTagList(handler.getOrAddList(handle, key, type.id));
    }

    public boolean getBoolean(String key)
    {
        return handler.getBoolean(handle, key);
    }

    public boolean isEmpty()
    {
        return handler.isEmpty(handle);
    }

    public void remove(String key)
    {
        handler.remove(handle, key);
    }

    public String toString()
    {
        return handler.toString();
    }
}
