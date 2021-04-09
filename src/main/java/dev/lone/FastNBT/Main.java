package dev.lone.FastNBT;

import dev.lone.FastNBT.NBT.FastNBT;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener
{
    @Override
    public void onEnable()
    {
        FastNBT.init();

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

        FastNBT.item.setString(e.getItem(), "nice cock bro", "yea i know");
        System.out.println(FastNBT.item.toString(e.getItem()));

        Object compoundTest = FastNBT.item.getCompound(e.getItem(), "test");
        FastNBT.nbt.setString(compoundTest, "verygood", "hellyea");


    }
}
