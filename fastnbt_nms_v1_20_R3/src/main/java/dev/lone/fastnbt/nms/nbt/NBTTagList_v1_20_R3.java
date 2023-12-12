package dev.lone.fastnbt.nms.nbt;

import dev.lone.fastnbt.nms.Implementation;
import dev.lone.fastnbt.nms.Version;
import net.minecraft.nbt.*;
import org.jetbrains.annotations.Nullable;

import java.text.MessageFormat;
import java.util.List;

@Implementation.CyclicDependency(type = IListTag.class, version = Version.v1_20_R3)
@SuppressWarnings({"unused"})
public class NBTTagList_v1_20_R3 implements IListTag<ListTag, Tag, CompoundTag>
{
    @Override
    public ListTag newNmsInstance()
    {
        return new ListTag();
    }

    @Override
    public boolean isEmpty(ListTag list)
    {
        return list.isEmpty();
    }

    @Override
    public CompoundTag getCompoundAt(ListTag list, int i)
    {
        if (i >= 0 && i < list.size())
        {
            Tag tag = list.get(i);
            if (tag.getId() != NBTType.Compound.id)
                throwIllegalArgumentException(i, tag, "Compound");
            return (CompoundTag) tag;
        }
        return null;
    }

    @Override
    public ListTag getListAt(ListTag list, int i)
    {
        if (i >= 0 && i < list.size())
        {
            Tag tag = list.get(i);
            if (tag.getId() != NBTType.List.id)
                throwIllegalArgumentException(i, tag, "ListTag");
            return (ListTag) tag;
        }
        return null;
    }

    @Override
    public byte getByteAt(ListTag list, int i)
    {
        if (i >= 0 && i < list.size())
        {
            Tag tag = list.get(i);
            if (tag.getId() != NBTType.Byte.id)
                throwIllegalArgumentException(i, tag, "ByteTag");
            return ((ByteTag) tag).getAsByte();
        }

        return 0;
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
    public byte @Nullable [] getByteArrayAt(ListTag list, int i)
    {
        if (i >= 0 && i < list.size())
        {
            Tag tag = list.get(i);
            if (tag.getId() != NBTType.ByteArray.id)
                throwIllegalArgumentException(i, tag, "ByteArrayTag");
            return ((ByteArrayTag) tag).getAsByteArray();
        }
        return null;
    }

    @Override
    public int @Nullable [] getIntArrayAt(ListTag list, int i)
    {
        if (i >= 0 && i < list.size())
        {
            Tag tag = list.get(i);
            if (tag.getId() != NBTType.IntArray.id)
                throwIllegalArgumentException(i, tag, "IntArrayTag");
            return ((IntArrayTag) tag).getAsIntArray();
        }
        return null;
    }
    
    @Override
    public long @Nullable [] getLongArrayAt(ListTag list, int i)
    {
        if (i >= 0 && i < list.size())
        {
            Tag tag = list.get(i);
            if (tag.getId() != NBTType.LongArray.id)
                throwIllegalArgumentException(i, tag, "LongArrayTag");
            return ((LongArrayTag) tag).getAsLongArray();
        }
        return null;
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
    public void set(ListTag list, int i, Object any)
    {
        Tag tag = anyToTag(any);
        if (tag != null)
        {
            list.setTag(i, tag);
            return;
        }

        throw new IllegalArgumentException("This object is not supported by FastNbt: " + any.getClass());
    }

    @Override
    public void add(ListTag list, int i, @Nullable Object any)
    {
        if(any == null)
            return;

        Tag tag = anyToTag(any);
        if (tag != null)
        {
            list.addTag(i, tag);
            return;
        }

        throw new IllegalArgumentException("This object is not supported by FastNbt: " + any.getClass());
    }

    @Nullable
    private Tag anyToTag(Object any)
    {
        Tag tag = null;
        if(any instanceof Tag tt)
            return tt;
        if (any instanceof Byte)
            tag = ByteTag.valueOf((byte) any);
        else if (any instanceof Short)
            tag = ShortTag.valueOf((short) any);
        else if (any instanceof Integer)
            tag = IntTag.valueOf((int) any);
        else if (any instanceof Long)
            tag = LongTag.valueOf((long) any);
        else if (any instanceof Float)
            tag = FloatTag.valueOf((float) any);
        else if (any instanceof Double)
            tag = DoubleTag.valueOf((double) any);
        else if (any instanceof byte[])
            tag = new ByteArrayTag((byte[]) any);
        else if (any instanceof String)
            tag = StringTag.valueOf((String) any);
        else if (any instanceof int[])
            tag = new IntArrayTag((int[]) any);
        else if (any instanceof long[])
            tag = new LongArrayTag((long[]) any);
        else if (any instanceof List<?> anySubList)
        {
            tag = new ListTag();
            ListTag listTag = (ListTag) tag;
            for (Object anyListEntry : anySubList)
            {
                Tag entryTag = anyToTag(anyListEntry);
                if(entryTag == null) // Skip null elements.
                    continue;
                listTag.add(listTag.size(), entryTag);
            }
        }
        return tag;
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

    private static void throwIllegalArgumentException(int i, Tag tag, String typeName)
    {
        throw new IllegalArgumentException(MessageFormat.format("Tag at {0} is not a {1}: {2}", i, typeName, NBTType.byId(tag.getId())));
    }
}
