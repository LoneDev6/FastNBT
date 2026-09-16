package beer.devs.fastnbt.nms;

import org.junit.Test;

import java.util.Arrays;
import java.util.EnumSet;

import static org.junit.Assert.assertEquals;

public final class VersionCheck
{
    @Test
    @SuppressWarnings("deprecation")
    public void configuredAdaptersMatchSupportedVersions()
    {
        EnumSet<Version> expected = EnumSet.allOf(Version.class);
        // Historical unsupported versions and aliases resolved by NMSImpl.
        expected.removeAll(Arrays.asList(Version.UNKNOWN, Version.v1_15_R1, Version.v1_16_R3,
                Version.v1_18_R1, Version.v1_20_5, Version.v1_21));
        EnumSet<Version> configured = EnumSet.noneOf(Version.class);
        for (String name : System.getProperty("fastnbt.adapterVersions").split(","))
            configured.add(Version.valueOf(name));
        assertEquals(expected, configured);
    }

    @Test
    @SuppressWarnings("deprecation")
    public void protocolAndResourcePackFormatsMatchOfficialClients()
    {
        // Verified against version.json inside all 28 official Mojang client JARs.
        // pack_version.resource (older clients), resource_major (26.x), and protocol_version.
        assertOfficialVersion(Version.v1_15_R1, 578, 5);
        assertOfficialVersion(Version.v1_16_R3, 754, 6);
        assertOfficialVersion(Version.v1_17_R1, 756, 7);
        assertOfficialVersion(Version.v1_18_R1, 757, 8);
        assertOfficialVersion(Version.v1_18_R2, 758, 8);
        assertOfficialVersion(Version.v1_19_R1, 760, 9);
        assertOfficialVersion(Version.v1_19_R2, 761, 12);
        assertOfficialVersion(Version.v1_19_R3, 762, 13);
        assertOfficialVersion(Version.v1_20_R1, 763, 15);
        assertOfficialVersion(Version.v1_20_R2, 764, 18);
        assertOfficialVersion(Version.v1_20_R3, 765, 22);
        assertOfficialVersion(Version.v1_20_4, 765, 22);
        assertOfficialVersion(Version.v1_20_5, 766, 32);
        assertOfficialVersion(Version.v1_20_6, 766, 32);
        assertOfficialVersion(Version.v1_21, 767, 34);
        assertOfficialVersion(Version.v1_21_1, 767, 34);
        assertOfficialVersion(Version.v1_21_3, 768, 42);
        assertOfficialVersion(Version.v1_21_4, 769, 46);
        assertOfficialVersion(Version.v1_21_5, 770, 55);
        assertOfficialVersion(Version.v1_21_6, 771, 63);
        assertOfficialVersion(Version.v1_21_7, 772, 64);
        assertOfficialVersion(Version.v1_21_8, 772, 64);
        assertOfficialVersion(Version.v1_21_10, 773, 69);
        assertOfficialVersion(Version.v1_21_11, 774, 75);
        assertOfficialVersion(Version.v26_1_1, 775, 84);
        assertOfficialVersion(Version.v26_1_2, 775, 84);
        assertOfficialVersion(Version.v26_2, 776, 88);
        assertOfficialVersion(Version.v26_3, 777, 97);
    }

    private static void assertOfficialVersion(Version version, int protocol, int resourceFormat)
    {
        assertEquals(version.getName() + " protocol", protocol, version.getProtocol());
        assertEquals(version.getName() + " resource pack", resourceFormat, version.getPackVersion());
    }

    @Test
    public void detection()
    {
        assert Version.byName("26.2") == Version.v26_2;
        assert Version.byName("26.2-Paper-123") == Version.v26_2;
        assert Version.byName("4645-Spigot-8db49a2-c4d1107 (MC: 26.2)") == Version.v26_2;
        assert Version.byName("26.3") == Version.v26_3;
        assert Version.byName("26.3-Paper-3") == Version.v26_3;
        assert Version.byName("4650-Spigot-766de51-f48060a (MC: 26.3)") == Version.v26_3;
        assert Version.byName("1.20.6-R0.1-SNAPSHOT") == Version.v1_20_6;
        assert Version.byName("99.9") == Version.UNKNOWN;
        assert Version.byName(null) == Version.UNKNOWN;
    }

    @Test
    public void unknownIsNotComparable()
    {
        assert Version.get() == Version.UNKNOWN;
        assert !Version.isAtLeast(Version.v1_15_R1);
        assert !Version.isNewerThan(Version.v1_15_R1);
        assert !Version.isOlderThan(Version.v1_15_R1);
        assert !Version.isAtLeast(Version.UNKNOWN);
        assert !Version.isNewerThan(Version.UNKNOWN);
        assert !Version.isOlderThan(Version.UNKNOWN);
    }
}
