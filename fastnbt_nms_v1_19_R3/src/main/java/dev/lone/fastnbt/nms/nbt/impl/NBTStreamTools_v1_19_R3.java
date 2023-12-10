package dev.lone.fastnbt.nms.nbt.impl;

import dev.lone.fastnbt.nms.Implementation;
import dev.lone.fastnbt.nms.Version;
import dev.lone.fastnbt.nms.nbt.nms.ICompound;
import dev.lone.fastnbt.nms.nbt.nms.INBTStreamTools;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import org.jetbrains.annotations.NotNull;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@Implementation.CyclicDependency(type = INBTStreamTools.class, version = Version.v1_19_R3)
@SuppressWarnings({"unused"})
public class NBTStreamTools_v1_19_R3 implements INBTStreamTools<CompoundTag>
{
    @Override
    public CompoundTag read(FileInputStream inputStream) throws IOException
    {
        try (inputStream)
        {
            return NbtIo.readCompressed(inputStream);
        }
    }

    @Override
    public void save(@NotNull CompoundTag nbt, FileOutputStream outputStream) throws IOException
    {
        try (outputStream)
        {
            NbtIo.writeCompressed(nbt, outputStream);
        }
    }
}