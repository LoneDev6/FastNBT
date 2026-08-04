package beer.devs.fastnbt.nms.nbt;

import java.io.*;
import java.nio.file.Files;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@SuppressWarnings({"unchecked", "unused"})
public class NFile extends NCompound
{
    protected File file;
    private final Lock writeLock;

    public NFile(File file) throws IOException
    {
        super();
        if (file == null)
            throw new NullPointerException("File can't be null!");

        this.file = file;
        this.writeLock = new ReentrantReadWriteLock().writeLock();
        if (file.exists())
        {
            try (FileInputStream inputStream = new FileInputStream(file))
            {
                this.handle = NBT.streamTools.read(inputStream);
            }
            return;
        }

        File parent = file.getParentFile();
        if (parent != null)
            Files.createDirectories(parent.toPath());
        this.handle = NBT.compound.newInstance();
    }

    public File getFile()
    {
        return file;
    }

    public void save() throws IOException
    {
        try
        {
            this.writeLock.lock();
            try (FileOutputStream outputStream = new FileOutputStream(file))
            {
                NBT.streamTools.save(handle, outputStream);
            }
        }
        catch (FileNotFoundException ignored) {} // File deleted by another plugin or manually.
        finally
        {
            this.writeLock.unlock();
        }
    }
}
