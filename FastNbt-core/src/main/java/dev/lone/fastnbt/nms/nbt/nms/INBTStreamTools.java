package dev.lone.fastnbt.nms.nbt.nms;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public interface INBTStreamTools<T>
{
    T read(FileInputStream inputStream) throws IOException;
    void save(T nbt, FileOutputStream outputStream) throws IOException;
}
