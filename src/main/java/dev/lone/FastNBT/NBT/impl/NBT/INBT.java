package dev.lone.FastNBT.NBT.impl.NBT;

import net.minecraft.server.v1_16_R3.NBTTagCompound;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public interface INBT<T,L>
{
    void setByte(T compound, String s, byte param);
    void setShort(T compound, String s, short param);
    void setInt(T compound, String s, int param);
    void setLong(T compound, String s, long param);
    void setUUID(T compound, String s, UUID param);
    void setFloat(T compound, String s, float param);
    void setDouble(T compound, String s, double param);
    void setString(T compound, String s, String param);
    void setByteArray(T compound, String s, byte[] param);
    void setIntArray(T compound, String s, int[] param);
    void setIntegerList(T compound, String s, List<Integer> param);
    void setLongArray(T compound, String s, long[] param);
    void setLongList(T compound, String s, List<Long> param);
    void setBoolean(T compound, String s, boolean param);

    boolean hasKey(T compound, String s);
    boolean hasUUID(T compound, String param);
    @Nullable UUID getUUID(T compound, String param);
    byte getByte(T compound, String s);
    short getShort(T compound, String s);
    int getInt(T compound, String s);
    long getLong(T compound, String s);
    float getFloat(T compound, String s);
    double getDouble(T compound, String s);
    String getString(T compound, String s);
    byte[] getByteArray(T compound, String s);
    int[] getIntArray(T compound, String s);
    long[] getLongArray(T compound, String s);
    T getCompound(T compound, String s);
    L getList(T compound, String s, int typeID);
    boolean getBoolean(T compound, String s);
    boolean isEmpty(T compound);
    void remove(T compound, String s);
    String toString(NBTTagCompound handle);
}
