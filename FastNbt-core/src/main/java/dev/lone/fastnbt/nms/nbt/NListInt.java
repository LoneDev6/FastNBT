package dev.lone.fastnbt.nms.nbt;

import org.jetbrains.annotations.Nullable;

@SuppressWarnings({"UnusedReturnValue", "unused", "unchecked"})
public class NListInt extends NSafeTagList<Integer>
{
    public NListInt(Object internal)
    {
        super(internal);
    }

    public @Nullable Integer get(int i)
    {
        if(i < 0 || i > size())
            return null;
        return handler.getIntAt(handle, i);
    }
}
