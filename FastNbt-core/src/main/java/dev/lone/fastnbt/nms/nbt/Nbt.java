package dev.lone.fastnbt.nms.nbt;

import dev.lone.fastnbt.nms.Implementation;
import dev.lone.fastnbt.nms.Version;
import dev.lone.fastnbt.nms.nbt.nms.ICompound;
import dev.lone.fastnbt.nms.nbt.nms.ICraftItemStack;
import dev.lone.fastnbt.nms.nbt.nms.INBTStreamTools;
import dev.lone.fastnbt.nms.nbt.nms.INBTTagList;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("rawtypes")
public class Nbt
{
    public static ICompound compound;
    public static ICraftItemStack item;
    public static INBTTagList nbtTagList;
    public static INBTStreamTools nbtStreamTools;

    static
    {
        try
        {
            compound = Implementation.find(ICompound.class, Version.get());
            item = Implementation.find(ICraftItemStack.class, Version.get());
            nbtTagList = Implementation.find(INBTTagList.class, Version.get());
            nbtStreamTools = Implementation.find(INBTStreamTools.class, Version.get());
        }
        catch (Throwable ex)
        {
            Bukkit.getLogger().severe("This server is not compatible with FastNBT. Server: " + Bukkit.getVersion() + " (NMS: " + Version.get() + ")");
            ex.printStackTrace();
            Bukkit.shutdown();
        }
    }

    public static boolean isInstanceOfCraftItemStack(ItemStack itemStack)
    {
        return item.isInstanceOfCraftItemStack(itemStack);
    }
}
