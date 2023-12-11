package dev.lone.fastnbt.nms.nbt;

import dev.lone.fastnbt.nms.nbt.nms.IListTag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings({"UnusedReturnValue", "unused", "unchecked", "rawtypes"})
public abstract class NSafeTagList<E>
{
    protected IListTag handler;
    protected Object handle;

    public NSafeTagList()
    {
        this(newInstance());
    }

    NSafeTagList(Object handle)
    {
        this.handle = handle;
        this.handler = Nbt.list;
    }

    static Object newInstance()
    {
        return Nbt.list.newNmsInstance();
    }

    public Object getInternal()
    {
        return handle;
    }

    @Nullable
    NCompound getCompoundAt(int i)
    {
        Object handle = handler.getCompoundAt(this.handle, i);
        if(handle == null)
            return null;
        return new NCompound(handle);
    }

    NCompound getOrAddCompoundAt(int i)
    {
        Object handle = handler.getCompoundAt(this.handle, i);
        if (handle != null)
            return new NCompound(handle);

        return addCompoundAt(i);
    }

    NCompound addCompoundAt(int i)
    {
        NCompound wrapped = new NCompound();
        add(i, wrapped);
        return wrapped;
    }

    NCompound addCompound()
    {
        NCompound wrapped = new NCompound();
        add(size(), wrapped);
        return wrapped;
    }

    @Nullable
    NRawList getListAt(int i)
    {
        Object handle = handler.getListAt(this.handle, i);
        if(handle == null)
            return null;
        return new NRawList(handle);
    }

    NRawList getOrAddListAt(int i)
    {
        Object handle = handler.getListAt(this.handle, i);
        if (handle != null)
            return new NRawList(handle);

        return addListAt(i);
    }

    NRawList addListAt(int i)
    {
        NRawList wrapped = new NRawList();
        add(i, wrapped);
        return wrapped;
    }

    short getShortAt(int i)
    {
        return handler.getShortAt(handle, i);
    }

    int getIntAt(int i)
    {
        return handler.getIntAt(handle, i);
    }

    int @Nullable [] getIntArrayAt(int i)
    {
        return handler.getIntArrayAt(this.handle, i);
    }

    double getDoubleAt(int i)
    {
        return handler.getDoubleAt(handle, i);
    }

    float getFloatAt(int i)
    {
        return handler.getFloatAt(handle, i);
    }

    public int size()
    {
        return handler.size(handle);
    }

    Object getRaw(int i)
    {
        return handler.get(handle, i);
    }

    @ApiStatus.Internal
    void add(int i, Object any)
    {
        handler.add(handle, i, any);
    }

    void add(int i, ItemStack bukkitItemStack)
    {
        handler.add(handle, i,  NItem.bukkitItemToNmsCompound(bukkitItemStack));
    }

    <N extends NCompound> void add(N nCompound)
    {
        handler.add(handle, handler.size(handle), nCompound.handle);
    }

    @ApiStatus.Internal
    void addRaw(Object any)
    {
        handler.add(handle, size(), any);
    }

    void add(ItemStack bukkitItemStack)
    {
        handler.add(handle, size(), NItem.bukkitItemToNmsCompound(bukkitItemStack));
    }

    <N extends NCompound> void add(int i, N nCompound)
    {
        handler.add(handle, i, nCompound.handle);
    }

    @ApiStatus.Internal
    void setRaw(int i, Object any)
    {
        handler.set(handle, i, any);
    }

    void set(int i, ItemStack bukkitItemStack)
    {
        handler.set(handle, i, NItem.bukkitItemToNmsCompound(bukkitItemStack));
    }

    <N extends NCompound> void set(int i, N nCompound)
    {
        handler.set(handle, i, nCompound.handle);
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

    abstract public @Nullable E get(int i);

    public void add(E value)
    {
        addRaw(value);
    }

    public void set(int index, E value)
    {
        setRaw(index, value);
    }
}
