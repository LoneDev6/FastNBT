package dev.lone.fastnbt.nbt.NMS.NBTStreamTools;

import java.io.File;
import java.io.IOException;

public interface INBTStreamTools<T>
{
    T read(File file) throws IOException;
    void save(T nbt, File file) throws IOException;
}
