package beer.devs.fastnbt.nms.nbt;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public interface IDataFixer<T>
{
    /** Updates an item compound between two Minecraft data versions. */
    T fixItem(T nbt, int fromVersion, int toVersion);
}
