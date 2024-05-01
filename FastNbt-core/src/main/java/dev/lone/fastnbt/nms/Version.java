package dev.lone.fastnbt.nms;

import org.bukkit.Bukkit;

/**
 * Protocol version.
 * https://wiki.vg/Protocol_version_numbers
 */
@SuppressWarnings({"unused"})
public enum Version
{
    UNKNOWN(Integer.MAX_VALUE),
    v1_15_R1(578),
    v1_16_R3(754),
    v1_17_R1(756),
    v1_18_R1(757),
    v1_18_R2(758),
    v1_19_R1(760),
    v1_19_R2(761),
    v1_19_R3(762),
    v1_20_R1(763),
    v1_20_R2(764),
    v1_20_R3(765),
    v1_20_R4(770);

    private static Version version;

    /**
     * Protocol ID.
     */
    public final int id;

    Version(int id)
    {
        this.id = id;
    }

    /**
     * Determines if the current version meets or exceeds the specified version
     *
     * @param version The minimum version
     * @return true if the current version is at least the specified version, false otherwise.
     */
    public static boolean isAtLeast(Version version)
    {
        return get().id >= version.id;
    }

    /**
     * Verifies if the current version is an upgrade over the specified version.
     *
     * @param version The minimum version
     * @return true if the current version is newer than the specified version, false otherwise.
     */
    public static boolean isNewerThan(Version version)
    {
        return get().id > version.id;
    }

    /**
     * Verifies if the current version is a lower version compared to the specified version.
     *
     * @param version The maximum version
     * @return true if the current version is lower than the specified version, false otherwise.
     */
    public static boolean isOlderThan(Version version)
    {
        return get().id < version.id;
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