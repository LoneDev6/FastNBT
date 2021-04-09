package dev.lone.FastNBT;

import dev.lone.FastNBT.NBT.NBT;
import dev.lone.FastNBT.NBT.NItem;
import dev.lone.FastNBT.NBT.impl.NBTTypeId;
import dev.lone.LoneLibs.nbt.nbtapi.NBTCompoundList;
import dev.lone.LoneLibs.nbt.nbtapi.NBTItem;
import dev.lone.LoneLibs.nbt.nbtapi.NBTList;
import dev.lone.LoneLibs.nbt.nbtapi.NBTListCompound;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener
{
    @Override
    public void onEnable()
    {
        NBT.init();

        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable()
    {
        // Plugin shutdown logic
    }

    @EventHandler
    private void onInteract(PlayerInteractEvent e)
    {
        if(!e.hasItem())
            return;


        for(int i=0;i<64*1000;i++)
        {
            ItemStack first = new ItemStack(Material.DIAMOND);
            e.getPlayer().getInventory().addItem(testFastNbt(first));

            ItemStack second = new ItemStack(Material.EMERALD);
            e.getPlayer().getInventory().addItem(testOldNbt(second));
        }
    }

    //NBTAPI 122 samples                        | 316 samples
    //FastNBT 12 samples = 90.16% less time     | 38 samples = 87.97% less time

    private ItemStack testOldNbt(ItemStack item)
    {
        NBTItem nbt = new NBTItem(item);
        nbt.setString("nice cock bro", "yea i know");
        nbt.addCompound("test").setString("verygood", "hellyea");

        NBTList<String> testList = nbt.getCompound("test").getStringList("test_list");
        testList.add(0, "bruh");

        item = nbt.getItem();

//        System.out.println(testList.get(0));
//        System.out.println(nbt.toString());

        return item;
    }

    private ItemStack testFastNbt(ItemStack item)
    {
        item = NBT.item().convert(item);
        NBT.item().setString(item, "nice cock bro", "yea i know");

        Object compoundTest = NBT.item().getOrAddCompound(item, "test");
        NBT.compound().setString(compoundTest, "verygood", "hellyea");


        Object nbtList = NBT.compound().getOrAddList(compoundTest, "test_list", NBTTypeId.String.id);
        NBT.nbtTagList().add(nbtList, 0, "bruh");

//        System.out.println(NBT.nbtTagList().getStringAt(nbtList, 0));
//        System.out.println(NBT.item().toString(item));

        return item;
    }

    private ItemStack testFastNbt2(ItemStack item)
    {
        NItem nItem = new NItem(item);
        nItem.setString("nice cock bro", "yea i know");

        Object compoundTest = nItem.getOrAddCompound("test");
        NBT.compound().setString(compoundTest, "verygood", "hellyea");


        Object nbtList = NBT.compound().getOrAddList(compoundTest, "test_list", NBTTypeId.String.id);
        NBT.nbtTagList().add(nbtList, 0, "bruh");

//        System.out.println(NBT.nbtTagList().getStringAt(nbtList, 0));
//        System.out.println(NBT.item().toString(item));

        return item;
    }
}
