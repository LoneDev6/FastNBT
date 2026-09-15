package beer.devs.fastnbt.nms;

import org.junit.Test;

public final class VersionCheck
{
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
