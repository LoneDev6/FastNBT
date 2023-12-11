package dev.lone.fastnbt.nms.nbt;

import dev.lone.fastnbt.nms.Implementation;
import dev.lone.fastnbt.nms.Version;
import dev.lone.fastnbt.nms.nbt.nms.IListTag;
import net.minecraft.server.v1_15_R1.*;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Implementation.CyclicDependency(type = IListTag.class, version = Version.v1_15_R1)
@SuppressWarnings({"unused"})
public class NBTTagList_v1_15_R1 implements IListTag<NBTTagList, NBTBase, NBTTagCompound>
{
    @Override
    public NBTTagList newNmsInstance()
    {
        return new NBTTagList();
    }

    @Override
    public boolean isEmpty(NBTTagList list)
    {
        return list.isEmpty();
    }

    @Override
    public NBTTagCompound getCompoundAt(NBTTagList list, int i)
    {
        if (i >= 0 && i < list.size())
        {
            NBTBase tag = list.get(i);
            if (tag.getTypeId() != NBTType.Compound.id)
                throw new IllegalArgumentException("Tag at " + i + " is not a Compound: " + NBTType.byId(tag.getTypeId()));
            return (NBTTagCompound) tag;
        }

        return new NBTTagCompound();
    }

    @Override
    public NBTTagList getListAt(NBTTagList list, int i)
    {
        if (i >= 0 && i < list.size())
        {
            NBTBase tag = list.get(i);
            if (tag.getTypeId() != NBTType.List.id)
                throw new IllegalArgumentException("Tag at " + i + " is not a ListTag: " + NBTType.byId(tag.getTypeId()));
            return (NBTTagList) tag;
        }

        return null;
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
    public void set(NBTTagList list, int i, Object any)
    {
        NBTBase tag = anyToTag(any);
        if (tag != null)
        {
            list.a(i, tag);
            return;
        }

        throw new IllegalArgumentException("This object is not supported by FastNbt: " + any.getClass());
    }

    @Override
    public void add(NBTTagList list, int i, @Nullable Object any)
    {
        if(any == null)
            return;

        NBTBase tag = anyToTag(any);
        if (tag != null)
        {
            list.b(i, tag);
            return;
        }

        throw new IllegalArgumentException("This object is not supported by FastNbt: " + any.getClass());
    }

    @Nullable
    private NBTBase anyToTag(Object any)
    {
        NBTBase tag = null;
        if(any instanceof NBTBase)
        {
            return (NBTBase) any;
        }
        if (any instanceof Byte)
            tag = NBTTagByte.a((byte) any);
        else if (any instanceof Short)
            tag = NBTTagShort.a((short) any);
        else if (any instanceof Integer)
            tag = NBTTagInt.a((int) any);
        else if (any instanceof Long)
            tag = NBTTagLong.a((long) any);
        else if (any instanceof Float)
            tag = NBTTagFloat.a((float) any);
        else if (any instanceof Double)
            tag = NBTTagDouble.a((double) any);
        else if (any instanceof byte[])
            tag = new NBTTagByteArray((byte[]) any);
        else if (any instanceof String)
            tag = NBTTagString.a((String) any);
        else if (any instanceof int[])
            tag = new NBTTagIntArray((int[]) any);
        else if (any instanceof long[])
            tag = new NBTTagLongArray((long[]) any);
        else if (any instanceof List<?>)
        {
            List<?> anySubList = (List<?>) any;
            tag = new NBTTagList();
            NBTTagList listTag = (NBTTagList) tag;
            for (Object anyListEntry : anySubList)
            {
                NBTBase entryTag = anyToTag(anyListEntry);
                if(entryTag == null) // Skip null elements.
                    continue;
                listTag.add(listTag.size(), entryTag);
            }
        }
        return tag;
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
