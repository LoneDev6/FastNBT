package beer.devs.fastnbt.nms.nbt;

import java.util.*;

@SuppressWarnings({"unchecked", "rawtypes"})
class EditCompound extends NCompound
{
    final EditScope scope;
    final EditScope.Node node;

    EditCompound(EditScope scope, EditScope.Node node)
    {
        super((Object) null);
        this.scope = scope;
        this.node = node;
    }

    @Override
    public Object getInternal()
    {
        return handler.copyTag(node.read());
    }

    @Override
    public NCompound setByte(String key, byte value)
    {
        handler.setByte(node.write(), key, value);
        return this;
    }

    @Override
    public byte getByte(String key)
    {
        return handler.getByte(node.read(), key);
    }

    @Override
    public NCompound setShort(String key, short value)
    {
        handler.setShort(node.write(), key, value);
        return this;
    }

    @Override
    public short getShort(String key)
    {
        return handler.getShort(node.read(), key);
    }

    @Override
    public NCompound setInt(String key, int value)
    {
        handler.setInt(node.write(), key, value);
        return this;
    }

    @Override
    public int getInt(String key)
    {
        return handler.getInt(node.read(), key);
    }

    @Override
    public NCompound setLong(String key, long value)
    {
        handler.setLong(node.write(), key, value);
        return this;
    }

    @Override
    public long getLong(String key)
    {
        return handler.getLong(node.read(), key);
    }

    @Override
    public NCompound setUUID(String key, UUID value)
    {
        handler.setUUID(node.write(), key, value);
        return this;
    }

    @Override
    public UUID getUUID(String key)
    {
        return handler.getUUID(node.read(), key);
    }

    @Override
    public NCompound setFloat(String key, float value)
    {
        handler.setFloat(node.write(), key, value);
        return this;
    }

    @Override
    public float getFloat(String key)
    {
        return handler.getFloat(node.read(), key);
    }

    @Override
    public NCompound setDouble(String key, double value)
    {
        handler.setDouble(node.write(), key, value);
        return this;
    }

    @Override
    public double getDouble(String key)
    {
        return handler.getDouble(node.read(), key);
    }

    @Override
    public NCompound setString(String key, String value)
    {
        handler.setString(node.write(), key, value);
        return this;
    }

    @Override
    public String getString(String key)
    {
        return handler.getString(node.read(), key);
    }

    @Override
    public NCompound setByteArray(String key, byte[] value)
    {
        handler.setByteArray(node.write(), key, value.clone());
        return this;
    }

    @Override
    public byte[] getByteArray(String key)
    {
        byte[] value = handler.getByteArray(node.read(), key);
        return value == null ? null : value.clone();
    }

    @Override
    public NCompound setIntArray(String key, int[] value)
    {
        handler.setIntArray(node.write(), key, value.clone());
        return this;
    }

    @Override
    public int[] getIntArray(String key)
    {
        int[] value = handler.getIntArray(node.read(), key);
        return value == null ? null : value.clone();
    }

    @Override
    public NCompound setLongArray(String key, long[] value)
    {
        handler.setLongArray(node.write(), key, value.clone());
        return this;
    }

    @Override
    public long[] getLongArray(String key)
    {
        long[] value = handler.getLongArray(node.read(), key);
        return value == null ? null : value.clone();
    }

    @Override
    public NCompound setBoolean(String key, boolean value)
    {
        handler.setBoolean(node.write(), key, value);
        return this;
    }

    @Override
    public boolean getBoolean(String key)
    {
        return handler.getBoolean(node.read(), key);
    }

    @Override
    public NCompound setIntegerList(String key, List<Integer> value)
    {
        handler.setIntegerList(node.write(), key, new ArrayList<>(value));
        return this;
    }

    @Override
    public NCompound setLongList(String key, List<Long> value)
    {
        handler.setLongList(node.write(), key, new ArrayList<>(value));
        return this;
    }

    @Override
    public boolean hasKey(String key)
    {
        return handler.hasKey(node.read(), key);
    }

    @Override
    public boolean hasUUID(String key)
    {
        return handler.hasUUID(node.read(), key);
    }

    @Override
    public boolean isEmpty()
    {
        return handler.isEmpty(node.read());
    }

    @Override
    public Set<String> getKeys()
    {
        return Collections.unmodifiableSet(new HashSet<>(handler.getKeys(node.read())));
    }

    @Override
    public String toString()
    {
        return handler.toString(node.read());
    }

    @Override
    public NCompound remove(String key)
    {
        if (hasKey(key))
            handler.remove(node.write(), key);
        return this;
    }

    @Override
    public NCompound merge(NCompound other)
    {
        Object copy = other.getInternal();
        handler.merge(node.write(), handler.copyTag(copy));
        return this;
    }

    @Override
    public void putInternalTag(String key, Object tag)
    {
        Object copy = handler.copyTag(tag);
        handler.putTag(node.write(), key, copy);
    }

    @Override
    public NCompound getCompound(String key)
    {
        Object tag = handler.rawTag(node.read(), key);
        return handler.tagId(tag) == 10 ? new EditCompound(scope, node.child(key, tag)) : null;
    }

    @Override
    public NCompound getOrAddCompound(String key)
    {
        NCompound existing = getCompound(key);
        if (existing != null)
            return existing;
        handler.putTag(node.write(), key, handler.newInstance());
        return getCompound(key);
    }

    @Override
    public NCompound setCompound(String key, NCompound compound)
    {
        putInternalTag(key, compound.getInternal());
        return getCompound(key);
    }

    @Override
    public NList getList(String key, NBTType type)
    {
        Object tag = handler.rawTag(node.read(), key);
        if (handler.tagId(tag) != 9)
            return null;
        if (NBT.list.size(tag) != 0 && handler.tagId(NBT.list.get(tag, 0)) != type.id)
            return null;
        return new EditList(scope, node.child(key, tag));
    }

    @Override
    public NList getOrAddList(String key, NBTType type)
    {
        NList existing = getList(key, type);
        if (existing != null)
            return existing;
        handler.putTag(node.write(), key, NBT.list.newNmsInstance());
        return getList(key, type);
    }

    @Override
    public void setList(String key, NList list)
    {
        putInternalTag(key, list.getInternal());
    }
}
