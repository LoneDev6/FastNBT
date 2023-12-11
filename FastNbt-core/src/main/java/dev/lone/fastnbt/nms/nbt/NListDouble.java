package dev.lone.fastnbt.nms.nbt;

import org.jetbrains.annotations.Nullable;

@SuppressWarnings({"UnusedReturnValue", "unused", "unchecked"})
public class NListDouble extends NSafeTagList<Double>
{
    public NListDouble(Object internal)
    {
        super(internal);
    }

    public @Nullable Double get(int i)
    {
        if(i < 0 || i > size())
            return null;
        return handler.getDoubleAt(handle, i);
    }
}
