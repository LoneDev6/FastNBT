package dev.lone.FastNBT.NBT;

import dev.lone.FastNBT.NBT.impl.Compound.Compound_v1_16_R3;
import dev.lone.FastNBT.NBT.impl.Compound.ICompound;
import dev.lone.FastNBT.NBT.impl.CraftItemStack.CraftItemStack_v1_16_R3;
import dev.lone.FastNBT.NBT.impl.CraftItemStack.ICraftItemStack;
import dev.lone.FastNBT.NBT.impl.NBT.INBT;
import dev.lone.FastNBT.NBT.impl.NBT.NBT_v1_16_R3;
import org.bukkit.Bukkit;

public class FastNBT
{
    public static INBT nbt;
    public static ICraftItemStack item;
    public static ICompound compound;

    public static void init()
    {
        String implVersion = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        switch (implVersion)
        {
            case "v1_16_R3":
                nbt = new NBT_v1_16_R3();
                item = new CraftItemStack_v1_16_R3();
                compound = new Compound_v1_16_R3();
                break;
        }
        //TODO: check missing implementation
    }    
}
