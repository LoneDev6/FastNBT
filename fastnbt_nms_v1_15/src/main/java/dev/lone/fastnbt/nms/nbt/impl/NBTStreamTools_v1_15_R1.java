package dev.lone.fastnbt.nms.nbt.impl;

import org.jetbrains.annotations.NotNull;
import dev.lone.fastnbt.nms.Implementation;
import dev.lone.fastnbt.nms.Version;
import dev.lone.fastnbt.nms.nbt.nms.INBTStreamTools;
import net.minecraft.server.v1_15_R1.NBTCompressedStreamTools;
import net.minecraft.server.v1_15_R1.NBTTagCompound;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@Implementation.CyclicDependency(type = INBTStreamTools.class, version = Version.v1_15_R1)
@SuppressWarnings({"unused"})
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
