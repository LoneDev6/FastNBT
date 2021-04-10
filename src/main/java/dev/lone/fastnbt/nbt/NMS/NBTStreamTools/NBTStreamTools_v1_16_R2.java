package dev.lone.fastnbt.nbt.NMS.NBTStreamTools;

import net.minecraft.server.v1_16_R2.NBTCompressedStreamTools;
import net.minecraft.server.v1_16_R2.NBTTagCompound;

import java.io.File;
import java.io.IOException;

public class NBTStreamTools_v1_16_R2 implements INBTStreamTools<NBTTagCompound>
{
    @Override
    public NBTTagCompound read(File file) throws IOException
    {
        return NBTCompressedStreamTools.a(file);
    }

    @Override
    public void save(NBTTagCompound nbt, File file) throws IOException
    {
        NBTCompressedStreamTools.a(nbt, file);
    }
}
