package dev.lone.fastnbt.nms.nbt;

import org.jetbrains.annotations.Nullable;

@SuppressWarnings({"UnusedReturnValue", "unused", "unchecked"})
public class NListFloat extends NSafeTagList<Float>
{
    public NListFloat(Object internal)
    {
        super(internal);
    }

    public @Nullable Float get(int i)
    {
        if(i < 0 || i > size())
            return null;
        return handler.getFloatAt(handle, i);
    }
}
