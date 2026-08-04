package beer.devs.fastnbt.nms.nbt;

import beer.devs.fastnbt.nms.NMSImpl;
import org.bukkit.Bukkit;

@SuppressWarnings({"rawtypes", "unchecked"})
public final class DataFixerUtil
{
    private DataFixerUtil() {}

    /**
     * Updates raw server item data between two Minecraft data versions.
     *
     * @param nbt the server-internal item compound
     * @param fromVersion the data version that produced the compound
     * @param toVersion the target data version
     * @return the updated server-internal compound
     */
    public static Object fixUpRawItemData(Object nbt, int fromVersion, int toVersion)
    {
        return Holder.FIXER.fixItem(nbt, fromVersion, toVersion);
    }

    /**
     * Updates item data between two Minecraft data versions and records the target version.
     *
     * @param nbt the item compound to update
     * @param fromVersion the data version that produced the compound
     * @param toVersion the target data version
     * @return a wrapper around the updated item compound
     */
    public static NCompound fixUpItemData(NCompound nbt, int fromVersion, int toVersion)
    {
        return new NCompound(fixUpRawItemData(nbt.getInternal(), fromVersion, toVersion))
                .setInt("DataVersion", toVersion);
    }

    /**
     * Updates item data from a known source version to the current server version.
     *
     * @param nbt the item compound to update
     * @param fromVersion the data version that produced the compound
     * @return a wrapper around the updated item compound
     */
    public static NCompound fixUpItemData(NCompound nbt, int fromVersion)
    {
        return fixUpItemData(nbt, fromVersion, getCurrentVersion());
    }

    /**
     * Updates item data to the current server version using its {@code DataVersion} field.
     * No safe automatic conversion is possible when that field is absent.
     *
     * @param nbt the item compound containing an integer {@code DataVersion}
     * @return a wrapper around the updated item compound
     * @throws IllegalArgumentException when {@code DataVersion} is absent or invalid
     */
    public static NCompound fixUpItemData(NCompound nbt)
    {
        if (!nbt.hasKey("DataVersion"))
            throw new IllegalArgumentException("Cannot detect item version: DataVersion is missing.");
        int dataVersion = nbt.getInt("DataVersion");
        if (dataVersion <= 0)
            throw new IllegalArgumentException("Cannot detect item version: DataVersion must be a positive integer.");
        return fixUpItemData(nbt, dataVersion);
    }

    /** Returns the Minecraft data version used by the current Paper or Spigot server. */
    public static int getCurrentVersion()
    {
        return Bukkit.getUnsafe().getDataVersion();
    }

    private static final class Holder
    {
        private static final IDataFixer FIXER = NMSImpl.instantiate(IDataFixer.class);
    }
}
