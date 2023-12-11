package dev.lone.fastnbt.nms.nbt;

import org.jetbrains.annotations.Nullable;

@SuppressWarnings({"UnusedReturnValue", "unused", "unchecked"})
public class NListShort extends NSafeTagList<Short>
{
    public NListShort(Object internal)
    {
        super(internal);
    }

    public @Nullable Short get(int i)
    {
        if(i < 0 || i > size())
            return null;
        return handler.getShortAt(handle, i);
    }
}
