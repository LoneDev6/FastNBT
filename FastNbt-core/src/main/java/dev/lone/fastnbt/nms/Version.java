package dev.lone.fastnbt.nms;

import dev.lone.fastnbt.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings({"unused"})
public enum Version
{
    UNKNOWN(Integer.MAX_VALUE),
    v1_7_R4(174),
    v1_8_R3(183), 
    v1_9_R1(191), 
    v1_9_R2(192), 
    v1_10_R1(1101),
    v1_11_R1(1111), 
    v1_12_R1(1121),
    v1_13_R1(1131), 
    v1_13_R2(1132),
    v1_14_R1(1141),
    v1_15_R1(1151),
    v1_16_R1(1161),
    v1_16_R2(1162),
    v1_16_R3(1163), 
    v1_17_R1(1171), 
    v1_18_R1(1181),
    v1_18_R2(1182),
    v1_19_R1(1191),
    v1_19_R2(1192),
    v1_19_R3(1193), 
    v1_20_R1(1201), 
    v1_20_R2(1202), 
    v1_20_R3(1203);

    private static Version version;

    private final int id;

    Version(int id)
    {
        this.id = id;
    }

    public int id()
    {
        return id;
    }

    public static void init(JavaPlugin plugin)
    {
        new Metrics(plugin, 10);
    }

    /**
     * Determines if the current version meets or exceeds the specified version
     *
     * @param version The minimum version
     * @return true if the current version is at least the specified version, false otherwise.
     */
    public static boolean isAtLeast(Version version)
    {
        return get().id() >= version.id();
    }

    /**
     * Verifies if the current version is an upgrade over the specified version.
     *
     * @param version The minimum version
     * @return true if the current version is newer than the specified version, false otherwise.
     */
    public static boolean isNewerThan(Version version)
    {
        return get().id() > version.id();
    }

    public static Version get()
    {
        if (version != null)
            return version;

        try
        {
            version = Version.valueOf(Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3]);
        }
        catch (IllegalArgumentException ex)
        {
            version = Version.UNKNOWN;
        }
        
        return version;
    }
}