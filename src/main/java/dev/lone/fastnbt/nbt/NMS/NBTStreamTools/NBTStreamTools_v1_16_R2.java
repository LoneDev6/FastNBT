package dev.lone.fastnbt.nbt.NMS.NBTStreamTools;

import net.minecraft.server.v1_16_R2.NBTCompressedStreamTools;
import net.minecraft.server.v1_16_R2.NBTTagCompound;
import org.jetbrains.annotations.NotNull;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class NBTStreamTools_v1_16_R2 implements INBTStreamTools<NBTTagCompound>
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
