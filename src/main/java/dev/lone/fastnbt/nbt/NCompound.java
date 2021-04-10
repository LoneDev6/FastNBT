package dev.lone.fastnbt.nbt;

import dev.lone.fastnbt.nbt.NMS.Compound.ICompound;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class NCompound<T>
{
    protected ICompound handler;
    protected T handle;

    public NCompound()
    {
        this.handle = (T) NBT.compound().newCompoundInstance();
        this.handler = NBT.compound();
    }

    public NCompound(T handle)
    {
        this.handle = handle;
        this.handler = NBT.compound();
    }

    public Object getInternal()
    {
        return handle;
    }

    public NCompound<T> setByte(String key, byte param)
    {
        handler.setByte(handle, key, param);
        return this;
    }

    public NCompound<T> setShort(String key, short param)
    {
        handler.setShort(handle, key, param);
        return this;
    }

    public NCompound<T> setInt(String key, int param)
    {
        handler.setInt(handle, key, param);
        return this;
    }

    public NCompound<T> setLong(String key, long param)
    {
        handler.setLong(handle, key, param);
        return this;
    }

    public NCompound<T> setUUID(String key, UUID param)
    {
        handler.setUUID(handle, key, param);
        return this;
    }

    public NCompound<T> setFloat(String key, float param)
    {
        handler.setFloat(handle, key, param);
        return this;
    }

    public NCompound<T> setDouble(String key, double param)
    {
        handler.setDouble(handle, key, param);
        return this;
    }

    public NCompound<T> setString(String key, String param)
    {
        handler.setString(handle, key, param);
        return this;
    }

    public NCompound<T> setByteArray(String key, byte[] param)
    {
        handler.setByteArray(handle, key, param);
        return this;
    }

    public NCompound<T> setIntArray(String key, int[] param)
    {
        handler.setIntArray(handle, key, param);
        return this;
    }

    public NCompound<T> setIntegerList(String key, List<Integer> param)
    {
        handler.setIntegerList(handle, key, param);
        return this;
    }

    public NCompound<T> setLongArray(String key, long[] param)
    {
        handler.setLongArray(handle, key, param);
        return this;
    }

    public NCompound<T> setLongList(String key, List<Long> param)
    {
        handler.setLongList(handle, key, param);
        return this;
    }

    public NCompound<T> setBoolean(String key, boolean param)
    {
        handler.setBoolean(handle, key, param);
        return this;
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
        if(handler.hasKey(handle, key))
            return new NCompound(handler.getCompound(handle, key));
        return null;
    }

    public NCompound getOrAddCompound(String key)
    {
        return new NCompound(handler.getOrAddCompound(handle, key));
    }

    @Nullable
    public NTagList getList(String key, NBTTypeId type)
    {
        if(handler.hasKey(handle, key))
            return new NTagList(handler.getList(handle, key, type.id));
        return null;
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

    public NCompound<T> remove(String key)
    {
        handler.remove(handle, key);
        return this;
    }

    public NCompound<T> merge(NCompound b)
    {
        handler.merge(this, b);
        return this;
    }

    public String toString()
    {
        return handler.toString(handle);
    }
}
