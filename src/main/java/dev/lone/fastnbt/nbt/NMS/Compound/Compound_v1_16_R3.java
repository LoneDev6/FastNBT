package dev.lone.fastnbt.nbt.NMS.Compound;

import net.minecraft.server.v1_16_R3.NBTTagCompound;
import net.minecraft.server.v1_16_R3.NBTTagList;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class Compound_v1_16_R3 implements ICompound<NBTTagCompound, NBTTagList, NBTTagCompound>
{
    @Override
    public void setByte(NBTTagCompound handle, String key, byte param)
    {
        handle.setByte(key, param);
    }

    @Override
    public void setShort(NBTTagCompound handle, String key, short param)
    {
        handle.setShort(key, param);
    }

    @Override
    public void setInt(NBTTagCompound handle, String key, int param)
    {
        handle.setInt(key, param);
    }

    @Override
    public void setLong(NBTTagCompound handle, String key, long param)
    {
        handle.setLong(key, param);
    }

    @Override
    public void setUUID(NBTTagCompound handle, String key, UUID param)
    {
        handle.setUUID(key, param);
    }

    @Override
    public void setFloat(NBTTagCompound handle, String key, float param)
    {
        handle.setFloat(key, param);
    }

    @Override
    public void setDouble(NBTTagCompound handle, String key, double param)
    {
        handle.setDouble(key, param);
    }

    @Override
    public void setString(NBTTagCompound handle, String key, String param)
    {
        handle.setString(key, param);
    }

    @Override
    public void setByteArray(NBTTagCompound handle, String key, byte[] param)
    {
        handle.setByteArray(key, param);
    }

    @Override
    public void setIntArray(NBTTagCompound handle, String key, int[] param)
    {
        handle.setIntArray(key, param);
    }

    @Override
    public void setIntegerList(NBTTagCompound handle, String key, List<Integer> param)
    {
        handle.b(key, param);
    }

    @Override
    public void setLongArray(NBTTagCompound handle, String key, long[] param)
    {
        handle.a(key, param);
    }

    @Override
    public void setLongList(NBTTagCompound handle, String key, List<Long> param)
    {
        handle.c(key, param);
    }

    @Override
    public void setBoolean(NBTTagCompound handle, String key, boolean param)
    {
        handle.setBoolean(key, param);
    }

    @Override
    public boolean hasKey(NBTTagCompound handle, String key)
    {
        if (handle == null)
            return false;
        return handle.hasKey(key);
    }

    @Override
    public boolean hasUUID(NBTTagCompound handle, String key)
    {
        if (handle == null)
            return false;
        return handle.hasUUID(key);
    }

    @Override
    public @Nullable UUID getUUID(NBTTagCompound handle, String key)
    {
        return handle.getUUID(key);
    }

    @Override
    public byte getByte(NBTTagCompound handle, String key)
    {
        return handle.getByte(key);
    }

    @Override
    public short getShort(NBTTagCompound handle, String key)
    {
        return handle.getShort(key);
    }

    @Override
    public int getInt(NBTTagCompound handle, String key)
    {
        return handle.getInt(key);
    }

    @Override
    public long getLong(NBTTagCompound handle, String key)
    {
        return handle.getLong(key);
    }

    @Override
    public float getFloat(NBTTagCompound handle, String key)
    {
        return handle.getFloat(key);
    }

    @Override
    public double getDouble(NBTTagCompound handle, String key)
    {
        return handle.getDouble(key);
    }

    @Override
    public String getString(NBTTagCompound handle, String key)
    {
        return handle.getString(key);
    }

    @Override
    public byte[] getByteArray(NBTTagCompound handle, String key)
    {
        return handle.getByteArray(key);
    }

    @Override
    public int[] getIntArray(NBTTagCompound handle, String key)
    {
        return handle.getIntArray(key);
    }

    @Override
    public long[] getLongArray(NBTTagCompound handle, String key)
    {
        return handle.getLongArray(key);
    }

    @Override
    public NBTTagCompound getCompound(NBTTagCompound handle, String key)
    {
        if (!handle.hasKey(key))
            return null;
        return handle.getCompound(key);
    }

    @Override
    public NBTTagCompound getOrAddCompound(NBTTagCompound handle, String key)
    {
        if (!handle.hasKey(key))
            handle.set(key, new NBTTagCompound());
        return handle.getCompound(key);
    }

    @Override
    public NBTTagList getList(NBTTagCompound handle, String key, int typeID)
    {
        if (!handle.hasKey(key))
            return null;
        return handle.getList(key, typeID);
    }

    @Override
    public NBTTagList getOrAddList(NBTTagCompound handle, String key, int typeID)
    {
        if (!handle.hasKey(key))
            handle.set(key, new NBTTagList());
        return handle.getList(key, typeID);
    }

    @Override
    public boolean getBoolean(NBTTagCompound handle, String key)
    {
        return handle.getBoolean(key);
    }

    @Override
    public boolean isEmpty(NBTTagCompound handle)
    {
        if (handle == null)
            return true;
        return handle.isEmpty();
    }

    @Override
    public void remove(NBTTagCompound handle, String key)
    {
        if (handle == null)
            return;
        handle.remove(key);
    }

    @Override
    public String toString(NBTTagCompound handle)
    {
        if (handle == null)
            return null;
        return handle.toString();
    }
}
