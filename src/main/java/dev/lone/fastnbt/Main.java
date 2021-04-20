package dev.lone.fastnbt;

import dev.lone.fastnbt.nbt.NBT;
import dev.lone.fastnbt.nbt.NItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener
{
    @Override
    public void onEnable()
    {
        NBT.init(this);
        new Test(this).register();

        Bukkit.getPluginManager().registerEvents(this, this);


        ItemStack first = new ItemStack(Material.STONE);
        ItemMeta firstMeta = first.getItemMeta();
        firstMeta.setDisplayName("TEST1");
        first.setItemMeta(firstMeta);

        NItem testa = new NItem(first);


        ItemStack second = new ItemStack(Material.DIAMOND);
        ItemMeta secondMeta = first.getItemMeta();
        secondMeta.setDisplayName("TEST2");
        secondMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        second.setItemMeta(secondMeta);

        testa.merge(new NItem(second));
        testa.save();
        System.out.println(testa);
        System.out.println(first);

    }
}
