package dev.lone.fastnbt.nms.nbt;

import dev.lone.fastnbt.nms.Implementation;
import dev.lone.fastnbt.nms.Version;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Implementation.CyclicDependency(type = ICompoundTag.class, version = Version.v1_20_R1)
@SuppressWarnings({"unchecked", "unused"})
public class Compound_v1_20_R1 implements ICompoundTag<CompoundTag, ListTag, CompoundTag>
{
    @Override
    public CompoundTag newInstance()
    {
        return new CompoundTag();
    }

    @Override
    public void setByte(CompoundTag handle, String key, byte param)
    {
        handle.putByte(key, param);
    }

    @Override
    public void setShort(CompoundTag handle, String key, short param)
    {
        handle.putShort(key, param);
    }

    @Override
    public void setInt(CompoundTag handle, String key, int param)
    {
        handle.putInt(key, param);
    }

    @Override
    public void setLong(CompoundTag handle, String key, long param)
    {
        handle.putLong(key, param);
    }

    @Override
    public void setUUID(CompoundTag handle, String key, UUID param)
    {
        handle.putUUID(key, param);
    }

    @Override
    public void setFloat(CompoundTag handle, String key, float param)
    {
        handle.putFloat(key, param);
    }

    @Override
    public void setDouble(CompoundTag handle, String key, double param)
    {
        handle.putDouble(key, param);
    }

    @Override
    public void setString(CompoundTag handle, String key, String param)
    {
        handle.putString(key, param);
    }

    @Override
    public void setByteArray(CompoundTag handle, String key, byte[] param)
    {
        handle.putByteArray(key, param);
    }

    @Override
    public void setIntArray(CompoundTag handle, String key, int[] param)
    {
        handle.putIntArray(key, param);
    }

    @Override
    public void setIntegerList(CompoundTag handle, String key, List<Integer> param)
    {
        handle.putIntArray(key, param);
    }

    @Override
    public void setLongArray(CompoundTag handle, String key, long[] param)
    {
        handle.putLongArray(key, param);
    }

    @Override
    public void setLongList(CompoundTag handle, String key, List<Long> param)
    {
        handle.putLongArray(key, param);
    }

    @Override
    public void setBoolean(CompoundTag handle, String key, boolean param)
    {
        handle.putBoolean(key, param);
    }

    @Override
    public boolean hasKey(CompoundTag handle, String key)
    {
        if (handle == null)
            return false;
        return handle.contains(key);
    }

    @Override
    public boolean hasUUID(CompoundTag handle, String key)
    {
        if (handle == null)
            return false;
        return handle.hasUUID(key);
    }

    @Override
    public UUID getUUID(CompoundTag handle, String key)
    {
        if (!handle.contains(key, NBTType.IntArray.id) && handle.contains(key + "Most", NBTType.AnyNumeric.id) && handle.contains(key + "Least", NBTType.AnyNumeric.id))
            return new UUID(handle.getLong(key + "Most"), handle.getLong(key + "Least"));

        Tag tag = handle.get(key);
        if(tag == null)
            return null;
        return NbtUtils.loadUUID(tag);
    }

    @Override
    public byte getByte(CompoundTag handle, String key)
    {
        return handle.getByte(key);
    }

    @Override
    public short getShort(CompoundTag handle, String key)
    {
        return handle.getShort(key);
    }

    @Override
    public int getInt(CompoundTag handle, String key)
    {
        return handle.getInt(key);
    }

    @Override
    public long getLong(CompoundTag handle, String key)
    {
        return handle.getLong(key);
    }

    @Override
    public float getFloat(CompoundTag handle, String key)
    {
        return handle.getFloat(key);
    }

    @Override
    public double getDouble(CompoundTag handle, String key)
    {
        return handle.getDouble(key);
    }

    @Override
    public String getString(CompoundTag handle, String key)
    {
        if(!hasKey(handle, key))
            return null;
        return handle.getString(key);
    }

    @Override
    public byte @Nullable [] getByteArray(CompoundTag handle, String key)
    {
        if(!hasKey(handle, key))
            return null;
        return handle.getByteArray(key);
    }

    @Override
    public int @Nullable [] getIntArray(CompoundTag handle, String key)
    {
        if(!hasKey(handle, key))
            return null;
        return handle.getIntArray(key);
    }

    @Override
    public long @Nullable [] getLongArray(CompoundTag handle, String key)
    {
        if(!hasKey(handle, key))
            return null;
        return handle.getLongArray(key);
    }

    @Override
    public CompoundTag getCompound(CompoundTag handle, String key)
    {
        if (!handle.contains(key))
            return null;
        return handle.getCompound(key);
    }

    @Override
    public CompoundTag getOrAddCompound(CompoundTag handle, String key)
    {
        if (!handle.contains(key))
            handle.put(key, new CompoundTag());
        return handle.getCompound(key);
    }

    @Override
    public ListTag getList(CompoundTag handle, String key, int typeID)
    {
        if (!handle.contains(key))
            return null;
        return handle.getList(key, typeID);
    }

    @Override
    public ListTag getOrAddList(CompoundTag handle, String key, int typeID)
    {
        if (!handle.contains(key))
            handle.put(key, new ListTag());
        return handle.getList(key, typeID);
    }

    @Override
    public void putTag(CompoundTag handle, String key, Object value)
    {
        handle.put(key, (Tag) value);
    }

    @Override
    public boolean getBoolean(CompoundTag handle, String key)
    {
        return handle.getBoolean(key);
    }

    @Override
    public Set<String> getKeys(CompoundTag handle)
    {
        return handle.getAllKeys();
    }

    @Override
    public boolean isEmpty(CompoundTag handle)
    {
        if (handle == null)
            return true;
        return handle.isEmpty();
    }

    @Override
    public void remove(CompoundTag handle, String key)
    {
        if (handle == null)
            return;
        handle.remove(key);
    }

    @Override
    public void merge(CompoundTag handle, CompoundTag otherHandle)
    {
        handle.merge(otherHandle);
    }

    @Override
    public String toString(CompoundTag handle)
    {
        return handle.toString();
    }
}
