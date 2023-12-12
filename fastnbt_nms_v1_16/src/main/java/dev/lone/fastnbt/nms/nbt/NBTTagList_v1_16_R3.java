package dev.lone.fastnbt.nms.nbt;

import dev.lone.fastnbt.nms.Implementation;
import dev.lone.fastnbt.nms.Version;
import net.minecraft.server.v1_16_R3.*;
import org.jetbrains.annotations.Nullable;

import java.text.MessageFormat;
import java.util.List;

@Implementation.CyclicDependency(type = IListTag.class, version = Version.v1_16_R3)
@SuppressWarnings({"unused"})
public class NBTTagList_v1_16_R3 implements IListTag<NBTTagList, NBTBase, NBTTagCompound>
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
                throwIllegalArgumentException(i, tag, "Compound");
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
                throwIllegalArgumentException(i, tag, "ListTag");
            return (NBTTagList) tag;
        }
        return null;
    }

    @Override
    public byte getByteAt(NBTTagList list, int i)
    {
        if (i >= 0 && i < list.size())
        {
            NBTBase tag = list.get(i);
            if (tag.getTypeId() != NBTType.Byte.id)
                throwIllegalArgumentException(i, tag, "ByteTag");
            return ((NBTTagByte) tag).asByte();
        }
        return 0;
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
    public byte @Nullable [] getByteArrayAt(NBTTagList list, int i)
    {
        if (i >= 0 && i < list.size())
        {
            NBTBase tag = list.get(i);
            if (tag.getTypeId() != NBTType.ByteArray.id)
                throwIllegalArgumentException(i, tag, "ByteArrayTag");
            return ((NBTTagByteArray) tag).getBytes();
        }
        return null;
    }

    @Override
    public int @Nullable [] getIntArrayAt(NBTTagList list, int i)
    {
        if (i >= 0 && i < list.size())
        {
            NBTBase tag = list.get(i);
            if (tag.getTypeId() != NBTType.IntArray.id)
                throwIllegalArgumentException(i, tag, "IntArrayTag");
            return ((NBTTagIntArray) tag).getInts();
        }
        return null;
    }

    @Override
    public long @Nullable [] getLongArrayAt(NBTTagList list, int i)
    {
        if (i >= 0 && i < list.size())
        {
            NBTBase tag = list.get(i);
            if (tag.getTypeId() != NBTType.LongArray.id)
                throwIllegalArgumentException(i, tag, "LongArrayTag");
            return ((NBTTagLongArray) tag).getLongs();
        }
        return null;
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

    private static void throwIllegalArgumentException(int i, NBTBase tag, String typeName)
    {
        throw new IllegalArgumentException(MessageFormat.format("Tag at {0} is not a {1}: {2}", i, typeName, NBTType.byId(tag.getTypeId())));
    }
}
