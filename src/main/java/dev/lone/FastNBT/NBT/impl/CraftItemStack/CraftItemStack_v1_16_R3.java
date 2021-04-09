package dev.lone.FastNBT.NBT.impl.CraftItemStack;

import dev.lone.FastNBT.NBT.impl.NBT.NBT_v1_16_R3;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class CraftItemStack_v1_16_R3 extends NBT_v1_16_R3 implements ICraftItemStack
{
    private CraftItemStack handle;

    @Override
    public void setByte(ItemStack itemStack, String s, byte param)
    {
        CraftItemStack craftItemStack = ((CraftItemStack) itemStack);
        setByte(craftItemStack.getHandle().getOrCreateTag(), s, param);
    }

    @Override
    public void setShort(ItemStack itemStack, String s, short param)
    {
        CraftItemStack craftItemStack = ((CraftItemStack) itemStack);
        setShort(craftItemStack.getHandle().getOrCreateTag(), s, param);
    }

    @Override
    public void setInt(ItemStack itemStack, String s, int param)
    {
        CraftItemStack craftItemStack = ((CraftItemStack) itemStack);
        setInt(craftItemStack.getHandle().getOrCreateTag(), s, param);
    }

    @Override
    public void setLong(ItemStack itemStack, String s, long param)
    {
        CraftItemStack craftItemStack = ((CraftItemStack) itemStack);
        setLong(craftItemStack.getHandle().getOrCreateTag(), s, param);
    }

    @Override
    public void setUUID(ItemStack itemStack, String s, UUID param)
    {
        CraftItemStack craftItemStack = ((CraftItemStack) itemStack);
        setUUID(craftItemStack.getHandle().getOrCreateTag(), s, param);
    }

    @Override
    public void setFloat(ItemStack itemStack, String s, float param)
    {
        CraftItemStack craftItemStack = ((CraftItemStack) itemStack);
        setFloat(craftItemStack.getHandle().getOrCreateTag(), s, param);
    }

    @Override
    public void setDouble(ItemStack itemStack, String s, double param)
    {
        CraftItemStack craftItemStack = ((CraftItemStack) itemStack);
        setDouble(craftItemStack.getHandle().getOrCreateTag(), s, param);
    }

    @Override
    public void setString(ItemStack itemStack, String s, String param)
    {
        CraftItemStack craftItemStack = ((CraftItemStack) itemStack);
        setString(craftItemStack.getHandle().getOrCreateTag(), s, param);
    }

    @Override
    public void setByteArray(ItemStack itemStack, String s, byte[] param)
    {
        CraftItemStack craftItemStack = ((CraftItemStack) itemStack);
        setByteArray(craftItemStack.getHandle().getOrCreateTag(), s, param);
    }

    @Override
    public void setIntArray(ItemStack itemStack, String s, int[] param)
    {
        CraftItemStack craftItemStack = ((CraftItemStack) itemStack);
        setIntArray(craftItemStack.getHandle().getOrCreateTag(), s, param);
    }

    @Override
    public void setIntegerList(ItemStack itemStack, String s, List<Integer> param)
    {
        CraftItemStack craftItemStack = ((CraftItemStack) itemStack);
        setIntegerList(craftItemStack.getHandle().getOrCreateTag(), s, param);
    }

    @Override
    public void setLongArray(ItemStack itemStack, String s, long[] param)
    {
        CraftItemStack craftItemStack = ((CraftItemStack) itemStack);
        setLongArray(craftItemStack.getHandle().getOrCreateTag(), s, param);
    }

    @Override
    public void setLongList(ItemStack itemStack, String s, List<Long> param)
    {
        CraftItemStack craftItemStack = ((CraftItemStack) itemStack);
        setLongList(craftItemStack.getHandle().getOrCreateTag(), s, param);
    }

    @Override
    public void setBoolean(ItemStack itemStack, String s, boolean param)
    {
        CraftItemStack craftItemStack = ((CraftItemStack) itemStack);
        setBoolean(craftItemStack.getHandle().getOrCreateTag(), s, param);
    }

    @Override
    public boolean hasKey(ItemStack itemStack, String s)
    {
        CraftItemStack craftItemStack = ((CraftItemStack) itemStack);
        if (!craftItemStack.getHandle().hasTag())
            return false;
        return hasKey(craftItemStack.getHandle().getTag(), s);
    }

    @Override
    public boolean hasUUID(ItemStack itemStack, String param)
    {
        CraftItemStack craftItemStack = ((CraftItemStack) itemStack);
        if (!craftItemStack.getHandle().hasTag())
            return false;
        return hasUUID(craftItemStack.getHandle().getTag(), param);
    }

    @Override
    public @Nullable UUID getUUID(ItemStack itemStack, String param)
    {
        CraftItemStack craftItemStack = ((CraftItemStack) itemStack);
        return getUUID(craftItemStack.getHandle().getOrCreateTag(), param);
    }

    @Override
    public byte getByte(ItemStack itemStack, String s)
    {
        CraftItemStack craftItemStack = ((CraftItemStack) itemStack);
        return getByte(craftItemStack.getHandle().getOrCreateTag(), s);
    }

    @Override
    public short getShort(ItemStack itemStack, String s)
    {
        CraftItemStack craftItemStack = ((CraftItemStack) itemStack);
        return getShort(craftItemStack.getHandle().getOrCreateTag(), s);
    }

    @Override
    public int getInt(ItemStack itemStack, String s)
    {
        CraftItemStack craftItemStack = ((CraftItemStack) itemStack);
        return getInt(craftItemStack.getHandle().getOrCreateTag(), s);
    }

    @Override
    public long getLong(ItemStack itemStack, String s)
    {
        CraftItemStack craftItemStack = ((CraftItemStack) itemStack);
        return getLong(craftItemStack.getHandle().getOrCreateTag(), s);
    }

    @Override
    public float getFloat(ItemStack itemStack, String s)
    {
        CraftItemStack craftItemStack = ((CraftItemStack) itemStack);
        return getFloat(craftItemStack.getHandle().getOrCreateTag(), s);
    }

    @Override
    public double getDouble(ItemStack itemStack, String s)
    {
        CraftItemStack craftItemStack = ((CraftItemStack) itemStack);
        return getDouble(craftItemStack.getHandle().getOrCreateTag(), s);
    }

    @Override
    public String getString(ItemStack itemStack, String s)
    {
        CraftItemStack craftItemStack = ((CraftItemStack) itemStack);
        return getString(craftItemStack.getHandle().getOrCreateTag(), s);
    }

    @Override
    public byte[] getByteArray(ItemStack itemStack, String s)
    {
        CraftItemStack craftItemStack = ((CraftItemStack) itemStack);
        return getByteArray(craftItemStack.getHandle().getOrCreateTag(), s);
    }

    @Override
    public int[] getIntArray(ItemStack itemStack, String s)
    {
        CraftItemStack craftItemStack = ((CraftItemStack) itemStack);
        return getIntArray(craftItemStack.getHandle().getOrCreateTag(), s);
    }

    @Override
    public long[] getLongArray(ItemStack itemStack, String s)
    {
        CraftItemStack craftItemStack = ((CraftItemStack) itemStack);
        return getLongArray(craftItemStack.getHandle().getOrCreateTag(), s);
    }

    @Override
    public Object getCompound(ItemStack itemStack, String s)
    {
        CraftItemStack craftItemStack = ((CraftItemStack) itemStack);
        return craftItemStack.getHandle().getOrCreateTag().getCompound(s);
    }

    @Override
    public Object getList(ItemStack itemStack, String s, int typeID)
    {
        return null;//TODO
    }

    @Override
    public boolean getBoolean(ItemStack itemStack, String s)
    {
        CraftItemStack craftItemStack = ((CraftItemStack) itemStack);
        return getBoolean(craftItemStack.getHandle().getOrCreateTag(), s);
    }

    @Override
    public boolean isEmpty(ItemStack itemStack)
    {
        CraftItemStack craftItemStack = ((CraftItemStack) itemStack);
        if (!craftItemStack.getHandle().hasTag())
            return true;
        return isEmpty(craftItemStack.getHandle().getTag());
    }

    @Override
    public void remove(ItemStack itemStack, String s)
    {
        CraftItemStack craftItemStack = ((CraftItemStack) itemStack);
        if (!craftItemStack.getHandle().hasTag())
            return;
        remove(craftItemStack.getHandle().getTag(), s);
    }

    @Override
    public String toString(ItemStack itemStack)
    {
        CraftItemStack craftItemStack = ((CraftItemStack) itemStack);
        if (!craftItemStack.getHandle().hasTag())
            return null;
        return toString(craftItemStack.getHandle().getTag());
    }
}
