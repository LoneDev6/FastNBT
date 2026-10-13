package beer.devs.fastnbt.nms.nbt;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unchecked", "rawtypes"})
final class EditList extends NList
{
    private final EditScope scope;
    private final EditScope.Node node;

    EditList(EditScope scope, EditScope.Node node)
    {
        super(null);
        this.scope = scope;
        this.node = node;
    }

    @Override
    public Object getInternal()
    {
        return NBT.compound.copyTag(node.read());
    }

    @Override
    public Object getRaw(int i)
    {
        return NBT.compound.copyTag(handler.get(node.read(), i));
    }

    @Override
    public int size()
    {
        return handler.size(node.read());
    }

    @Override
    public boolean isEmpty()
    {
        return handler.isEmpty(node.read());
    }

    @Override
    public String toString()
    {
        return handler.toString(node.read());
    }

    @Override
    public Object remove(int i)
    {
        return handler.remove(node.write(), i);
    }

    private Object raw(int i)
    {
        return i >= 0 && i < size() ? handler.get(node.read(), i) : null;
    }

    @Override
    public NCompound getCompound(int i)
    {
        Object tag = raw(i);
        return NBT.compound.tagId(tag) == 10 ? new EditCompound(scope, node.child(i, tag)) : null;
    }

    @Override
    public NList getList(int i)
    {
        Object tag = raw(i);
        return NBT.compound.tagId(tag) == 9 ? new EditList(scope, node.child(i, tag)) : null;
    }

    @Override
    public ItemStack getItemStack(int i)
    {
        Object tag = raw(i);
        if (NBT.compound.tagId(tag) == 10)
            return getCompound(i).toBukkitItem();
        return NBT.compound.tagId(tag) == 8 ? super.getItemStack(i) : null;
    }

    @Override
    public NCompound getOrAddCompound(int i)
    {
        NCompound value = getCompound(i);
        return value == null ? addCompound(i) : value;
    }

    @Override
    public NList getOrAddList(int i)
    {
        NList value = getList(i);
        return value == null ? addList(i) : value;
    }

    private static Object copyInput(Object value)
    {
        if (value instanceof byte[])
            return ((byte[]) value).clone();
        if (value instanceof int[])
            return ((int[]) value).clone();
        if (value instanceof long[])
            return ((long[]) value).clone();
        if (value instanceof List<?>)
        {
            List<Object> copy = new ArrayList<>();
            for (Object entry : (List<?>) value)
                copy.add(copyInput(entry));
            return copy;
        }

        if (value == null || value instanceof String || value instanceof Number || value instanceof Boolean)
            return value;
        return NBT.compound.copyTag(value);
    }

    private void insert(int i, Object value)
    {
        handler.add(node.write(), i, copyInput(value));
    }

    @Override
    protected void addRaw(Object value)
    {
        insert(size(), value);
    }

    @Override
    protected void setRaw(int i, Object value)
    {
        handler.set(node.write(), i, copyInput(value));
    }

    @Override
    public <N extends NCompound> void addCompound(int i, N value)
    {
        insert(i, NBT.compound.copyTag(value.getInternal()));
    }

    @Override
    public NCompound addCompound(int i)
    {
        insert(i, NBT.compound.newInstance());
        return getCompound(i);
    }

    @Override
    public NList addList(int i, NList value)
    {
        insert(i, NBT.compound.copyTag(value.getInternal()));
        return getList(i);
    }

    @Override
    public NList addList(NList value)
    {
        return addList(size(), value);
    }

    @Override
    public NList addList(int i)
    {
        insert(i, NBT.list.newNmsInstance());
        return getList(i);
    }

    @Override
    public void addItemStack(int i, ItemStack value)
    {
        insert(i, NItem.bukkitItemToNmsCompound(value));
    }

    @Override
    public void setItemStack(int i, ItemStack value)
    {
        setRaw(i, NItem.bukkitItemToNmsCompound(value));
    }

    @Override
    public <N extends NCompound> void setCompound(int i, N value)
    {
        setRaw(i, NBT.compound.copyTag(value.getInternal()));
    }

    @Override
    public void setList(int i, NList value)
    {
        setRaw(i, NBT.compound.copyTag(value.getInternal()));
    }

    @Override
    public void addByte(int i, byte value)
    {
        insert(i, value);
    }

    @Override
    public short getByte(int i)
    {
        return handler.getByteAt(node.read(), i);
    }

    @Override
    public void addShort(int i, short value)
    {
        insert(i, value);
    }

    @Override
    public short getShort(int i)
    {
        return handler.getShortAt(node.read(), i);
    }

    @Override
    public void addInt(int i, int value)
    {
        insert(i, value);
    }

    @Override
    public int getInt(int i)
    {
        return handler.getIntAt(node.read(), i);
    }

    @Override
    public void addLong(int i, long value)
    {
        insert(i, value);
    }

    @Override
    public void addFloat(int i, float value)
    {
        insert(i, value);
    }

    @Override
    public float getFloat(int i)
    {
        return handler.getFloatAt(node.read(), i);
    }

    @Override
    public void addDouble(int i, double value)
    {
        insert(i, value);
    }

    @Override
    public double getDouble(int i)
    {
        return handler.getDoubleAt(node.read(), i);
    }

    @Override
    public void addString(int i, String value)
    {
        insert(i, value);
    }

    @Override
    public String getString(int i)
    {
        return handler.getStringAt(node.read(), i);
    }

    @Override
    public void addByteArray(int i, byte[] value)
    {
        insert(i, value);
    }

    @Override
    public byte[] getByteArray(int i)
    {
        byte[] value = handler.getByteArrayAt(node.read(), i);
        return value == null ? null : value.clone();
    }

    @Override
    public void addIntArray(int i, int[] value)
    {
        insert(i, value);
    }

    @Override
    public int[] getIntArray(int i)
    {
        int[] value = handler.getIntArrayAt(node.read(), i);
        return value == null ? null : value.clone();
    }

    @Override
    public void addLongArray(int i, long[] value)
    {
        insert(i, value);
    }

    @Override
    public long[] getLongArray(int i)
    {
        long[] value = handler.getLongArrayAt(node.read(), i);
        return value == null ? null : value.clone();
    }

    @Override
    public void addList(int i, List<?> value)
    {
        insert(i, value);
    }
}
