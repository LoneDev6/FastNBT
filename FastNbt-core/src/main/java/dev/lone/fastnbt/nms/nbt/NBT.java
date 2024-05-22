package dev.lone.fastnbt.nms.nbt;

import dev.lone.fastnbt.Metrics;
import dev.lone.fastnbt.nms.Implementation;
import dev.lone.fastnbt.nms.Version;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@SuppressWarnings("rawtypes")
public class NBT
{
    public static ICompoundTag compound;
    public static ICraftItemStack item;
    public static IListTag list;
    public static INBTIO streamTools;
    public static IDataComponents dataComponents;

    static
    {
        initMetrics();

        try
        {
            compound = Implementation.find(ICompoundTag.class);
            item = Implementation.find(ICraftItemStack.class);
            list = Implementation.find(IListTag.class);
            streamTools = Implementation.find(INBTIO.class);
            if(Version.isNewerThan(Version.v1_20_4))
                dataComponents = Implementation.find(IDataComponents.class);
        }
        catch (Throwable ex)
        {
            Bukkit.getLogger().severe("This server is not compatible with FastNBT. Server: " + Bukkit.getVersion() + " (NMS: " + Version.get() + ")");
            ex.printStackTrace();
            Bukkit.shutdown();
        }
    }

    public static boolean isInstanceOfCraftItemStack(ItemStack itemStack)
    {
        return item.isInstanceOfCraftItemStack(itemStack);
    }

    private static void initMetrics()
    {
        try
        {
            Plugin plugin = getPlugin();
            if (plugin instanceof JavaPlugin)
            {
                new Metrics(plugin, 10);
                return;
            }
        }
        catch (Throwable ex)
        {
            ex.printStackTrace();
        }
        Bukkit.getServer().getLogger().info("[FastNBT] Failed to initialize metrics.");
    }

    protected static @Nullable Plugin getPlugin()
    {
        ClassLoader classLoader = NBT.class.getClassLoader();
        InputStream stream = classLoader.getResourceAsStream("plugin.yml");
        if (stream != null)
        {
            try (InputStreamReader reader = new InputStreamReader(stream))
            {
                YamlConfiguration yaml = YamlConfiguration.loadConfiguration(reader);
                String name = yaml.getString("name");
                if(name == null) // Should not happen in any way.
                    return null;
                return Bukkit.getPluginManager().getPlugin(name);
            }
            catch (IOException ignored) {}
        }
        return null;
    }
}
