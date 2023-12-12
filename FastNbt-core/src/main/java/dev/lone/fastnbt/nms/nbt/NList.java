package dev.lone.fastnbt.nms.nbt;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

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

    public static NList ofItemStackList(List<ItemStack> list)
    {
        NList nList = new NList(newInstance());
        for (ItemStack e : list)
            nList.addItemStack(e);
        return nList;
    }

    public static NList ofShortList(List<Short> list)
    {
        NList nList = new NList(newInstance());
        for (Short e : list)
            nList.addShort(e);
        return nList;
    }

    public static NList ofIntList(List<Integer> list)
    {
        NList nList = new NList(newInstance());
        for (Integer e : list)
            nList.addInt(e);
        return nList;
    }

    public static NList ofIntArrayList(List<int[]> list)
    {
        NList nList = new NList(newInstance());
        for (int[] e : list)
            nList.addIntArray(e);
        return nList;
    }

    public static NList ofLongArrayList(List<long[]> list)
    {
        NList nList = new NList(newInstance());
        for (long[] e : list)
            nList.addLongArray(e);
        return nList;
    }

    public static NList ofDouble(List<Double> list)
    {
        NList nList = new NList(newInstance());
        for (Double e : list)
            nList.addDouble(e);
        return nList;
    }

    public static NList ofFloat(List<Float> list)
    {
        NList nList = new NList(newInstance());
        for (Float e : list)
            nList.addFloat(e);
        return nList;
    }

    public static NList ofString(List<String> list)
    {
        NList nList = new NList(newInstance());
        for (String e : list)
            nList.addString(e);
        return nList;
    }

    public Object getInternal()
    {
        return handle;
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

    public @Nullable ItemStack getItemStack(int i)
    {
        String string = getString(i);
        if (string.isEmpty())
            return null;
        return NItem.compoundStrToBukkitItem(string);
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

    public short getByte(int i)
    {
        return handler.getByteAt(handle, i);
    }

    public short getShort(int i)
    {
        return handler.getShortAt(handle, i);
    }

    public int getInt(int i)
    {
        return handler.getIntAt(handle, i);
    }

    public byte @Nullable [] getByteArray(int i)
    {
        return handler.getByteArrayAt(this.handle, i);
    }

    public int @Nullable [] getIntArray(int i)
    {
        return handler.getIntArrayAt(this.handle, i);
    }

    public long @Nullable [] getLongArray(int i)
    {
        return handler.getLongArrayAt(this.handle, i);
    }

    public double getDouble(int i)
    {
        return handler.getDoubleAt(handle, i);
    }

    public float getFloat(int i)
    {
        return handler.getFloatAt(handle, i);
    }

    public String getString(int i)
    {
        return handler.getStringAt(handle, i);
    }

    @ApiStatus.Internal
    private void addRaw(int i, Object any)
    {
        handler.add(handle, i, any);
    }

    @ApiStatus.Internal
    protected void addRaw(Object any)
    {
        addRaw(size(), any);
    }

    /**
     * Adds a NCompound at a specified index.
     */
    public <N extends NCompound> void addCompound(int i, N nCompound)
    {
        handler.add(handle, i, nCompound.handle);
    }

    /**
     * Adds a NCompound at the end of the list.
     */
    public <N extends NCompound> void addCompound(N nCompound)
    {
        addCompound(size(), nCompound);
    }

    /**
     * Adds a new NCompound at a specified index.
     */
    public NCompound addCompound(int i)
    {
        NCompound wrapped = new NCompound();
        addCompound(i, wrapped);
        return wrapped;
    }

    /**
     * Adds a new NCompound at the end of the list.
     */
    public NCompound addCompound()
    {
        return addCompound(size());
    }

    /**
     * Adds a ItemStack at a specified index.
     */
    public void addItemStack(int i, ItemStack bukkitItemStack)
    {
        handler.add(handle, i,  NItem.bukkitItemToNmsCompound(bukkitItemStack));
    }

    /**
     * Adds a ItemStack at the end of the list.
     */
    public void addItemStack(ItemStack bukkitItemStack)
    {
        addItemStack(size(), bukkitItemStack);
    }

    /**
     * Adds a new NList at a specified index.
     */
    public NList addList(int i)
    {
        NList wrapped = new NList();
        addRaw(i, wrapped.handle);
        return wrapped;
    }

    /**
     * Adds a new NList at the end of the list.
     */
    public NList addList()
    {
        return addList(size());
    }

    /**
     * Adds a byte at the end of the list.
     */
    public void addByte(byte value)
    {
        addRaw(value);
    }

    /**
     * Adds a short at the end of the list.
     */
    public void addShort(short value)
    {
        addRaw(value);
    }

    /**
     * Adds an int at the end of the list.
     */
    public void addInt(int value)
    {
        addRaw(value);
    }

    /**
     * Adds a long at the end of the list.
     */
    public void addLong(long value)
    {
        addRaw(value);
    }

    /**
     * Adds a float at the end of the list.
     */
    public void addFloat(float value)
    {
        addRaw(value);
    }

    /**
     * Adds a double at the end of the list.
     */
    public void addDouble(double value)
    {
        addRaw(value);
    }

    /**
     * Adds a byte[] at the end of the list.
     */
    public void addByteArray(byte[] value)
    {
        addRaw(value);
    }

    /**
     * Adds a String at the end of the list.
     */
    public void addString(String value)
    {
        addRaw(value);
    }

    /**
     * Adds a List at the end of the list.
     */
    public void addList(List<?> value)
    {
        addRaw(value);
    }

    /**
     * Adds an int[] at the end of the list.
     */
    public void addIntArray(int[] value)
    {
        addRaw(value);
    }

    /**
     * Adds a long[] at the end of the list.
     */
    public void addLongArray(long[] value)
    {
        addRaw(value);
    }

    @ApiStatus.Internal
    protected void setRaw(int i, Object any)
    {
        handler.set(handle, i, any);
    }

    /**
     * Replaces the ItemStack at a specified index.
     */
    public void setItemStack(int i, ItemStack bukkitItemStack)
    {
        handler.set(handle, i, NItem.bukkitItemToNmsCompound(bukkitItemStack));
    }

    /**
     * Replaces the ItemStack at a specified index.
     */
    public <N extends NCompound> void setCompound(int i, N nCompound)
    {
        handler.set(handle, i, nCompound.handle);
    }

    /**
     * Replaces the List at a specified index.
     */
    public void setList(int i, List<?> value)
    {
        setRaw(i, value);
    }

    /**
     * Replaces the NList at a specified index.
     */
    public void setList(int i, NList list)
    {
        addRaw(i, list.handle);
    }

    /**
     * Replaces the byte at a specified index.
     */
    public void setByte(int i, byte value)
    {
        setRaw(i, value);
    }

    /**
     * Replaces the short at a specified index.
     */
    public void setShort(int i, short value)
    {
        setRaw(i, value);
    }

    /**
     * Replaces the int at a specified index.
     */
    public void setInt(int i, int value)
    {
        setRaw(i, value);
    }

    /**
     * Replaces the long at a specified index.
     */
    public void setLong(int i, long value)
    {
        setRaw(i, value);
    }

    /**
     * Replaces the float at a specified index.
     */
    public void setFloat(int i, float value)
    {
        setRaw(i, value);
    }

    /**
     * Replaces the double at a specified index.
     */
    public void setDouble(int i, double value)
    {
        setRaw(i, value);
    }

    /**
     * Replaces the String at a specified index.
     */
    public void setString(int i, String value)
    {
        setRaw(i, value);
    }

    /**
     * Replaces the byte[] at a specified index.
     */
    public void setByteArray(int i, byte[] value)
    {
        setRaw(i, value);
    }

    /**
     * Replaces the int[] at a specified index.
     */
    public void setIntArray(int i, int[] value)
    {
        setRaw(i, value);
    }

    /**
     * Replaces the long[] at a specified index.
     */
    public void setLongArray(int i, long[] value)
    {
        setRaw(i, value);
    }

    /**
     * Removed the element at a specified index.
     */
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

    @NotNull
    public Iterator<NCompound> compoundIterator()
    {
        return iterator(this::getCompound);
    }

    @NotNull
    public Iterator<String> stringIterator()
    {
        return iterator(this::getString);
    }

    @NotNull
    public Iterator<ItemStack> itemIterator()
    {
        return iterator(this::getItemStack);
    }

    @NotNull
    public Iterator<NList> listIterator()
    {
        return iterator(this::getList);
    }

    @NotNull
    public Iterator<Short> shortIterator()
    {
        return iterator(this::getShort);
    }

    @NotNull
    public Iterator<Integer> intIterator()
    {
        return iterator(this::getInt);
    }

    @NotNull
    public Iterator<int[]> intArrayIterator()
    {
        return iterator(this::getIntArray);
    }

    private <Q, R extends Class<Q>> Iterator<Q> iterator(Function<Integer, Q> consumer)
    {
        if (size() == 0)
            return Collections.emptyIterator();

        return new Iterator<>()
        {
            int index = 0;

            @Override
            public boolean hasNext()
            {
                return index < size();
            }

            @Override
            public Q next()
            {
                return consumer.apply(index++);
            }
        };
    }
}
