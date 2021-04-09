package dev.lone.fastnbt.nbt;

import dev.lone.fastnbt.nbt.NMS.Compound.*;
import dev.lone.fastnbt.nbt.NMS.CraftItemStack.*;
import dev.lone.fastnbt.nbt.NMS.NBTTagList.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class NBT
{
    private static ICompound nbt;
    private static ICraftItemStack item;
    private static INBTTagList nbtTagList;

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

        if(nbt == null || item == null || nbtTagList() == null)
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
}
