package dev.lone.fastnbt.nbt.NMS.NBTStreamTools;

import net.minecraft.server.v1_15_R1.NBTCompressedStreamTools;
import net.minecraft.server.v1_15_R1.NBTTagCompound;
import org.jetbrains.annotations.NotNull;

import java.io.*;

public class NBTStreamTools_v1_15_R1 implements INBTStreamTools<NBTTagCompound>
{
    @Override
    public NBTTagCompound read(FileInputStream inputStream) throws IOException
    {
        try
        {
            return NBTCompressedStreamTools.a(inputStream);
        } finally
        {
            inputStream.close();
        }
    }

    @Override
    public void save(@NotNull NBTTagCompound nbt, FileOutputStream outputStream) throws IOException
    {
        try
        {
            NBTCompressedStreamTools.a(nbt, outputStream);
        } finally
        {
            outputStream.close();
        }
    }
}
