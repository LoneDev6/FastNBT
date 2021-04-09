package dev.lone.FastNBT.NBT.impl.NBT;

import net.minecraft.server.v1_16_R3.NBTTagCompound;
import net.minecraft.server.v1_16_R3.NBTTagList;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class NBT_v1_16_R3 implements INBT<NBTTagCompound, NBTTagList>
{
    @Override
    public void setByte(NBTTagCompound handle, String s, byte param)
    {
        handle.setByte(s, param);
    }

    @Override
    public void setShort(NBTTagCompound handle, String s, short param)
    {
        handle.setShort(s, param);
    }

    @Override
    public void setInt(NBTTagCompound handle, String s, int param)
    {
        handle.setInt(s, param);
    }

    @Override
    public void setLong(NBTTagCompound handle, String s, long param)
    {
        handle.setLong(s, param);
    }

    @Override
    public void setUUID(NBTTagCompound handle, String s, UUID param)
    {
        handle.setUUID(s, param);
    }

    @Override
    public void setFloat(NBTTagCompound handle, String s, float param)
    {
        handle.setFloat(s, param);
    }

    @Override
    public void setDouble(NBTTagCompound handle, String s, double param)
    {
        handle.setDouble(s, param);
    }

    @Override
    public void setString(NBTTagCompound handle, String s, String param)
    {
        handle.setString(s, param);
    }

    @Override
    public void setByteArray(NBTTagCompound handle, String s, byte[] param)
    {
        handle.setByteArray(s, param);
    }

    @Override
    public void setIntArray(NBTTagCompound handle, String s, int[] param)
    {
        handle.setIntArray(s, param);
    }

    @Override
    public void setIntegerList(NBTTagCompound handle, String s, List<Integer> param)
    {
        handle.b(s, param);
    }

    @Override
    public void setLongArray(NBTTagCompound handle, String s, long[] param)
    {
        handle.a(s, param);
    }

    @Override
    public void setLongList(NBTTagCompound handle, String s, List<Long> param)
    {
        handle.c(s, param);
    }

    @Override
    public void setBoolean(NBTTagCompound handle, String s, boolean param)
    {
        handle.setBoolean(s, param);
    }

    @Override
    public boolean hasKey(NBTTagCompound handle, String s)
    {
        if(handle == null)
            return false;
        return handle.hasKey(s);
    }

    @Override
    public boolean hasUUID(NBTTagCompound handle, String param)
    {
        if(handle == null)
            return false;
        return handle.hasUUID(param);
    }

    @Override
    public @Nullable UUID getUUID(NBTTagCompound handle, String param)
    {
        return handle.getUUID(param);
    }

    @Override
    public byte getByte(NBTTagCompound handle, String s)
    {
        return handle.getByte(s);
    }

    @Override
    public short getShort(NBTTagCompound handle, String s)
    {
        return handle.getShort(s);
    }

    @Override
    public int getInt(NBTTagCompound handle, String s)
    {
        return handle.getInt(s);
    }

    @Override
    public long getLong(NBTTagCompound handle, String s)
    {
        return handle.getLong(s);
    }

    @Override
    public float getFloat(NBTTagCompound handle, String s)
    {
        return handle.getFloat(s);
    }

    @Override
    public double getDouble(NBTTagCompound handle, String s)
    {
        return handle.getDouble(s);
    }

    @Override
    public String getString(NBTTagCompound handle, String s)
    {
        return handle.getString(s);
    }

    @Override
    public byte[] getByteArray(NBTTagCompound handle, String s)
    {
        return handle.getByteArray(s);
    }

    @Override
    public int[] getIntArray(NBTTagCompound handle, String s)
    {
        return handle.getIntArray(s);
    }

    @Override
    public long[] getLongArray(NBTTagCompound handle, String s)
    {
        return handle.getLongArray(s);
    }

    @Override
    public NBTTagCompound getCompound(NBTTagCompound handle, String s)
    {
        return handle.getCompound(s);
    }

    @Override
    public NBTTagList getList(NBTTagCompound handle, String s, int typeID)
    {
        return handle.getList(s, typeID);
    }

    @Override
    public boolean getBoolean(NBTTagCompound handle, String s)
    {
        return handle.getBoolean(s);
    }

    @Override
    public boolean isEmpty(NBTTagCompound handle)
    {
        if(handle == null)
            return true;
        return handle.isEmpty();
    }

    @Override
    public void remove(NBTTagCompound handle, String s)
    {
        if(handle == null)
            return;
        handle.remove(s);
    }

    @Override
    public String toString(NBTTagCompound handle)
    {
        if(handle == null)
            return null;
        return handle.toString();
    }
}
