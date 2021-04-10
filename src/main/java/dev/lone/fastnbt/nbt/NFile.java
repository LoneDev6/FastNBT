package dev.lone.fastnbt.nbt;

import java.io.File;
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
            try
            {
                this.writeLock.lock();
                if (!file.exists())
                {
                    file.getParentFile().mkdirs();
                    if (!file.createNewFile()) {
                        throw new IOException("Unable to create file at " + file.getAbsolutePath());
                    }
                    this.handle = (T) new NCompound().getInternal();
                    NBT.getNbtStreamTools().save(this.handle, file);
                }
                else
                {
                    this.handle = (T) NBT.getNbtStreamTools().read(file);
                }
            } catch (Exception e)
            {
                throw e;
            } finally
            {
                this.writeLock.unlock();
            }
        }
    }

    public void save() throws IOException
    {
        try
        {
            this.writeLock.lock();
            NBT.getNbtStreamTools().save(handle, file);
        } catch (IOException e)
        {
            throw e;
        } finally
        {
            this.writeLock.unlock();
        }
    }
}
