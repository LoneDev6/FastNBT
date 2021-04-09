package dev.lone.FastNBT.NBT.impl.CraftItemStack;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public interface ICraftItemStack
{
    void setByte(ItemStack itemStack, String s, byte param);
    void setShort(ItemStack itemStack, String s, short param);
    void setInt(ItemStack itemStack, String s, int param);
    void setLong(ItemStack itemStack, String s, long param);
    void setUUID(ItemStack itemStack, String s, UUID param);
    void setFloat(ItemStack itemStack, String s, float param);
    void setDouble(ItemStack itemStack, String s, double param);
    void setString(ItemStack itemStack, String s, String param);
    void setByteArray(ItemStack itemStack, String s, byte[] param);
    void setIntArray(ItemStack itemStack, String s, int[] param);
    void setIntegerList(ItemStack itemStack, String s, List<Integer> param);
    void setLongArray(ItemStack itemStack, String s, long[] param);
    void setLongList(ItemStack itemStack, String s, List<Long> param);
    void setBoolean(ItemStack itemStack, String s, boolean param);

    boolean hasKey(ItemStack itemStack, String s);
    boolean hasUUID(ItemStack itemStack, String param);
    @Nullable UUID getUUID(ItemStack itemStack, String param);
    byte getByte(ItemStack itemStack, String s);
    short getShort(ItemStack itemStack, String s);
    int getInt(ItemStack itemStack, String s);
    long getLong(ItemStack itemStack, String s);
    float getFloat(ItemStack itemStack, String s);
    double getDouble(ItemStack itemStack, String s);
    String getString(ItemStack itemStack, String s);
    byte[] getByteArray(ItemStack itemStack, String s);
    int[] getIntArray(ItemStack itemStack, String s);
    long[] getLongArray(ItemStack itemStack, String s);
    Object getCompound(ItemStack itemStack, String s);
    Object getList(ItemStack itemStack, String s, int typeID);
    boolean getBoolean(ItemStack itemStack, String s);
    boolean isEmpty(ItemStack itemStack);
    void remove(ItemStack itemStack, String s);
    String toString(ItemStack itemStack);
}