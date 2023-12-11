package dev.lone.fastnbt.nms.nbt;

import org.jetbrains.annotations.NotNull;
import dev.lone.fastnbt.nms.Implementation;
import dev.lone.fastnbt.nms.Version;
import dev.lone.fastnbt.nms.nbt.nms.INBTIO;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.NbtIo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@Implementation.CyclicDependency(type = INBTIO.class, version = Version.v1_20_R3)
@SuppressWarnings({"unused"})
public class NBTStreamTools_v1_20_R3 implements INBTIO<CompoundTag>
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
