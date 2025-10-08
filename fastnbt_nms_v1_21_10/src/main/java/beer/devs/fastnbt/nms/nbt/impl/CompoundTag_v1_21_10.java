package beer.devs.fastnbt.nms.nbt.impl;

import beer.devs.fastnbt.nms.nbt.ICompoundTag;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.item.ItemParser;
import net.minecraft.nbt.*;
import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@SuppressWarnings({"unchecked", "unused"})
public class CompoundTag_v1_21_10 implements ICompoundTag<CompoundTag, ListTag, CompoundTag>
{
    @Override
    public CompoundTag newInstance()
    {
        return new CompoundTag();
    }

    @Override
    public void setByte(CompoundTag handle, String key, byte param)
    {
        handle.putByte(key, param);
    }

    @Override
    public void setShort(CompoundTag handle, String key, short param)
    {
        handle.putShort(key, param);
    }

    @Override
    public void setInt(CompoundTag handle, String key, int param)
    {
        handle.putInt(key, param);
    }

    @Override
    public void setLong(CompoundTag handle, String key, long param)
    {
        handle.putLong(key, param);
    }

    @Override
    public void setUUID(CompoundTag handle, String key, UUID param)
    {
        NBTUtilsModern_v1_21_10.putUUID(handle, key, param);
    }

    @Override
    public void setFloat(CompoundTag handle, String key, float param)
    {
        handle.putFloat(key, param);
    }

    @Override
    public void setDouble(CompoundTag handle, String key, double param)
    {
        handle.putDouble(key, param);
    }

    @Override
    public void setString(CompoundTag handle, String key, String param)
    {
        handle.putString(key, param);
    }

    @Override
    public void setByteArray(CompoundTag handle, String key, byte[] param)
    {
        handle.putByteArray(key, param);
    }

    @Override
    public void setIntArray(CompoundTag handle, String key, int[] param)
    {
        handle.putIntArray(key, param);
    }

    @Override
    public void setIntegerList(CompoundTag handle, String key, List<Integer> param)
    {
       handle.putIntArray(key, param.stream().mapToInt(Integer::intValue).toArray());
    }

    @Override
    public void setLongArray(CompoundTag handle, String key, long[] param)
    {
        handle.putLongArray(key, param);
    }

    @Override
    public void setLongList(CompoundTag handle, String key, List<Long> param)
    {
        handle.putLongArray(key, param.stream().mapToLong(Long::longValue).toArray());
    }

    @Override
    public void setBoolean(CompoundTag handle, String key, boolean param)
    {
        handle.putBoolean(key, param);
    }

    @Override
    public boolean hasKey(CompoundTag handle, String key)
    {
        if (handle == null)
            return false;
        return handle.contains(key);
    }

    @Override
    public boolean hasUUID(CompoundTag handle, String key)
    {
        if (handle == null)
            return false;
        Tag tag = handle.get(key);
        return tag != null && tag.getType() == IntArrayTag.TYPE;
    }

    @Override
    public UUID getUUID(CompoundTag handle, String key)
    {
        return NBTUtilsModern_v1_21_10.getUUID(handle, key);
    }

    @Override
    public byte getByte(CompoundTag handle, String key)
    {
        return handle.getByte(key).orElse((byte) 0);
    }

    @Override
    public short getShort(CompoundTag handle, String key)
    {
        return handle.getShort(key).orElse((short) 0);
    }

    @Override
    public int getInt(CompoundTag handle, String key)
    {
        return handle.getInt(key).orElse(0);
    }

    @Override
    public long getLong(CompoundTag handle, String key)
    {
        return handle.getLong(key).orElse(0L);
    }

    @Override
    public float getFloat(CompoundTag handle, String key)
    {
        return handle.getFloat(key).orElse(0.0f);
    }

    @Override
    public double getDouble(CompoundTag handle, String key)
    {
        return handle.getDouble(key).orElse(0.0);
    }

    @Override
    public String getString(CompoundTag handle, String key)
    {
        return handle.getString(key).orElse(null);
    }

    @Override
    public byte @Nullable [] getByteArray(CompoundTag handle, String key)
    {
        return handle.getByteArray(key).orElse(null);
    }

    @Override
    public int @Nullable [] getIntArray(CompoundTag handle, String key)
    {
        return handle.getIntArray(key).orElse(null);
    }

    @Override
    public long @Nullable [] getLongArray(CompoundTag handle, String key)
    {
        return handle.getLongArray(key).orElse(null);
    }

    @Override
    public CompoundTag getCompound(CompoundTag handle, String key)
    {
        return handle.getCompound(key).orElse(null);
    }

    @Override
    public CompoundTag getOrAddCompound(CompoundTag handle, String key)
    {
        if (!handle.contains(key))
            handle.put(key, new CompoundTag());
        //noinspection OptionalGetWithoutIsPresent
        return handle.getCompound(key).get(); // This cannot be null because we just added it
    }

    @Override
    public ListTag getList(CompoundTag handle, String key, int typeID)
    {
        return handle.getList(key).orElse(null);
    }

    @Override
    public ListTag getOrAddList(CompoundTag handle, String key, int typeID)
    {
        if (!handle.contains(key))
            handle.put(key, new ListTag());
        //noinspection OptionalGetWithoutIsPresent
        return handle.getList(key).get(); // This cannot be null because we just added it
    }

    @Override
    public void putTag(CompoundTag handle, String key, Object value)
    {
        handle.put(key, (Tag) value);
    }

    @Override
    public boolean getBoolean(CompoundTag handle, String key)
    {
        return handle.getBoolean(key).orElse(false);
    }

    @Override
    public Set<String> getKeys(CompoundTag handle)
    {
        return handle.keySet();
    }

    @Override
    public boolean isEmpty(CompoundTag handle)
    {
        if (handle == null)
            return true;
        return handle.isEmpty();
    }

    @Override
    public void remove(CompoundTag handle, String key)
    {
        if (handle == null)
            return;
        handle.remove(key);
    }

    @Override
    public void merge(CompoundTag handle, CompoundTag otherHandle)
    {
        handle.merge(otherHandle);
    }

    @Override
    public String toString(CompoundTag handle)
    {
        return handle.toString();
    }

    @Override
    public boolean isValid(String component)
    {
        try
        {
            new ItemParser(Commands.createValidationContext(MinecraftServer.getServer().registryAccess())).parse(new StringReader(component));
            return true;
        }
        catch (CommandSyntaxException ex)
        {
            ex.printStackTrace();
            return false;
        }
    }
}
