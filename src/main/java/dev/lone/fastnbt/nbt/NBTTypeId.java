package dev.lone.fastnbt.nbt;

public enum NBTTypeId {
    End(0),
    Byte(1),
    Short(2),
    Int(3),
    Long(4),
    Float(5),
    Double(6),
    ByteArray(7),
    IntArray(11),
    String(8),
    List(9),
    Compound(10);

    NBTTypeId(int i) {
        id = i;
    }

    public final int id;
}