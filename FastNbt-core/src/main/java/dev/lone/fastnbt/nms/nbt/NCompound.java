package dev.lone.fastnbt.nms.nbt;

import dev.lone.fastnbt.nms.nbt.nms.ICompoundTag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@SuppressWarnings({"unchecked", "rawtypes"})
public class NCompound
{
    protected ICompoundTag handler;
    protected Object handle;

    public NCompound()
    {
        this(Nbt.compound.newInstance());
    }

    public NCompound(Object handle)
    {
        this.handle = handle;
        this.handler = Nbt.compound;
    }

    public static Object newNmsInstance()
    {
        return Nbt.compound.newInstance();
    }

    public Object getInternal()
    {
        return handle;
    }

    public NCompound setByte(String key, byte param)
    {
        handler.setByte(handle, key, param);
        return this;
    }

    public NCompound setShort(String key, short param)
    {
        handler.setShort(handle, key, param);
        return this;
    }

    public NCompound setInt(String key, int param)
    {
        handler.setInt(handle, key, param);
        return this;
    }

    public NCompound setLong(String key, long param)
    {
        handler.setLong(handle, key, param);
        return this;
    }

    public NCompound setUUID(String key, UUID param)
    {
        handler.setUUID(handle, key, param);
        return this;
    }

    public NCompound setFloat(String key, float param)
    {
        handler.setFloat(handle, key, param);
        return this;
    }

    public NCompound setDouble(String key, double param)
    {
        handler.setDouble(handle, key, param);
        return this;
    }

    public NCompound setString(String key, String param)
    {
        handler.setString(handle, key, param);
        return this;
    }

    public NCompound setByteArray(String key, byte[] param)
    {
        handler.setByteArray(handle, key, param);
        return this;
    }

    public NCompound setIntArray(String key, int[] param)
    {
        handler.setIntArray(handle, key, param);
        return this;
    }

    public NCompound setIntegerList(String key, List<Integer> param)
    {
        handler.setIntegerList(handle, key, param);
        return this;
    }

    public NCompound setLongArray(String key, long[] param)
    {
        handler.setLongArray(handle, key, param);
        return this;
    }

    public NCompound setLongList(String key, List<Long> param)
    {
        handler.setLongList(handle, key, param);
        return this;
    }

    public NCompound setBoolean(String key, boolean param)
    {
        handler.setBoolean(handle, key, param);
        return this;
    }

    public boolean hasKey(String key)
    {
        return handler.hasKey(handle, key);
    }

    public boolean hasUUID(String key)
    {
        return handler.hasUUID(handle, key);
    }

    @Nullable
    public UUID getUUID(String key)
    {
        return handler.getUUID(handle, key);
    }

    public byte getByte(String key)
    {
        return handler.getByte(handle, key);
    }

    public short getShort(String key)
    {
        return handler.getShort(handle, key);
    }

    public int getInt(String key)
    {
        return handler.getInt(handle, key);
    }

    public long getLong(String key)
    {
        return handler.getLong(handle, key);
    }

    public float getFloat(String key)
    {
        return handler.getFloat(handle, key);
    }

    public double getDouble(String key)
    {
        return handler.getDouble(handle, key);
    }

    public String getString(String key)
    {
        return handler.getString(handle, key);
    }

    public byte[] getByteArray(String key)
    {
        return handler.getByteArray(handle, key);
    }

    public int[] getIntArray(String key)
    {
        return handler.getIntArray(handle, key);
    }

    public long[] getLongArray(String key)
    {
        return handler.getLongArray(handle, key);
    }

    @Nullable
    public NCompound getCompound(String key)
    {
        if (handler.hasKey(handle, key))
            return new NCompound(handler.getCompound(handle, key));
        return null;
    }

    public NCompound getOrAddCompound(String key)
    {
        return new NCompound(handler.getOrAddCompound(handle, key));
    }

    public NCompound addCompound(String key)
    {
        remove(key);
        return getOrAddCompound(key);
    }

    public NCompound setCompound(String key, NCompound compound)
    {
        remove(key);
        putInternalTag(key, compound.handle);
        return compound;
    }

    @Nullable
    public NRawList getList(String key, NbtType type)
    {
        if (handler.hasKey(handle, key))
            return new NRawList(handler.getList(handle, key, type.id));
        return null;
    }

    @Nullable
    public NListDouble getListDouble(String key)
    {
        if (handler.hasKey(handle, key))
        {
            Object internal = handler.getList(handle, key, NbtType.Double.id);
            if(internal == null)
                return null;
            return new NListDouble(internal);
        }
        return null;
    }

    @Nullable
    public NListFloat getListFloat(String key)
    {
        if (handler.hasKey(handle, key))
        {
            Object internal = handler.getList(handle, key, NbtType.Float.id);
            if(internal == null)
                return null;
            return new NListFloat(internal);
        }
        return null;
    }

    @Nullable
    public NListInt getListInt(String key)
    {
        if (handler.hasKey(handle, key))
        {
            Object internal = handler.getList(handle, key, NbtType.Float.id);
            if(internal == null)
                return null;
            return new NListInt(internal);
        }
        return null;
    }

    @Nullable
    public NListShort getListShort(String key)
    {
        if (handler.hasKey(handle, key))
        {
            Object internal = handler.getList(handle, key, NbtType.Float.id);
            if(internal == null)
                return null;
            return new NListShort(internal);
        }
        return null;
    }

    @Nullable
    public NListString getListString(String key)
    {
        if (handler.hasKey(handle, key))
        {
            Object internal = handler.getList(handle, key, NbtType.Float.id);
            if(internal == null)
                return null;
            return new NListString(internal);
        }
        return null;
    }

    @Nullable
    public NListList getListList(String key)
    {
        if (handler.hasKey(handle, key))
        {
            Object internal = handler.getList(handle, key, NbtType.Float.id);
            if(internal == null)
                return null;
            return new NListList(internal);
        }
        return null;
    }

    public NRawList getOrAddList(String key, NbtType type)
    {
        return new NRawList(handler.getOrAddList(handle, key, type.id));
    }

    public NRawList addList(String key, NbtType type)
    {
        remove(key);
        return getOrAddList(key, type);
    }

    public void setList(String key, NRawList list)
    {
        remove(key);
        putInternalTag(key, list.handle);
    }

    public <T extends NSafeTagList> void setList(String key, T list)
    {
        remove(key);
        putInternalTag(key, list.handle);
    }

    public boolean getBoolean(String key)
    {
        return handler.getBoolean(handle, key);
    }

    public Set<String> getKeys()
    {
        return handler.getKeys(handle);
    }

    public boolean isEmpty()
    {
        return handler.isEmpty(handle);
    }

    public NCompound remove(String key)
    {
        handler.remove(handle, key);
        return this;
    }

    public NCompound merge(NCompound b)
    {
        handler.merge(this, b);
        return this;
    }

    public String toString()
    {
        return handler.toString(handle);
    }

    @ApiStatus.Internal
    public void putInternalTag(String key, Object nmsTag)
    {
        handler.putTag(handle, key, nmsTag);
    }

    /**
     * Attempts to convert this Compound to a Bukkit ItemStack.
     *
     * @return A Bukkit ItemsStack. Returns null if the Compound is not an item.
     */
    @Nullable
    public ItemStack toBukkitItem()
    {
        return NItem.compoundToBukkitItem(this);
    }
}
