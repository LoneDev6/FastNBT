package dev.lone.fastnbt.nbt;

import dev.lone.fastnbt.nbt.NMS.NBTTagList.INBTTagList;
import org.bukkit.inventory.ItemStack;

public class NTagList<T>
{
    protected INBTTagList handler;
    protected T handle;

    public NTagList() {}//TODO: init a new handle using NMS

    public NTagList(T handle)
    {
        this.handle = handle;
        this.handler = NBT.nbtTagList;
    }

    public Object getInternal()
    {
        return handle;
    }

    public NCompound getCompoundAt(int i)
    {
        return new NCompound(handler.getCompoundAt(handle, i));
    }

    public NTagList getNBTTagListAt(int i)
    {
        return new NTagList(handler.getNBTTagListAt(handle, i));
    }

    public short getShortAt(int i)
    {
        return handler.getShortAt(handle, i);
    }

    public int getIntAt(int i)
    {
        return handler.getIntAt(handle, i);
    }

    public int[] getIntArrayAt(int i)
    {
        return handler.getIntArrayAt(handle, i);
    }

    public double getDoubleAt(int i)
    {
        return handler.getDoubleAt(handle, i);
    }

    public float getFloatAt(int i)
    {
        return handler.getFloatAt(handle, i);
    }

    public String getStringAt(int i)
    {
        return handler.getStringAt(handle, i);
    }

    public int size()
    {
        return handler.size(handle);
    }

    public Object get(int i)
    {
        return handler.get(handle, i);
    }

    public <N extends NCompound> void setCompound(int i, N any)
    {
        handler.set(handle, i, any.handle);
    }

    public <N extends NCompound> void addCompound(int i, N any)
    {
        handler.add(handle, i, any.handle);
    }

    public <N extends NCompound> void add(N any)
    {
        handler.add(handle, handler.size(handle), any.handle);
    }

    public void add(int i, Object any)
    {
        if(any instanceof NCompound)
            any = ((NCompound<?>) any).getInternal();
        else if(any instanceof ItemStack)
            any = NItem.asNMSCompound((ItemStack) any);
        handler.add(handle, i, any);
    }

    public void add(Object any)
    {
        if(any instanceof NCompound)
            any = ((NCompound<?>) any).getInternal();
        else if(any instanceof ItemStack)
            any = NItem.asNMSCompound((ItemStack) any);
        handler.add(handle, size(), any);
    }

    public void set(int i, Object any)
    {
        if(any instanceof NCompound)
            any = ((NCompound<?>) any).getInternal();
        else if(any instanceof ItemStack)
            any = NItem.asNMSCompound((ItemStack) any);
        handler.set(handle, i, any);
    }

    public Object remove(int i)
    {
        return handler.remove(handle, i);
    }

    public String toString()
    {
        return handler.toString();
    }

    public boolean isEmpty()
    {
        return handler.isEmpty(handle);
    }
}
