package beer.devs.fastnbt.nms;

import org.bukkit.Bukkit;

/**
 * Protocol version.
 * <a href="https://minecraft.wiki/w/Protocol_version">Minecraft Wiki</a>
 * "Each Minecraft build since 18w47b specifies its current protocol version in the version.json file"
 */
@SuppressWarnings({"unused"})
public enum Version
{
    UNKNOWN(Integer.MAX_VALUE, ""),
    @Deprecated
    v1_15_R1(578, "1.15.2"),
    @Deprecated
    v1_16_R3(754, 6, "1.16.5"),
    v1_17_R1(756, 7, "1.17.1"),
    v1_18_R1(757, 8, "1.18.1"),
    v1_18_R2(758, 8, "1.18.2"),
    v1_19_R1(760, 11, "1.19.2"),
    v1_19_R2(761, 12, "1.19.3"),
    v1_19_R3(762, 13, "1.19.4"),
    v1_20_R1(763, 14, "1.20.1"),
    v1_20_R2(764, 16, "1.20.2"),
    v1_20_R3(765, 22, "1.20.3"),
    v1_20_4(765, 22, "1.20.4"), // v1_20_R3
    v1_20_5(766, 32, "1.20.5"), // v1_20_R4
    v1_20_6(766, 32, "1.20.6"), // v1_20_R4
    v1_21(767, 34, "1.21"),
    v1_21_1(767, 34, "1.21.1"),
    v1_21_3(768, 42, "1.21.3"),
    v1_21_4(769, 46, "1.21.4"),
    v1_21_5(770, 55, "1.21.5"),
    v1_21_6(771, 63, "1.21.6"),
    v1_21_7(772, 64, "1.21.7"),
    v1_21_8(772, 64, "1.21.8"),
    v1_21_10(773, 69, "1.21.10"),
    v1_21_11(774, 75, "1.21.11"),
    v26_1_1(775, 75, "26.1.1"),
    v26_1_2(775, 84, "26.1.2"),
    v26_2(776, 84, "26.2")
    ;

    private static Version version;

    /**
     * Protocol ID.
     */
    private final int protocol;
    private final String name;
    private final int packVersion;

    Version(int protocol, String name)
    {
        this(protocol, 1, name);
    }

    Version(int protocol, int packVersion, String name)
    {
        this.protocol = protocol;
        this.name = name;
        this.packVersion = packVersion;
    }

    /**
     * Gets the ordinal value of this version.
     *
     * @return the ordinal value
     */
    public int getOrdinal()
    {
        return ordinal();
    }

    /**
     * Gets the protocol version number.
     *
     * @return the protocol version number
     */
    public int getProtocol()
    {
        return protocol;
    }

    /**
     * Gets the version name.
     *
     * @return the version name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Gets the pack version number.
     *
     * @return the pack version number
     */
    public int getPackVersion()
    {
        return packVersion;
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
        return get().ordinal() >= version.ordinal();
    }

    /**
     * Verifies if the current version is an upgrade over the specified version.
     *
     * @param version The minimum version
     * @return true if the current version is newer than the specified version, false otherwise.
     */
    public static boolean isNewerThan(Version version)
    {
        return get().ordinal() > version.ordinal();
    }

    /**
     * Verifies if the current version is a lower version compared to the specified version.
     *
     * @param version The maximum version
     * @return true if the current version is lower than the specified version, false otherwise.
     */
    public static boolean isOlderThan(Version version)
    {
        return get().ordinal() < version.ordinal();
    }

    public static Version get()
    {
        if (version != null)
            return version;

        try
        {
            version = Version.byName(Bukkit.getServer().getVersion().split("-")[0]);
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
