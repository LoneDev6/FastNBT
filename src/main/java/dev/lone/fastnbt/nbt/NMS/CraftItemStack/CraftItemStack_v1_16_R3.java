package dev.lone.fastnbt.nbt.NMS.CraftItemStack;

import dev.lone.fastnbt.nbt.NBT;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import net.minecraft.server.v1_16_R3.NBTTagList;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class CraftItemStack_v1_16_R3 implements ICraftItemStack<NBTTagList, NBTTagCompound, CraftItemStack>
{
    @Override
    public NBTTagCompound newCompoundInstance()
    {
        return new NBTTagCompound();
    }

    @Override
    public CraftItemStack convert(ItemStack itemStack)
    {
        if (itemStack instanceof CraftItemStack)
            return ((CraftItemStack) itemStack);
        return CraftItemStack.asCraftCopy(itemStack);
    }

    @Override
    public boolean hasNbt(ItemStack itemStack)
    {
        if(!NBT.isInstanceOfCraftItemStack(itemStack))
        {
            if(itemStack != null && itemStack.hasItemMeta())
                return true;
            return false;
        }
        net.minecraft.server.v1_16_R3.ItemStack handle = toCraftItemStack(itemStack).getHandle();
        if(handle == null)
            return false;
        return handle.hasTag();
    }

    @Override
    public void merge(ItemStack itemStack, ItemStack otherItem)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        CraftItemStack other = toCraftItemStack(otherItem);
        this.merge(craftItemStack.getHandle().getOrCreateTag(), other.getHandle().getOrCreateTag());
    }

    private CraftItemStack toCraftItemStack(ItemStack itemStack)
    {
        return ((CraftItemStack) itemStack);
    }

    @Override
    public void setByte(ItemStack itemStack, String key, byte param)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        NBT.compound().setByte(craftItemStack.getHandle().getOrCreateTag(), key, param);
    }

    @Override
    public void setShort(ItemStack itemStack, String key, short param)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        NBT.compound().setShort(craftItemStack.getHandle().getOrCreateTag(), key, param);
    }

    @Override
    public void setInt(ItemStack itemStack, String key, int param)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        NBT.compound().setInt(craftItemStack.getHandle().getOrCreateTag(), key, param);
    }

    @Override
    public void setLong(ItemStack itemStack, String key, long param)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        NBT.compound().setLong(craftItemStack.getHandle().getOrCreateTag(), key, param);
    }

    @Override
    public void setUUID(ItemStack itemStack, String key, UUID param)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        NBT.compound().setUUID(craftItemStack.getHandle().getOrCreateTag(), key, param);
    }

    @Override
    public void setFloat(ItemStack itemStack, String key, float param)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        NBT.compound().setFloat(craftItemStack.getHandle().getOrCreateTag(), key, param);
    }

    @Override
    public void setDouble(ItemStack itemStack, String key, double param)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        NBT.compound().setDouble(craftItemStack.getHandle().getOrCreateTag(), key, param);
    }

    @Override
    public void setString(ItemStack itemStack, String key, String param)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        NBT.compound().setString(craftItemStack.getHandle().getOrCreateTag(), key, param);
    }

    @Override
    public void setByteArray(ItemStack itemStack, String key, byte[] param)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        NBT.compound().setByteArray(craftItemStack.getHandle().getOrCreateTag(), key, param);
    }

    @Override
    public void setIntArray(ItemStack itemStack, String key, int[] param)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        NBT.compound().setIntArray(craftItemStack.getHandle().getOrCreateTag(), key, param);
    }

    @Override
    public void setIntegerList(ItemStack itemStack, String key, List<Integer> param)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        NBT.compound().setIntegerList(craftItemStack.getHandle().getOrCreateTag(), key, param);
    }

    @Override
    public void setLongArray(ItemStack itemStack, String key, long[] param)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        NBT.compound().setLongArray(craftItemStack.getHandle().getOrCreateTag(), key, param);
    }

    @Override
    public void setLongList(ItemStack itemStack, String key, List<Long> param)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        NBT.compound().setLongList(craftItemStack.getHandle().getOrCreateTag(), key, param);
    }

    @Override
    public void setBoolean(ItemStack itemStack, String key, boolean param)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        NBT.compound().setBoolean(craftItemStack.getHandle().getOrCreateTag(), key, param);
    }

    @Override
    public boolean hasKey(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        if (!craftItemStack.getHandle().hasTag())
            return false;
        return NBT.compound().hasKey(craftItemStack.getHandle().getTag(), key);
    }

    @Override
    public boolean hasUUID(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        if (!craftItemStack.getHandle().hasTag())
            return false;
        return NBT.compound().hasUUID(craftItemStack.getHandle().getTag(), key);
    }

    @Override
    public @Nullable UUID getUUID(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        return NBT.compound().getUUID(craftItemStack.getHandle().getOrCreateTag(), key);
    }

    @Override
    public byte getByte(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        return NBT.compound().getByte(craftItemStack.getHandle().getOrCreateTag(), key);
    }

    @Override
    public short getShort(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        return NBT.compound().getShort(craftItemStack.getHandle().getOrCreateTag(), key);
    }

    @Override
    public int getInt(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        return NBT.compound().getInt(craftItemStack.getHandle().getOrCreateTag(), key);
    }

    @Override
    public long getLong(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        return NBT.compound().getLong(craftItemStack.getHandle().getOrCreateTag(), key);
    }

    @Override
    public float getFloat(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        return NBT.compound().getFloat(craftItemStack.getHandle().getOrCreateTag(), key);
    }

    @Override
    public double getDouble(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        return NBT.compound().getDouble(craftItemStack.getHandle().getOrCreateTag(), key);
    }

    @Override
    public String getString(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        return NBT.compound().getString(craftItemStack.getHandle().getOrCreateTag(), key);
    }

    @Override
    public byte[] getByteArray(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        return NBT.compound().getByteArray(craftItemStack.getHandle().getOrCreateTag(), key);
    }

    @Override
    public int[] getIntArray(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        return NBT.compound().getIntArray(craftItemStack.getHandle().getOrCreateTag(), key);
    }

    @Override
    public long[] getLongArray(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        return NBT.compound().getLongArray(craftItemStack.getHandle().getOrCreateTag(), key);
    }

    @Override
    public NBTTagCompound getCompound(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        return (NBTTagCompound) NBT.compound().getCompound(craftItemStack.getHandle().getOrCreateTag(), key);
    }

    @Override
    public NBTTagCompound getOrAddCompound(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        return (NBTTagCompound) NBT.compound().getOrAddCompound(craftItemStack.getHandle().getOrCreateTag(), key);
    }

    @Override
    public NBTTagList getList(ItemStack itemStack, String key, int typeID)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        return (NBTTagList) NBT.compound().getList(craftItemStack.getHandle().getOrCreateTag(), key, typeID);
    }

    @Override
    public NBTTagList getOrAddList(ItemStack itemStack, String key, int typeID)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        return (NBTTagList) NBT.compound().getOrAddList(craftItemStack.getHandle().getOrCreateTag(), key, typeID);
    }

    @Override
    public boolean getBoolean(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        return NBT.compound().getBoolean(craftItemStack.getHandle().getOrCreateTag(), key);
    }

    @Override
    public Set<String> getKeys(ItemStack itemStack)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        return craftItemStack.getHandle().getOrCreateTag().getKeys();
    }

    @Override
    public boolean isEmpty(ItemStack itemStack)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        if (!craftItemStack.getHandle().hasTag())
            return true;
        return NBT.compound().isEmpty(craftItemStack.getHandle().getTag());
    }

    @Override
    public void remove(ItemStack itemStack, String key)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        if (!craftItemStack.getHandle().hasTag())
            return;
        NBT.compound().remove(craftItemStack.getHandle().getTag(), key);
    }

    @Override
    public void merge(NBTTagCompound handle, NBTTagCompound otherHandle)
    {
        handle.a(otherHandle);
    }

    @Override
    public ItemStack asBukkitMirror(ItemStack itemStack)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        return craftItemStack.getHandle().asBukkitMirror();
    }

    @Override
    public Object asNMSCopy(ItemStack itemStack)
    {
        return CraftItemStack.asNMSCopy(itemStack);
    }

    @Override
    public ItemStack compoundToItemStack(NBTTagCompound compound)
    {
        return net.minecraft.server.v1_16_R3.ItemStack.fromCompound(compound).asBukkitMirror();
    }

    @Override
    public String toString(ItemStack itemStack)
    {
        CraftItemStack craftItemStack = toCraftItemStack(itemStack);
        if (!craftItemStack.getHandle().hasTag())
            return null;
        return NBT.compound().toString(craftItemStack.getHandle().getTag());
    }
}
