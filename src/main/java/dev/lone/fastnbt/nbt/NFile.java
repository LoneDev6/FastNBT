package dev.lone.fastnbt.nbt;

import dev.lone.LoneLibs.nbt.nbtapi.NBTReflectionUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class NFile<T> extends NCompound<T>
{
    private File file;

    public NFile(File file) throws IOException
    {
        super();
        if (file == null)
        {
            throw new NullPointerException("File can't be null!");
        }
        else
        {
            this.file = file;
            if (file.exists())
            {
                FileInputStream inputStream = new FileInputStream(file);
                try
                {
                    this.handle = (T) NBT.getNbtStreamTools().read(inputStream);
                } catch (Exception e)
                {
                    inputStream.close();
                    throw new IOException("Error reading the NBT file: " + e.getMessage());
                }
                finally
                {
                    inputStream.close();
                }
            }
        }
    }

    public void save() throws IOException {
        try {
            this.getWriteLock().lock();
            if (!this.file.exists()) {
                this.file.getParentFile().mkdirs();
                if (!this.file.createNewFile()) {
                    throw new IOException("Unable to create file at " + this.file.getAbsolutePath());
                }
            }

            FileOutputStream outStream = new FileOutputStream(this.file);
            NBTReflectionUtil.writeNBT(this.nbt, outStream);
        } finally {
            this.getWriteLock().unlock();
        }

    }
}
