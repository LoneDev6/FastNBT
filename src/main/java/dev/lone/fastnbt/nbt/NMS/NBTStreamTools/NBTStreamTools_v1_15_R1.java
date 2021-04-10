package dev.lone.fastnbt.nbt.NMS.NBTStreamTools;

import net.minecraft.server.v1_15_R1.NBTCompressedStreamTools;
import net.minecraft.server.v1_15_R1.NBTTagCompound;

import java.io.*;

public class NBTStreamTools_v1_15_R1 implements INBTStreamTools<NBTTagCompound>
{
    @Override
    public NBTTagCompound read(File file) throws IOException
    {
        FileInputStream fileinputstream = new FileInputStream(file);
        Throwable throwable = null;

        NBTTagCompound nbttagcompound;
        try
        {
            nbttagcompound = NBTCompressedStreamTools.a(fileinputstream);
        } catch (Throwable var12)
        {
            throwable = var12;
            throw var12;
        } finally
        {
            if (fileinputstream != null)
            {
                if (throwable != null)
                {
                    try
                    {
                        fileinputstream.close();
                    } catch (Throwable var11)
                    {
                        throwable.addSuppressed(var11);
                    }
                }
                else
                {
                    fileinputstream.close();
                }
            }
        }
        return nbttagcompound;
    }

    @Override
    public void save(NBTTagCompound nbt, File file) throws IOException
    {
        FileOutputStream fileoutputstream = new FileOutputStream(file);
        Throwable throwable = null;
        try
        {
            NBTCompressedStreamTools.a(nbt, fileoutputstream);
        } catch (Throwable var12)
        {
            throwable = var12;
            throw var12;
        } finally
        {
            if (fileoutputstream != null)
            {
                if (throwable != null)
                {
                    try
                    {
                        fileoutputstream.close();
                    } catch (Throwable var11)
                    {
                        throwable.addSuppressed(var11);
                    }
                }
                else
                {
                    fileoutputstream.close();
                }
            }
        }
    }
}
