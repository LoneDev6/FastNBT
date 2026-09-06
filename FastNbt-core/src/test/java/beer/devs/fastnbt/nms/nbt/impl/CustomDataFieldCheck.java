package beer.devs.fastnbt.nms.nbt.impl;

import beer.devs.fastnbt.nms.Version;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

public final class CustomDataFieldCheck
{
    private static class Data
    {
        private final String tag = "live";
        private final String unrelated = "unrelated";
        private static final String STATIC = "static";
        private String mutable;
    }

    private static final class InheritedData extends Data {}

    private static final class InvalidAdapter
    {
        static final Field FIELD = CustomDataField.resolve(Data.class, String.class,
                "invalid-mapping", "Spigot 1.20.6", "InvalidAdapter");
    }

    @Test
    public void exactVersionMappings()
    {
        for (Version version : new Version[]{Version.v1_20_5, Version.v1_20_6, Version.v1_21,
                Version.v1_21_1, Version.v1_21_3})
            assertEquals("f", CustomDataField.mappedName(version, true));
        for (Version version : new Version[]{Version.v1_21_4, Version.v1_21_5, Version.v1_21_6,
                Version.v1_21_7, Version.v1_21_8})
            assertEquals("g", CustomDataField.mappedName(version, true));
        for (Version version : new Version[]{Version.v1_21_10, Version.v1_21_11})
            assertEquals("e", CustomDataField.mappedName(version, true));
        for (Version version : new Version[]{Version.v26_1_1, Version.v26_1_2, Version.v26_2})
            assertEquals("tag", CustomDataField.mappedName(version, true));
        for (Version version : Version.values())
            if (version.ordinal() >= Version.v1_20_5.ordinal())
                assertEquals("tag", CustomDataField.mappedName(version, false));
        assertNull(CustomDataField.mappedName(Version.UNKNOWN, true));
        assertNull(CustomDataField.mappedName(Version.v1_20_4, false));
    }

    @Test
    public void exactOwnerTypeAndInstanceField() throws Exception
    {
        Field field = CustomDataField.resolve(Data.class, String.class, "tag", "test", "adapter");
        assertEquals(Data.class, field.getDeclaringClass());
        assertEquals("live", field.get(new Data()));
        assertEquals("tag", field.getName());
        expectFailure(Data.class, Integer.class, "tag");
        expectFailure(Data.class, String.class, "STATIC");
        expectFailure(Data.class, String.class, "mutable");
        expectFailure(InheritedData.class, String.class, "tag");
        expectFailure(Data.class, String.class, "missing");
        expectFailure(Data.class, String.class, null);
    }

    private static void expectFailure(Class<?> owner, Class<?> type, String name)
    {
        IllegalStateException failure = assertThrows(IllegalStateException.class,
                () -> CustomDataField.resolve(owner, type, name, "test-server", "test-adapter"));
        assertTrue(failure.getMessage().contains("test-server"));
        assertTrue(failure.getMessage().contains("test-adapter"));
        assertTrue(failure.getMessage().contains("CustomData.tag"));
        assertTrue(failure.getMessage().contains(owner.getName()));
        assertTrue(failure.getMessage().contains("exact declared field"));
    }

    @Test
    public void invalidMappingFailsDuringInitialization()
    {
        ExceptionInInitializerError failure = assertThrows(ExceptionInInitializerError.class,
                () -> Class.forName(InvalidAdapter.class.getName()));
        assertTrue(failure.getCause() instanceof IllegalStateException);
        assertTrue(failure.getCause().getMessage().contains("Spigot 1.20.6"));
        assertTrue(failure.getCause().getMessage().contains("invalid-mapping"));
        assertThrows(NoClassDefFoundError.class, () -> Class.forName(InvalidAdapter.class.getName()));
    }
}
