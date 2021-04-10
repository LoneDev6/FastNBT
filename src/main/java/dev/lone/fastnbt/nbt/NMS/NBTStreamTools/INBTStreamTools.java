package dev.lone.fastnbt.nbt.NMS.NBTStreamTools;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface INBTStreamTools<T>
{
    T read(InputStream stream) throws IOException;
    void write(T nbt, OutputStream stream) throws IOException;
}
