package beer.devs.fastnbt;

import org.bukkit.plugin.Plugin;
import org.junit.Test;

import java.io.File;
import java.lang.reflect.Proxy;

import static org.junit.Assert.assertEquals;

public final class ApiMetricsCheck
{
    @Test
    public void usesPluginsDirectoryEvenBeforeAPluginIsEnabled()
    {
        File dataFolder = new File("server/plugins/ItemsAdder");
        Plugin loadedPlugin = (Plugin) Proxy.newProxyInstance(Plugin.class.getClassLoader(),
                new Class<?>[]{Plugin.class}, (proxy, method, args) -> {
                    if (method.getName().equals("getDataFolder"))
                        return dataFolder;
                    throw new AssertionError("Unexpected plugin method: " + method.getName());
                });

        assertEquals(new File("server/plugins"), ApiMetrics.pluginFolder(null, new Plugin[]{loadedPlugin}));
        assertEquals(new File("plugins"), ApiMetrics.pluginFolder(null, new Plugin[0]));
        assertEquals(new File("server/plugins"), ApiMetrics.pluginFolder(loadedPlugin, new Plugin[0]));
    }
}
