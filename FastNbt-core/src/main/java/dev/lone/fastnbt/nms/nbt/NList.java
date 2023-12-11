package dev.lone.fastnbt.nms.nbt;

import dev.lone.fastnbt.nms.nbt.nms.IListTag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Version without boxing and safety checks and limits inheritance.
 */
@SuppressWarnings({"unchecked", "rawtypes", "UnusedReturnValue", "unused"})
public class NList
{
    protected IListTag handler;
    protected Object handle;

    public NList()
    {
        this(newInstance());
    }

    public NList(Object handle)
    {
        this.handle = handle;
        this.handler = NBT.list;
    }

    public static Object newInstance()
    {
        return NBT.list.newNmsInstance();
    }

    public Object getInternal()
    {
        return handle;
    }

    @Nullable
    public NCompound getCompound(int i)
    {
        Object handle = handler.getCompoundAt(this.handle, i);
        if(handle == null)
            return null;
        return new NCompound(handle);
    }

    public NCompound getOrAddCompound(int i)
    {
        Object handle = handler.getCompoundAt(this.handle, i);
        if (handle != null)
            return new NCompound(handle);

        return addCompound(i);
    }

    public NCompound addCompound(int i)
    {
        NCompound wrapped = new NCompound();
        addCompound(i, wrapped);
        return wrapped;
    }

    public NCompound addCompound()
    {
        NCompound wrapped = new NCompound();
        addCompound(size(), wrapped);
        return wrapped;
    }

    @Nullable
    public NList getList(int i)
    {
        Object handle = handler.getListAt(this.handle, i);
        if(handle == null)
            return null;
        return new NList(handle);
    }

    public NList getOrAddList(int i)
    {
        Object handle = handler.getListAt(this.handle, i);
        if (handle != null)
            return new NList(handle);

        return addList(i);
    }

    public NList addList(int i)
    {
        NList wrapped = new NList();
        addRaw(i, wrapped.handle);
        return wrapped;
    }

    public short getShort(int i)
    {
        return handler.getShortAt(handle, i);
    }

    public int getInt(int i)
    {
        return handler.getIntAt(handle, i);
    }

    public int @Nullable [] getIntArray(int i)
    {
        return handler.getIntArrayAt(this.handle, i);
    }

    public double getDouble(int i)
    {
        return handler.getDoubleAt(handle, i);
    }

    public float getFloat(int i)
    {
        return handler.getFloatAt(handle, i);
    }

    public int size()
    {
        return handler.size(handle);
    }

    @ApiStatus.Internal
    public Object getRaw(int i)
    {
        return handler.get(handle, i);
    }

    @ApiStatus.Internal
    private void addRaw(int i, Object any)
    {
        handler.add(handle, i, any);
    }

    public void addItemStack(int i, ItemStack bukkitItemStack)
    {
        handler.add(handle, i,  NItem.bukkitItemToNmsCompound(bukkitItemStack));
    }

    public <N extends NCompound> void addCompound(N nCompound)
    {
        handler.add(handle, handler.size(handle), nCompound.handle);
    }

    @ApiStatus.Internal
    protected void addRaw(Object any)
    {
        handler.add(handle, size(), any);
    }

    public void addItemStack(ItemStack bukkitItemStack)
    {
        handler.add(handle, size(), NItem.bukkitItemToNmsCompound(bukkitItemStack));
    }

    public <N extends NCompound> void addCompound(int i, N nCompound)
    {
        handler.add(handle, i, nCompound.handle);
    }

    public void addByte(byte value)
    {
        addRaw(value);
    }

    public void addShort(short value)
    {
        addRaw(value);
    }

    public void addInt(int value)
    {
        addRaw(value);
    }

    public void addLong(long value)
    {
        addRaw(value);
    }

    public void addFloat(float value)
    {
        addRaw(value);
    }

    public void addDouble(double value)
    {
        addRaw(value);
    }

    public void addByte(byte[] value)
    {
        addRaw(value);
    }

    public void addString(String value)
    {
        addRaw(value);
    }

    public void addList(List<?> value)
    {
        addRaw(value);
    }

    public void addIntArray(int[] value)
    {
        addRaw(value);
    }

    public void addLongArray(long[] value)
    {
        addRaw(value);
    }

    @ApiStatus.Internal
    protected void setRaw(int i, Object any)
    {
        handler.set(handle, i, any);
    }

    public void setItem(int i, ItemStack bukkitItemStack)
    {
        handler.set(handle, i, NItem.bukkitItemToNmsCompound(bukkitItemStack));
    }

    public <N extends NCompound> void setCompound(int i, N nCompound)
    {
        handler.set(handle, i, nCompound.handle);
    }

    public void setByte(int i, byte value)
    {
        setRaw(i, value);
    }

    public void setShort(int i, short value)
    {
        setRaw(i, value);
    }

    public void setInt(int i, int value)
    {
        setRaw(i, value);
    }

    public void setLong(int i, long value)
    {
        setRaw(i, value);
    }

    public void setFloat(int i, float value)
    {
        setRaw(i, value);
    }

    public void setDouble(int i, double value)
    {
        setRaw(i, value);
    }

    public void setByteArray(int i, byte[] value)
    {
        setRaw(i, value);
    }

    public void setString(int i, String value)
    {
        setRaw(i, value);
    }

    public void setList(int i, List<?> value)
    {
        setRaw(i, value);
    }

    public void setIntArray(int i, int[] value)
    {
        setRaw(i, value);
    }

    public void setLongArray(int i, long[] value)
    {
        setRaw(i, value);
    }

    public Object remove(int i)
    {
        return handler.remove(handle, i);
    }

    @Override
    public String toString()
    {
        return handler.toString();
    }

    public boolean isEmpty()
    {
        return handler.isEmpty(handle);
    }
}
