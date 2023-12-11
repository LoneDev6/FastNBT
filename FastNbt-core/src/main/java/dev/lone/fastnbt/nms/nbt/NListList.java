package dev.lone.fastnbt.nms.nbt;

import org.jetbrains.annotations.Nullable;

@SuppressWarnings({"UnusedReturnValue", "unused"})
public class NListList extends NSafeTagList<NRawList>
{
    public NListList(Object internal)
    {
        super(internal);
    }

    public @Nullable NRawList get(int i)
    {
        if(i < 0 || i > size())
            return null;
        return getListAt(i);
    }
}
