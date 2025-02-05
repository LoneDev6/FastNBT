package beer.devs.fastnbt.nms.nbt.impl;

import beer.devs.fastnbt.nms.nbt.INbtIo;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.NbtIo;
import org.jetbrains.annotations.NotNull;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@SuppressWarnings({"unused"})
public class NbtIo_v1_21_1 implements INbtIo<CompoundTag>
{
    @Override
    public CompoundTag read(FileInputStream inputStream) throws IOException
    {
        try (inputStream)
        {
            return NbtIo.readCompressed(inputStream, NbtAccounter.unlimitedHeap());
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
