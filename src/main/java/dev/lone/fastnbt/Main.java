package dev.lone.fastnbt;

import dev.lone.fastnbt.nbt.NBT;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener
{
    @Override
    public void onEnable()
    {
        NBT.init(this);
        new Test().register();
    }
}
