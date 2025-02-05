package beer.devs.fastnbt.nms.nbt;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public interface INbtIo<T>
{
    T read(FileInputStream inputStream) throws IOException;
    void save(T nbt, FileOutputStream outputStream) throws IOException;
}
