package beer.devs.fastnbt.nms.nbt;

import beer.devs.fastnbt.ApiMetrics;
import beer.devs.fastnbt.nms.NMSImpl;
import beer.devs.fastnbt.nms.Version;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("rawtypes")
public class NBT
{
    public static ICompoundTag compound;
    public static ICraftItemStack item;
    public static IListTag list;
    public static INbtIo streamTools;
    public static IDataComponents dataComponents;

    static
    {
        initMetrics();

        try
        {
            compound = NMSImpl.instantiate(ICompoundTag.class);
            item = NMSImpl.instantiate(ICraftItemStack.class);
            list = NMSImpl.instantiate(IListTag.class);
            streamTools = NMSImpl.instantiate(INbtIo.class);
            if(Version.isNewerThan(Version.v1_20_4))
                dataComponents = NMSImpl.instantiate(IDataComponents.class);
        }
        catch (Throwable ex)
        {
            Bukkit.getLogger().severe("This server is not compatible with FastNBT. Server: " + Bukkit.getVersion() + " (NMS: " + Version.get() + ")");
            ex.printStackTrace();
            Bukkit.shutdown();
        }
    }

    public static boolean isInstanceOfCraftItemStack(ItemStack itemStack)
    {
        return item.isInstanceOfCraftItemStack(itemStack);
    }

    private static void initMetrics()
    {
        try
        {
            new ApiMetrics("FastNbt", "1.4.5", 10);
            return;
        }
        catch (Throwable ex)
        {
            ex.printStackTrace();
        }
        Bukkit.getServer().getLogger().info("[FastNbt] Failed to initialize metrics.");
    }
}
