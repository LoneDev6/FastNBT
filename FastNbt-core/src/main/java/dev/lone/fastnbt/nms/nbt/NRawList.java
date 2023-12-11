package dev.lone.fastnbt.nms.nbt;

import dev.lone.fastnbt.nms.nbt.nms.IListTag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Version without boxing and safety checks.
 * It's probably faster than the other classes due to the fact that it limits inheritance.
 */
@SuppressWarnings({"unchecked", "rawtypes", "UnusedReturnValue", "unused"})
public class NRawList
{
    protected IListTag handler;
    protected Object handle;

    public NRawList()
    {
        this((Object) newInstance());
    }

    public NRawList(Object handle)
    {
        this.handle = handle;
        this.handler = Nbt.list;
    }

    public static Object newInstance()
    {
        return Nbt.list.newNmsInstance();
    }

    public Object getInternal()
    {
        return handle;
    }

    @Nullable
    public NCompound getCompoundAt(int i)
    {
        Object handle = handler.getCompoundAt(this.handle, i);
        if(handle == null)
            return null;
        return new NCompound(handle);
    }

    public NCompound getOrAddCompoundAt(int i)
    {
        Object handle = handler.getCompoundAt(this.handle, i);
        if (handle != null)
            return new NCompound(handle);

        return addCompoundAt(i);
    }

    public NCompound addCompoundAt(int i)
    {
        NCompound wrapped = new NCompound();
        add(i, wrapped);
        return wrapped;
    }

    public NCompound addCompound()
    {
        NCompound wrapped = new NCompound();
        add(size(), wrapped);
        return wrapped;
    }

    @Nullable
    public NRawList getListAt(int i)
    {
        Object handle = handler.getListAt(this.handle, i);
        if(handle == null)
            return null;
        return new NRawList(handle);
    }

    public NRawList getOrAddListAt(int i)
    {
        Object handle = handler.getListAt(this.handle, i);
        if (handle != null)
            return new NRawList(handle);

        return addListAt(i);
    }

    public NRawList addListAt(int i)
    {
        NRawList wrapped = new NRawList();
        add(i, wrapped);
        return wrapped;
    }

    public short getShortAt(int i)
    {
        return handler.getShortAt(handle, i);
    }

    public int getIntAt(int i)
    {
        return handler.getIntAt(handle, i);
    }

    public int @Nullable [] getIntArrayAt(int i)
    {
        return handler.getIntArrayAt(this.handle, i);
    }

    public double getDoubleAt(int i)
    {
        return handler.getDoubleAt(handle, i);
    }

    public float getFloatAt(int i)
    {
        return handler.getFloatAt(handle, i);
    }

    public int size()
    {
        return handler.size(handle);
    }

    public Object getRaw(int i)
    {
        return handler.get(handle, i);
    }

    @ApiStatus.Internal
    private void add(int i, Object any)
    {
        handler.add(handle, i, any);
    }

    public void add(int i, ItemStack bukkitItemStack)
    {
        handler.add(handle, i,  NItem.bukkitItemToNmsCompound(bukkitItemStack));
    }

    public <N extends NCompound> void add(N nCompound)
    {
        handler.add(handle, handler.size(handle), nCompound.handle);
    }

    @ApiStatus.Internal
    protected void addRaw(Object any)
    {
        handler.add(handle, size(), any);
    }

    public void add(ItemStack bukkitItemStack)
    {
        handler.add(handle, size(), NItem.bukkitItemToNmsCompound(bukkitItemStack));
    }

    public <N extends NCompound> void add(int i, N nCompound)
    {
        handler.add(handle, i, nCompound.handle);
    }

    public void add(byte value)
    {
        addRaw(value);
    }

    public void add(short value)
    {
        addRaw(value);
    }

    public void add(int value)
    {
        addRaw(value);
    }

    public void add(long value)
    {
        addRaw(value);
    }

    public void add(float value)
    {
        addRaw(value);
    }

    public void add(double value)
    {
        addRaw(value);
    }

    public void add(byte[] value)
    {
        addRaw(value);
    }

    public void add(String value)
    {
        addRaw(value);
    }

    public void add(List<?> value)
    {
        addRaw(value);
    }

    public void add(int[] value)
    {
        addRaw(value);
    }

    public void add(long[] value)
    {
        addRaw(value);
    }

    @ApiStatus.Internal
    protected void setRaw(int i, Object any)
    {
        handler.set(handle, i, any);
    }

    public void set(int i, ItemStack bukkitItemStack)
    {
        handler.set(handle, i, NItem.bukkitItemToNmsCompound(bukkitItemStack));
    }

    public <N extends NCompound> void set(int i, N nCompound)
    {
        handler.set(handle, i, nCompound.handle);
    }

    public void set(int i, byte value)
    {
        setRaw(i, value);
    }

    public void set(int i, short value)
    {
        setRaw(i, value);
    }

    public void set(int i, int value)
    {
        setRaw(i, value);
    }

    public void set(int i, long value)
    {
        setRaw(i, value);
    }

    public void set(int i, float value)
    {
        setRaw(i, value);
    }

    public void set(int i, double value)
    {
        setRaw(i, value);
    }

    public void set(int i, byte[] value)
    {
        setRaw(i, value);
    }

    public void set(int i, String value)
    {
        setRaw(i, value);
    }

    public void set(int i, List<?> value)
    {
        setRaw(i, value);
    }

    public void set(int i, int[] value)
    {
        setRaw(i, value);
    }

    public void set(int i, long[] value)
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
