package dev.lone.FastNBT.NBT.impl.Compound;

import dev.lone.FastNBT.NBT.impl.NBT.NBT_v1_16_R3;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class Compound_v1_16_R3 extends NBT_v1_16_R3 implements ICompound<NBTTagCompound>
{
    @Override
    public void setByte(NBTTagCompound compound, String s, byte param)
    {
        
    }

    @Override
    public void setShort(NBTTagCompound compound, String s, short param)
    {

    }

    @Override
    public void setInt(NBTTagCompound compound, String s, int param)
    {

    }

    @Override
    public void setLong(NBTTagCompound compound, String s, long param)
    {

    }

    @Override
    public void setUUID(NBTTagCompound compound, String s, UUID param)
    {

    }

    @Override
    public void setFloat(NBTTagCompound compound, String s, float param)
    {

    }

    @Override
    public void setDouble(NBTTagCompound compound, String s, double param)
    {

    }

    @Override
    public void setString(NBTTagCompound compound, String s, String param)
    {
        setstr
    }

    @Override
    public void setByteArray(NBTTagCompound compound, String s, byte[] param)
    {

    }

    @Override
    public void setIntArray(NBTTagCompound compound, String s, int[] param)
    {

    }

    @Override
    public void setIntegerList(NBTTagCompound compound, String s, List<Integer> param)
    {

    }

    @Override
    public void setLongArray(NBTTagCompound compound, String s, long[] param)
    {

    }

    @Override
    public void setLongList(NBTTagCompound compound, String s, List<Long> param)
    {

    }

    @Override
    public void setBoolean(NBTTagCompound compound, String s, boolean param)
    {

    }

    @Override
    public boolean hasKey(NBTTagCompound compound, String s)
    {
        return false;
    }

    @Override
    public boolean hasUUID(NBTTagCompound compound, String param)
    {
        return false;
    }

    @Override
    public @Nullable UUID getUUID(NBTTagCompound compound, String param)
    {
        return null;
    }

    @Override
    public byte getByte(NBTTagCompound compound, String s)
    {
        return 0;
    }

    @Override
    public short getShort(NBTTagCompound compound, String s)
    {
        return 0;
    }

    @Override
    public int getInt(NBTTagCompound compound, String s)
    {
        return 0;
    }

    @Override
    public long getLong(NBTTagCompound compound, String s)
    {
        return 0;
    }

    @Override
    public float getFloat(NBTTagCompound compound, String s)
    {
        return 0;
    }

    @Override
    public double getDouble(NBTTagCompound compound, String s)
    {
        return 0;
    }

    @Override
    public String getString(NBTTagCompound compound, String s)
    {
        return null;
    }

    @Override
    public byte[] getByteArray(NBTTagCompound compound, String s)
    {
        return new byte[0];
    }

    @Override
    public int[] getIntArray(NBTTagCompound compound, String s)
    {
        return new int[0];
    }

    @Override
    public long[] getLongArray(NBTTagCompound compound, String s)
    {
        return new long[0];
    }

    @Override
    public NBTTagCompound getCompound(ItemStack itemStack, String s)
    {
        return null;
    }

    @Override
    public NBTTagCompound getList(ItemStack itemStack, String s, int typeID)
    {
        return null;
    }

    @Override
    public boolean getBoolean(NBTTagCompound compound, String s)
    {
        return false;
    }

    @Override
    public boolean isEmpty(ItemStack itemStack)
    {
        return false;
    }

    @Override
    public void remove(NBTTagCompound compound, String s)
    {

    }

    @Override
    public String toString(ItemStack itemStack)
    {
        return null;
    }
}
