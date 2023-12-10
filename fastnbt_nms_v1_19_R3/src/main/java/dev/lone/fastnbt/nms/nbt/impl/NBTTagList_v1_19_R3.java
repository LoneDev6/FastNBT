package dev.lone.fastnbt.nms.nbt.impl;

import dev.lone.fastnbt.nms.Implementation;
import dev.lone.fastnbt.nms.Version;
import dev.lone.fastnbt.nms.nbt.nms.INBTTagList;
import net.minecraft.nbt.*;

@Implementation.CyclicDependency(type = INBTTagList.class, version = Version.v1_19_R3)
@SuppressWarnings({"unused"})
public class NBTTagList_v1_19_R3 implements INBTTagList<ListTag, Tag, CompoundTag>
{
    @Override
    public boolean isEmpty(ListTag list)
    {
        return list.isEmpty();
    }

    @Override
    public CompoundTag getOrAddCompoundAt(ListTag list, int i)
    {
        return list.getCompound(i);
    }

    @Override
    public ListTag getNBTTagListAt(ListTag list, int i)
    {
        return list.getList(i);
    }

    @Override
    public short getShortAt(ListTag list, int i)
    {
        return list.getShort(i);
    }

    @Override
    public int getIntAt(ListTag list, int i)
    {
        return list.getInt(i);
    }

    @Override
    public int[] getIntArrayAt(ListTag list, int i)
    {
        return list.getIntArray(i);
    }

    @Override
    public double getDoubleAt(ListTag list, int i)
    {
        return list.getDouble(i);
    }

    @Override
    public float getFloatAt(ListTag list, int i)
    {
        return list.getFloat(i);
    }

    @Override
    public String getStringAt(ListTag list, int i)
    {
        return list.getString(i);
    }

    @Override
    public int size(ListTag list)
    {
        return list.size();
    }

    @Override
    public Tag get(ListTag list, int i)
    {
        return list.get(i);
    }

    @Override
    public Tag set(ListTag list, int i, Object any)
    {
        Tag nbtBase = objToTag(any);
        if (nbtBase != null)
            return list.set(i, nbtBase);

        //TODO: catch and handle errors
        return list.set(i, (Tag) any);
    }

    @Override
    public void add(ListTag list, int i, Object any)
    {
        Tag nbtBase = objToTag(any);
        if (nbtBase != null)
        {
            list.addTag(i, nbtBase);
            return;
        }

        //TODO: catch and handle errors
        list.addTag(i, (Tag) any);
    }

    private Tag objToTag(Object any)
    {
        Tag nbtBase = null;
        if (any instanceof Byte)
            nbtBase = ByteTag.valueOf((byte) any);
        else if (any instanceof Short)
            nbtBase = ShortTag.valueOf((short) any);
        else if (any instanceof Integer)
            nbtBase = IntTag.valueOf((int) any);
        else if (any instanceof Long)
            nbtBase = LongTag.valueOf((long) any);
        else if (any instanceof Float)
            nbtBase = FloatTag.valueOf((float) any);
        else if (any instanceof Double)
            nbtBase = DoubleTag.valueOf((double) any);
        else if (any instanceof String)
            nbtBase = StringTag.valueOf((String) any);
        return nbtBase;
    }

    @Override
    public Tag remove(ListTag list, int i)
    {
        return list.remove(i);
    }

    @Override
    public String toString(ListTag list)
    {
        return list.toString();
    }
}
