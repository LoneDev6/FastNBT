package dev.lone.fastnbt.nms.nbt;

public enum NbtType
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

    NbtType(int i)
    {
        id = i;
    }

    public final int id;

    public static NbtType byId(byte id)
    {
        for (NbtType value : NbtType.values())
        {
            if(value.id == id)
                return value;
        }
        return UNKNOWN;
    }
}