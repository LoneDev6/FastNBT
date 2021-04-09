package dev.lone.fastnbt.nbt.NMS.NBTTagList;

import net.minecraft.server.v1_14_R1.*;

public class NBTTagList_v1_14_R1 implements INBTTagList<NBTTagList, NBTBase, NBTTagCompound>
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
        return list.h(i);
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
            nbtBase = new NBTTagByte((byte) any);
        else if(any instanceof Short)
            nbtBase = new NBTTagShort((short) any);
        else if(any instanceof Integer)
            nbtBase = new NBTTagInt((int) any);
        else if(any instanceof Long)
            nbtBase = new NBTTagLong((long) any);
        else if(any instanceof Float)
            nbtBase = new NBTTagFloat((float) any);
        else if(any instanceof Double)
            nbtBase = new NBTTagDouble((double) any);
        else if(any instanceof String)
            nbtBase = new NBTTagString((String) any);
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
