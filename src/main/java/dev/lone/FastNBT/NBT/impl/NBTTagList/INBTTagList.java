package dev.lone.FastNBT.NBT.impl.NBTTagList;

public interface INBTTagList<NBTTAGLIST, NBTBASE, NBTCOMPOUND>
{
    boolean isEmpty(NBTTAGLIST list);
    NBTCOMPOUND getCompoundAt(NBTTAGLIST list, int i);
    NBTTAGLIST getNBTTagListAt(NBTTAGLIST list, int i);
    short getShortAt(NBTTAGLIST list, int i);
    int getIntAt(NBTTAGLIST list, int i);
    int[] getIntArrayAt(NBTTAGLIST list, int i);
    double getDoubleAt(NBTTAGLIST list, int i);
    float getFloatAt(NBTTAGLIST list, int i);
    String getStringAt(NBTTAGLIST list, int i);
    int size(NBTTAGLIST list);
    NBTBASE get(NBTTAGLIST list, int i);
    NBTBASE set(NBTTAGLIST list, int i, Object nbtBase);
    void add(NBTTAGLIST list, int i, Object any);
    NBTBASE remove(NBTTAGLIST list, int i);
    String toString(NBTTAGLIST list);
}
