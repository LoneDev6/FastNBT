package beer.devs.fastnbt.nms.nbt.impl;

import net.minecraft.nbt.CompoundTag;

import java.util.Optional;
import java.util.UUID;

public class NBTUtilsModern_v1_21_8
{
    public static UUID getUUID(CompoundTag tag, String key)
    {
        Optional<int[]> val = tag.getIntArray(key);
        if(val.isEmpty())
            return null;

        int[] uuidArray = val.get();
        if (uuidArray.length == 4)
        {
            long mostSigBits = ((long) uuidArray[0] << 32) | (uuidArray[1] & 0xFFFFFFFFL);
            long leastSigBits = ((long) uuidArray[2] << 32) | (uuidArray[3] & 0xFFFFFFFFL);
            return new UUID(mostSigBits, leastSigBits);
        }

        throw new IllegalArgumentException("Invalid UUID array length: " + uuidArray.length);
    }

    public static void putUUID(CompoundTag tag, String key, UUID uuid)
    {
        long mostSigBits = uuid.getMostSignificantBits();
        long leastSigBits = uuid.getLeastSignificantBits();
        int[] uuidArray = new int[]{
                (int) (mostSigBits >> 32),
                (int) mostSigBits,
                (int) (leastSigBits >> 32),
                (int) leastSigBits
        };
        tag.putIntArray(key, uuidArray);
    }
}
