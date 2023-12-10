package dev.lone.fastnbt;

import dev.lone.fastnbt.nms.Version;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener
{
    @Override
    public void onEnable()
    {
        Version.init(this);
    }
}
