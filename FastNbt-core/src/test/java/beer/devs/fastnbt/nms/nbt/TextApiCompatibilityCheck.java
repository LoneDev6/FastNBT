package beer.devs.fastnbt.nms.nbt;

import java.util.List;
import org.junit.Test;

public final class TextApiCompatibilityCheck
{
    @Test
    public void compatibility() throws ReflectiveOperationException
    {
        assert NItem.class.getMethod("setCustomName", String.class).isAnnotationPresent(Deprecated.class);
        assert NItem.class.getMethod("setCustomNameJson", String.class).getReturnType() == void.class;
        assert NItem.class.getMethod("getItemNameJson").getReturnType() == String.class;
        assert NItem.class.getMethod("setLoreJson", List.class).getReturnType() == void.class;
        assert NItem.class.getMethod("getLoreRawCopy").getReturnType() == List.class;
        assert NItem.class.getMethod("setLoreRaw", List.class).getReturnType() == void.class;
        assert NItem.class.getMethod("getLoreCopy").isAnnotationPresent(Deprecated.class);
        assert NItem.class.getMethod("setLore", List.class).isAnnotationPresent(Deprecated.class);
        assert NItem.class.getMethod("itemFromSnbt", String.class).getReturnType().getSimpleName().equals("ItemStack");

        assert IDataComponents.class.getMethod("getCustomNameJson", org.bukkit.inventory.ItemStack.class).isDefault();
        assert IDataComponents.class.getMethod("getItemNameJson", org.bukkit.inventory.ItemStack.class).isDefault();
        assert IDataComponents.class.getMethod("getLoreJson", org.bukkit.inventory.ItemStack.class).isDefault();
        assert IDataComponents.class.getMethod("setLoreJson", org.bukkit.inventory.ItemStack.class, List.class).isDefault();
        assert DataFixerUtil.class.getMethod("fixUpItemData", NCompound.class).getReturnType() == NCompound.class;
    }
}
