package dev.lone.fastnbt;

import dev.lone.LoneLibs.nbt.nbtapi.NBTCompoundList;
import dev.lone.LoneLibs.nbt.nbtapi.NBTFile;
import dev.lone.fastnbt.nbt.*;
import dev.lone.LoneLibs.nbt.nbtapi.NBTItem;
import dev.lone.LoneLibs.nbt.nbtapi.NBTList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

public class Test implements CommandExecutor
{
    Plugin plugin;

    int iterations = 0;
    int fileListIteration = 5000;

    public Test(Plugin plugin)
    {
        this.plugin = plugin;
    }

    public void register()
    {
        Bukkit.getPluginCommand("fastnbttest").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args)
    {
        //int iterations = 2_000_000;
        //int iterations = 1_000_000;
        System.out.printf("Started iterating %d items%n", iterations);
        Instant start;
        Duration dur1;
        Duration dur2;
        Duration dur3;

        Duration dur4;
        Duration dur5;

        start = Instant.now();
        for (int i = 0; i < iterations; i++)
        {
            ItemStack funcItem = new ItemStack(Material.DIAMOND);
            funcItem = test_fastNBTProc(funcItem);
        }
        dur1 = Duration.between(start, Instant.now());

        start = Instant.now();
        for (int i = 0; i < iterations; i++)
        {
            ItemStack ooItem = new ItemStack(Material.DIAMOND);
            ooItem = test_fastNBTOO(ooItem);
        }
        dur2 = Duration.between(start, Instant.now());

        start = Instant.now();
        for (int i = 0; i < iterations; i++)
        {
            ItemStack nbtApiItem = new ItemStack(Material.DIAMOND);
            nbtApiItem = test_nbtApi(nbtApiItem);
        }
        dur3 = Duration.between(start, Instant.now());


        start = Instant.now();
        testFileOld();
        dur4 = Duration.between(start, Instant.now());

        start = Instant.now();
        testFileNew();
        dur5 = Duration.between(start, Instant.now());


        System.out.println("Processed items: " + iterations);
        System.out.println(" - NBTAPI:         " + dur3.toString().substring(2));
        System.out.println(" - FastNBT (OO):   " + dur2.toString().substring(2) + " " + String.format("%.2f", ((dur2.toMillis() - dur3.toMillis()) / (double) dur3.toMillis() * 100)) + "%");
        System.out.println(" - FastNBT (func): " + dur1.toString().substring(2) + " " + String.format("%.2f", (((dur1.toMillis() - dur3.toMillis()) / (double) dur3.toMillis() * 100))) + "%");

        System.out.println();
        System.out.println("Processed file");
        System.out.println(" - NBTAPI:         " + dur4.toString().substring(2));
        System.out.println(" - FastNBT (OO):   " + dur5.toString().substring(2) + " " + String.format("%.2f", ((dur5.toMillis() - dur4.toMillis()) / (double) dur4.toMillis() * 100)) + "%");


        return true;
    }

    //NBTAPI 122 samples                        | 316 samples
    //FastNBT 12 samples = 90.16% less time     | 38 samples = 87.97% less time
    private ItemStack test_nbtApi(ItemStack item)
    {
        NBTItem nbt = new NBTItem(item);
        nbt.setString("nice cock bro", "yea i know");
        nbt.addCompound("test").setString("verygood", "hellyea");

        NBTList<String> testList = nbt.getCompound("test").getStringList("test_list");
        testList.add(0, "bruh");

        item = nbt.getItem();
        return item;
    }

    private ItemStack test_fastNBTProc(ItemStack item)
    {
        item = NBT.item().convert(item);
        NBT.item().setString(item, "nice cock bro", "yea i know");

        Object compoundTest = NBT.item().getOrAddCompound(item, "test");
        NBT.compound().setString(compoundTest, "verygood", "hellyea");


        Object nbtList = NBT.compound().getOrAddList(compoundTest, "test_list", NBTTypeId.String.id);
        NBT.nbtTagList().add(nbtList, 0, "bruh");
        return item;
    }

    private ItemStack test_fastNBTOO(ItemStack item)
    {
        NItem nItem = new NItem(item);
        nItem.setString("nice cock bro", "yea i know");

        NCompound nCompound = nItem.getOrAddCompound("test");
        nCompound.setString("verygood", "hellyea");

        NTagList nbtList = nCompound.getOrAddList("test_list", NBTTypeId.String);
        nbtList.add(0, "bruh");
        return item;
    }

    private void testFileOld()
    {
        try
        {
            File file = new File(plugin.getDataFolder(), "test1.nbt");
            NBTFile nFile = new NBTFile(file);
            String wowman = nFile.getString("wowman");

            nFile.setString("wowman", "nice kok");

//            NBTList<String> list = nFile.getStringList("list");
//            for (int i = 0; i < fileListIteration; i++)
//                list.add("wow");
//
//            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
//                NBTList asd = nFile.getStringList("asd");
//                asd.add(0, "asdasd");
//                asd.add(0, "asdasd");
//                asd.add(0, "asdasd");
//                asd.add(0, "asdasd");
//                list.add(0, "test");
//            });

            nFile.save();

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void testFileNew()
    {
        try
        {
            File file = new File(plugin.getDataFolder(), "test2.nbt");
            NFile nFile = new NFile(file);
            String wowman = nFile.getString("wowman");

            nFile.setString("wowman", "nice kok");

//            NTagList list = nFile.getOrAddList("list", NBTTypeId.String);
//            for (int i = 0; i < fileListIteration; i++)
//                list.add("wow");
//
//            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
//                NTagList asd = nFile.getOrAddList("asd", NBTTypeId.String);
//                asd.add(0, "asdasd");
//                asd.add(0, "asdasd");
//                asd.add(0, "asdasd");
//                asd.add(0, "asdasd");
//                list.add(0, "test");
//            });

            nFile.save();

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
