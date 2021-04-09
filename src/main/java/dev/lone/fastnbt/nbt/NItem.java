package dev.lone.fastnbt.nbt;

import org.bukkit.inventory.ItemStack;

public class NItem extends NCompound<ItemStack>
{
    public NItem(ItemStack itemStack)
    {
        if (itemStack.getClass().getSuperclass() == Object.class)
            this.handle = NBT.item().convert(itemStack);
        else
            this.handle = itemStack;
        this.handler = NBT.item();
    }
}
