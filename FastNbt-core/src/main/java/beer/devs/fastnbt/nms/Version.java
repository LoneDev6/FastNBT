package beer.devs.fastnbt.nms;

import org.bukkit.Bukkit;

/**
 * Protocol version.
 * <a href="https://minecraft.wiki/w/Protocol_version">Minecraft Wiki</a>
 */
@SuppressWarnings({"unused"})
public enum Version
{
    UNKNOWN(Integer.MAX_VALUE, Integer.MAX_VALUE, ""),
    @Deprecated
    v1_15_R1(0, 578, "1.15.2"),
    @Deprecated
    v1_16_R3(10, 754, "1.16.5"),
    v1_17_R1(20, 756, "1.17.1"),
    v1_18_R1(30, 757, "1.18.1"),
    v1_18_R2(40, 758, "1.18.2"),
    v1_19_R1(50, 760, "1.19.2"),
    v1_19_R2(60, 761, "1.19.3"),
    v1_19_R3(70, 762, "1.19.4"),
    v1_20_R1(80, 763, "1.20.1"),
    v1_20_R2(90, 764, "1.20.2"),
    v1_20_R3(100, 765, "1.20.3"),
    v1_20_4(110, 765, "1.20.4"), // v1_20_R3
    v1_20_5(120, 766, "1.20.5"), // v1_20_R4
    v1_20_6(130, 766, "1.20.6"), // v1_20_R4
    v1_21(140, 767, "1.21"),
    v1_21_1(141, 767, "1.21.1"),
    v1_21_3(143, 768, "1.21.3"),
    v1_21_4(144, 769, "1.21.4"),
    v1_21_5(145, 770, "1.21.5");

    private static Version version;

    /**
     * Protocol ID.
     */
    public final int id;
    // Used internally by the plugin to identify the version chronologically,
    // because sometimes the Mojang protocol version doesn't increase.
    public final int protocol;
    public final String name;

    Version(int protocol, int id, String name)
    {
        this.id = id;
        this.protocol = protocol;
        this.name = name;
    }

    public static Version byName(String name)
    {
        for (Version value : values())
        {
            if(value.name.equals(name))
                return value;
        }
        return null;
    }

    /**
     * Determines if the current version meets or exceeds the specified version
     *
     * @param version The minimum version
     * @return true if the current version is at least the specified version, false otherwise.
     */
    public static boolean isAtLeast(Version version)
    {
        return get().protocol >= version.protocol;
    }

    /**
     * Verifies if the current version is an upgrade over the specified version.
     *
     * @param version The minimum version
     * @return true if the current version is newer than the specified version, false otherwise.
     */
    public static boolean isNewerThan(Version version)
    {
        return get().protocol > version.protocol;
    }

    /**
     * Verifies if the current version is a lower version compared to the specified version.
     *
     * @param version The maximum version
     * @return true if the current version is lower than the specified version, false otherwise.
     */
    public static boolean isOlderThan(Version version)
    {
        return get().protocol < version.protocol;
    }

    public static Version get()
    {
        if (version != null)
            return version;

        try
        {
            version = Version.byName(Bukkit.getServer().getBukkitVersion().split("-")[0]);
        }
        catch (Throwable ignored)
        {
            try
            {
                version = Version.valueOf(Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3]);
            }
            catch (IllegalArgumentException ex)
            {
                version = Version.UNKNOWN;
            }
        }
        
        return version;
    }
}