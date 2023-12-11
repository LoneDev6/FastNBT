package dev.lone.fastnbt.nms.nbt;

import org.jetbrains.annotations.Nullable;

@SuppressWarnings({"UnusedReturnValue", "unused", "unchecked"})
public class NListString extends NSafeTagList<String>
{
    public NListString(Object internal)
    {
        super(internal);
    }

    public @Nullable String get(int i)
    {
        if(i < 0 || i > size())
            return null;
        return handler.getStringAt(handle, i);
    }
}
