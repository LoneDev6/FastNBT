package dev.lone.fastnbt.nms.nbt.nms;

import org.jetbrains.annotations.Nullable;

public interface IListTag<ListTag, Tag, CompoundTag>
{
    ListTag newNmsInstance();
    boolean isEmpty(ListTag list);
    @Nullable
    CompoundTag getCompoundAt(ListTag list, int i);
    @Nullable
    ListTag getListAt(ListTag list, int i);
    short getShortAt(ListTag list, int i);
    int getIntAt(ListTag list, int i);
    int @Nullable [] getIntArrayAt(ListTag list, int i);
    long @Nullable [] getLongArrayAt(ListTag list, int i);
    double getDoubleAt(ListTag list, int i);
    float getFloatAt(ListTag list, int i);
    String getStringAt(ListTag list, int i);
    int size(ListTag list);
    Tag get(ListTag list, int i);
    void set(ListTag list, int i, Object any);
    void add(ListTag list, int i, @Nullable Object any);
    Tag remove(ListTag list, int i);
    String toString(ListTag list);
}
