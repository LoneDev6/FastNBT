package beer.devs.fastnbt.nms;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class NMSImpl
{
    public static final String LIB_VERSION = "1.4.11";

    private static final String NMS_IMPL_PACKAGE = "beer.devs.fastnbt.nms.nbt.impl";

    public static boolean isPaper;
    private static final Map<Class<?>, Constructor<?>> CACHED_CONSTRUCTORS = new HashMap<>();

    static
    {
        isPaper = isPaper();
    }

    public static boolean hasClass(String className)
    {
        try
        {
            Class.forName(className);
            return true;
        }
        catch (Throwable ignored) {}
        return false;
    }

    public static boolean isPaper()
    {
        String name = Bukkit.getServer().getName();
        if (name.contains("Paper") || name.contains("Purpur"))
            return true;

        return
                hasClass("com.destroystokyo.paper.event.player.PlayerSetSpawnEvent$Cause") ||
                        hasClass("com.destroystokyo.paper.utils.PaperPluginLogger") ||
                        hasClass("io.papermc.paper.ServerBuildInfo") ||
                        hasClass("io.papermc.paper.text.PaperComponents");
    }

    @Nullable
    public static Field getField(Class<?> clazz, Class<?> type)
    {
        for (Field field : clazz.getDeclaredFields())
        {
            if (field.getType() == type)
            {
                field.setAccessible(true);
                return field;
            }
        }
        return null;
    }

    /**
     * Since Paper 1.20.5 the NMS classes might not be Spigot mapped anymore.
     * They might use the Mojang mappings.
     */
    public static boolean isSpigotMapped()
    {
        // Split Bukkit.getServer().getClass().getPackageName() and check if the last entry starts by v1_
        String[] split = Bukkit.getServer().getClass().getPackageName().split("\\.");
        String version = split[split.length - 1];
        return version.startsWith("v1_");
    }

    private static Class<?> getImplClass(Class<?> type) throws ClassNotFoundException
    {
        String classNamePath = null;
         try
         {
             Version version = Version.get();
             String typeName = type.getSimpleName();
             if(typeName.startsWith("I"))
                 typeName = typeName.substring(1);

             classNamePath = NMS_IMPL_PACKAGE + "." + typeName + "_" + version.toString();
             return Class.forName(classNamePath);
         }
         catch (ClassNotFoundException e)
         {
             throw new ClassNotFoundException("Class not found " + type.getName() + " (" + classNamePath + ").", e);
         }
    }

    public static <T> T instantiate(Class<T> type)
    {
        Class<?> clazz = null;
        try
        {
            clazz = getImplClass(type);
            return instantiate(type, clazz);
        }
        catch (Throwable e)
        {
            FastNbtLogger.error("Can't instantiate NMS wrapper");
            FastNbtLogger.error("This server version is not compatible with this plugin.");
            throw new RuntimeException("Failed to create instance for class: " + clazz + " (" + type.getName() + ")", e);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> T instantiate(Class<T> type, Class<?> clazz) throws Exception
    {
        Constructor<?> constructor = CACHED_CONSTRUCTORS.computeIfAbsent(type, s -> {
            try
            {
                return clazz.getDeclaredConstructor();
            }
            catch (NoSuchMethodException e)
            {
                throw new RuntimeException(e);
            }
        });
        return (T) constructor.newInstance();
    }

}
