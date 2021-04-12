package dev.lone.fastnbt.nbt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class NFile<T> extends NCompound<T>
{
    private File file;
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
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
            this.writeLock = this.readWriteLock.writeLock();
            if (file.exists())
            {
                this.handle = (T) NBT.nbtStreamTools.read(new FileInputStream(file));
            }
            else
            {
                file.getParentFile().mkdirs();
                this.handle = (T) NBT.compound.newCompoundInstance();
            }
        }
    }

    public void save() throws IOException
    {
        try
        {
            this.writeLock.lock();
            NBT.nbtStreamTools.save(handle, new FileOutputStream(file));
        }
        finally
        {
            this.writeLock.unlock();
        }
    }
}
