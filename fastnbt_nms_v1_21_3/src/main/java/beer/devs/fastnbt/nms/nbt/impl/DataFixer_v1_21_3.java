package beer.devs.fastnbt.nms.nbt.impl;

import beer.devs.fastnbt.nms.nbt.IDataFixer;
import com.mojang.serialization.Dynamic;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.util.datafix.DataFixers;
import net.minecraft.util.datafix.fixes.References;

public class DataFixer_v1_21_3 implements IDataFixer<CompoundTag>
{
    @Override
    public CompoundTag fixItem(CompoundTag nbt, int fromVersion, int toVersion)
    {
        return (CompoundTag) DataFixers.getDataFixer()
                .update(References.ITEM_STACK, new Dynamic<>(NbtOps.INSTANCE, nbt), fromVersion, toVersion)
                .getValue();
    }
}
