package dev.lone.fastnbt.nms.nbt;

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
        this.file = file;
        if (file == null)
        {
            throw new NullPointerException("File can't be null!");
        }
        else
        {
            this.writeLock = new ReentrantReadWriteLock().writeLock();
            if (file.exists())
            {
                this.handle = NBT.streamTools.read(new FileInputStream(file));
            }
            else
            {
                Files.createDirectories(file.getParentFile().toPath());
                this.handle = NBT.compound.newInstance();
            }
        }
    }

    public void save() throws IOException
    {
        try
        {
            this.writeLock.lock();
            NBT.streamTools.save(handle, new FileOutputStream(file));
        }
        catch (FileNotFoundException ignored) { } // File deleted by another plugin or manually.
        finally
        {
            this.writeLock.unlock();
        }
    }
}
