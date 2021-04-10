package dev.lone.fastnbt.nbt;

import dev.lone.fastnbt.nbt.NMS.Compound.*;
import dev.lone.fastnbt.nbt.NMS.CraftItemStack.*;
import dev.lone.fastnbt.nbt.NMS.NBTStreamTools.INBTStreamTools;
import dev.lone.fastnbt.nbt.NMS.NBTStreamTools.NBTStreamTools_v1_16_R3;
import dev.lone.fastnbt.nbt.NMS.NBTTagList.*;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class NBT
{
    private static ICompound nbt;
    private static ICraftItemStack item;
    private static INBTTagList nbtTagList;
    private static INBTStreamTools nbtStreamTools;

    private static Class class_CraftItemStack;

    public static void init(Plugin plugin)
    {
        if (nbt != null && item != null && nbtTagList != null)
            return;

        String nmsVersion = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        switch (nmsVersion)
        {
            case "v1_16_R3":
                nbt = new Compound_v1_16_R3();
                item = new CraftItemStack_v1_16_R3();
                nbtTagList = new NBTTagList_v1_16_R3();
                nbtStreamTools = new NBTStreamTools_v1_16_R3();
                break;
            case "v1_16_R2":
                nbt = new Compound_v1_16_R2();
                item = new CraftItemStack_v1_16_R2();
                nbtTagList = new NBTTagList_v1_16_R2();
                break;
            case "v1_15_R1":
                nbt = new Compound_v1_15_R1();
                item = new CraftItemStack_v1_15_R1();
                nbtTagList = new NBTTagList_v1_15_R1();
                break;
            case "v1_14_R1":
                nbt = new Compound_v1_14_R1();
                item = new CraftItemStack_v1_14_R1();
                nbtTagList = new NBTTagList_v1_14_R1();
                break;
        }

        try
        {
            class_CraftItemStack = Class.forName("org.bukkit.craftbukkit." + nmsVersion + ".inventory.CraftItemStack");
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        if (nbt == null || item == null || nbtTagList() == null)
        {
            plugin.getLogger().severe("This server is not compatible with FastNBT. Server: " + Bukkit.getVersion() + " (NMS: " + nmsVersion + ")");
        }
    }

    public static ICompound compound()
    {
        return nbt;
    }

    public static ICraftItemStack item()
    {
        return item;
    }

    public static INBTTagList nbtTagList()
    {
        return nbtTagList;
    }

    public static INBTStreamTools getNbtStreamTools()
    {
        return nbtStreamTools;
    }

    public static boolean isInstanceOfCraftItemStack(ItemStack itemStack)
    {
        return class_CraftItemStack.isInstance(itemStack);
    }
}
