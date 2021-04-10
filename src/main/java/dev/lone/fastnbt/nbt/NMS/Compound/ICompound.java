package dev.lone.fastnbt.nbt.NMS.Compound;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public interface ICompound<T, NBTLIST, NBTCOMPOUND>
{
    <T> T newCompoundInstance();

    void setByte(T t, String key, byte param);
    void setShort(T t, String key, short param);
    void setInt(T t, String key, int param);
    void setLong(T t, String key, long param);
    void setUUID(T t, String key, UUID param);
    void setFloat(T t, String key, float param);
    void setDouble(T t, String key, double param);
    void setString(T t, String key, String param);
    void setByteArray(T t, String key, byte[] param);
    void setIntArray(T t, String key, int[] param);
    void setIntegerList(T t, String key, List<Integer> param);
    void setLongArray(T t, String key, long[] param);
    void setLongList(T t, String key, List<Long> param);
    void setBoolean(T t, String key, boolean param);

    boolean hasKey(T t, String key);
    boolean hasUUID(T t, String key);
    @Nullable UUID getUUID(T t, String key);
    byte getByte(T t, String key);
    short getShort(T t, String key);
    int getInt(T t, String key);
    long getLong(T t, String key);
    float getFloat(T t, String key);
    double getDouble(T t, String key);
    String getString(T t, String key);
    byte[] getByteArray(T t, String key);
    int[] getIntArray(T t, String key);
    long[] getLongArray(T t, String key);
    @Nullable
    NBTCOMPOUND getCompound(T t, String key);
    NBTCOMPOUND getOrAddCompound(T t, String key);
    @Nullable
    NBTLIST getList(T t, String key, int typeID);
    NBTLIST getOrAddList(T t, String key, int typeID);
    boolean getBoolean(T t, String key);
    boolean isEmpty(T t);
    void remove(T t, String key);
    void merge(NBTCOMPOUND handle, NBTCOMPOUND otherHandle);
    String toString(T t);
}
