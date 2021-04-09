package dev.lone.fastnbt.nbt.NMS.NBTTagList;

import net.minecraft.server.v1_16_R2.*;

public class NBTTagList_v1_16_R2 implements INBTTagList<NBTTagList, NBTBase, NBTTagCompound>
{
    @Override
    public boolean isEmpty(NBTTagList list)
    {
        return list.isEmpty();
    }

    @Override
    public NBTTagCompound getCompoundAt(NBTTagList list, int i)
    {
        return list.getCompound(i);
    }

    @Override
    public NBTTagList getNBTTagListAt(NBTTagList list, int i)
    {
        return list.b(i);
    }

    @Override
    public short getShortAt(NBTTagList list, int i)
    {
        return list.d(i);
    }

    @Override
    public int getIntAt(NBTTagList list, int i)
    {
        return list.e(i);
    }

    @Override
    public int[] getIntArrayAt(NBTTagList list, int i)
    {
        return list.f(i);
    }

    @Override
    public double getDoubleAt(NBTTagList list, int i)
    {
        return list.getDoubleAt(i);
    }

    @Override
    public float getFloatAt(NBTTagList list, int i)
    {
        return list.i(i);
    }

    @Override
    public String getStringAt(NBTTagList list, int i)
    {
        return list.getString(i);
    }

    @Override
    public int size(NBTTagList list)
    {
        return list.size();
    }

    @Override
    public NBTBase get(NBTTagList list, int i)
    {
        return list.get(i);
    }

    @Override
    public NBTBase set(NBTTagList list, int i, Object any)
    {
        NBTBase nbtBase = objToNBTBase(any);
        if(nbtBase != null)
            return list.set(i, nbtBase);

        //TODO: chatching errors
        return list.set(i, (NBTBase) any);
    }

    @Override
    public void add(NBTTagList list, int i, Object any)
    {
        NBTBase nbtBase = objToNBTBase(any);
        if(nbtBase != null)
        {
            list.add(i, nbtBase);
            return;
        }

        //TODO: chatching errors
        list.add(i, (NBTBase) any);
    }

    private NBTBase objToNBTBase(Object any)
    {
        NBTBase nbtBase = null;
        if(any instanceof Byte)
            nbtBase = NBTTagByte.a((byte) any);
        else if(any instanceof Short)
            nbtBase = NBTTagShort.a((short) any);
        else if(any instanceof Integer)
            nbtBase = NBTTagInt.a((int) any);
        else if(any instanceof Long)
            nbtBase = NBTTagLong.a((long) any);
        else if(any instanceof Float)
            nbtBase = NBTTagFloat.a((float) any);
        else if(any instanceof Double)
            nbtBase = NBTTagDouble.a((double) any);
        else if(any instanceof String)
            nbtBase = NBTTagString.create((String) any);
        return nbtBase;
    }

    @Override
    public NBTBase remove(NBTTagList list, int i)
    {
        return list.remove(i);
    }

    @Override
    public String toString(NBTTagList list)
    {
        return list.toString();
    }
}
