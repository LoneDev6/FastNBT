package dev.lone.fastnbt.nms.nbt;

import com.google.gson.JsonParser;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface ICompoundTag<Handle, ListTag, CompoundTag>
{
    <I> I newInstance();
    void setByte(Handle handle, String key, byte param);
    void setShort(Handle handle, String key, short param);
    void setInt(Handle handle, String key, int param);
    void setLong(Handle handle, String key, long param);
    void setUUID(Handle handle, String key, UUID param);
    void setFloat(Handle handle, String key, float param);
    void setDouble(Handle handle, String key, double param);
    void setString(Handle handle, String key, String param);
    void setByteArray(Handle handle, String key, byte[] param);
    void setIntArray(Handle handle, String key, int[] param);
    void setIntegerList(Handle handle, String key, List<Integer> param);
    void setLongArray(Handle handle, String key, long[] param);
    void setLongList(Handle handle, String key, List<Long> param);
    void setBoolean(Handle handle, String key, boolean param);
    boolean hasKey(Handle handle, String key);
    boolean hasUUID(Handle handle, String key);
    @Nullable UUID getUUID(Handle handle, String key);
    byte getByte(Handle handle, String key);
    short getShort(Handle handle, String key);
    int getInt(Handle handle, String key);
    long getLong(Handle handle, String key);
    float getFloat(Handle handle, String key);
    double getDouble(Handle handle, String key);
    @Nullable String getString(Handle handle, String key);
    byte @Nullable [] getByteArray(Handle handle, String key);
    int @Nullable [] getIntArray(Handle handle, String key);
    long @Nullable [] getLongArray(Handle handle, String key);
    @Nullable CompoundTag getCompound(Handle handle, String key);
    CompoundTag getOrAddCompound(Handle handle, String key);
    @Nullable ListTag getList(Handle handle, String key, int typeID);
    ListTag getOrAddList(Handle handle, String key, int typeID);
    void putTag(Handle handle, String key, Object value);
    boolean getBoolean(Handle handle, String key);
    Set<String> getKeys(Handle handle);
    boolean isEmpty(Handle handle);
    void remove(Handle handle, String key);
    void merge(CompoundTag handle, CompoundTag otherHandle);
    @Nullable String toString(Handle handle);

    default boolean isValid(String component)
    {
        try
        {
            return JsonParser.parseString(component).getAsJsonObject() != null;
        }
        catch (Throwable ignored) {}
        return false;
    }
}
