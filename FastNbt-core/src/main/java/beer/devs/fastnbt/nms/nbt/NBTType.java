package beer.devs.fastnbt.nms.nbt;

public enum NBTType
{
    End(0),
    Byte(1),
    Short(2),
    Int(3),
    Long(4),
    Float(5),
    Double(6),
    ByteArray(7),
    String(8),
    List(9),
    Compound(10),
    IntArray(11),
    LongArray(12),
    AnyNumeric(99),
    UNKNOWN(-1337);

    NBTType(int i)
    {
        id = i;
    }

    public final int id;

    public static NBTType byId(byte id)
    {
        for (NBTType value : NBTType.values())
        {
            if(value.id == id)
                return value;
        }
        return UNKNOWN;
    }
}