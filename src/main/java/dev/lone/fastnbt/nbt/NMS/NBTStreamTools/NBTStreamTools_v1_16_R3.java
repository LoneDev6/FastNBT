package dev.lone.fastnbt.nbt.NMS.NBTStreamTools;

import net.minecraft.server.v1_16_R3.NBTCompressedStreamTools;
import net.minecraft.server.v1_16_R3.NBTTagCompound;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class NBTStreamTools_v1_16_R3 implements INBTStreamTools<NBTTagCompound>
{
    @Override
    public NBTTagCompound read(InputStream stream) throws IOException
    {
        return NBTCompressedStreamTools.a(stream);
    }

    @Override
    public void write(NBTTagCompound nbt, OutputStream stream) throws IOException
    {
        NBTCompressedStreamTools.a(nbt, stream);
    }
}
