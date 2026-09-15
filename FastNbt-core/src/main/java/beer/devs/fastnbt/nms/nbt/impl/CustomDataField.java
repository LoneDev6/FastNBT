package beer.devs.fastnbt.nms.nbt.impl;

import beer.devs.fastnbt.nms.NMSImpl;
import beer.devs.fastnbt.nms.Version;
import org.bukkit.Bukkit;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/** Initialization-only member mapping shared by the custom-data adapters. */
final class CustomDataField
{
    private CustomDataField() {}

    static Field resolve(Class<?> owner, Class<?> type, Class<?> adapter)
    {
        Version version = Version.get();
        String name = mappedName(version, NMSImpl.isSpigotMapped());
        return resolve(owner, type, name, Bukkit.getVersion(), adapter.getName());
    }

    static String mappedName(Version version, boolean spigotMapped)
    {
        // Exact CustomData.tag entries in the adapters' paperweight reobfMappings.tiny,
        // verified against the corresponding local Spigot runtime artifacts.
        switch (version)
        {
            case v1_20_5:
            case v1_20_6:
            case v1_21:
            case v1_21_1:
            case v1_21_3:
                return spigotMapped ? "f" : "tag";
            case v1_21_4:
            case v1_21_5:
            case v1_21_6:
            case v1_21_7:
            case v1_21_8:
                return spigotMapped ? "g" : "tag";
            case v1_21_10:
            case v1_21_11:
                return spigotMapped ? "e" : "tag";
            case v26_1_1:
            case v26_1_2:
            case v26_2:
            case v26_3:
                return "tag";
            default:
                return null;
        }
    }

    static Field resolve(Class<?> owner, Class<?> type, String name, String server, String adapter)
    {
        try
        {
            if (name == null)
                throw new NoSuchFieldException("No custom-data mapping for this version");
            Field field = owner.getDeclaredField(name);
            if (field.getType() != type || Modifier.isStatic(field.getModifiers())
                    || !Modifier.isFinal(field.getModifiers()))
                throw new NoSuchFieldException("Expected a final instance field of type " + type.getName());
            field.setAccessible(true);
            return field;
        }
        catch (ReflectiveOperationException | RuntimeException ex)
        {
            throw new IllegalStateException("FastNBT initialization failed: server=" + server
                    + ", adapter=" + adapter + ", member=CustomData.tag (live custom NBT)"
                    + ", owner=" + owner.getName() + ", type=" + type.getName()
                    + ", strategy=exact declared field, mapped name=" + name, ex);
        }
    }
}
