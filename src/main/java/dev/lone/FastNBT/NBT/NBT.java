package dev.lone.FastNBT.NBT;

import dev.lone.FastNBT.NBT.impl.CraftItemStack.ICraftItemStack;
import dev.lone.FastNBT.NBT.impl.CraftItemStack.CraftItemStack_v1_16_R3;
import dev.lone.FastNBT.NBT.impl.NBT.ICompound;
import dev.lone.FastNBT.NBT.impl.NBT.NBT_v1_16_R3;
import dev.lone.FastNBT.NBT.impl.NBTTagList.INBTTagList;
import dev.lone.FastNBT.NBT.impl.NBTTagList.NBTTagList_v1_16_R3;
import org.bukkit.Bukkit;

public class NBT
{
    private static ICompound nbt;
    private static ICraftItemStack item;
    private static INBTTagList nbtTagList;

    public static void init()
    {
        String implVersion = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        switch (implVersion)
        {
            case "v1_16_R3":
                nbt = new NBT_v1_16_R3();
                item = new CraftItemStack_v1_16_R3();
                nbtTagList = new NBTTagList_v1_16_R3();
                break;
        }
        //TODO: check missing implementation
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
