package beer.devs.fastnbt.nms.nbt.impl;

import beer.devs.fastnbt.nms.nbt.IDataComponents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.ItemLore;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.craftbukkit.util.CraftChatMessage;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unused"})
public class DataComponents_v26_1_2 implements IDataComponents
{
    @Override
    public Object getCustomName(ItemStack bukkitItemStack)
    {
        CraftItemStack craftItemStack = CraftItemStack_v26_1_2.castToCraftItemStack(bukkitItemStack);
        return CraftItemStack_v26_1_2.getHandle(craftItemStack).get(DataComponents.CUSTOM_NAME);
    }

    @Override
    public String getCustomNameJson(ItemStack bukkitItemStack)
    {
        CraftItemStack craftItemStack = CraftItemStack_v26_1_2.castToCraftItemStack(bukkitItemStack);
        Component component = CraftItemStack_v26_1_2.getHandle(craftItemStack).get(DataComponents.CUSTOM_NAME);
        if(component == null)
            return null;
        return CraftChatMessage.toJSON(component);
    }

    @Override
    public Object getItemName(ItemStack bukkitItemStack)
    {
        CraftItemStack craftItemStack = CraftItemStack_v26_1_2.castToCraftItemStack(bukkitItemStack);
        return CraftItemStack_v26_1_2.getHandle(craftItemStack).get(DataComponents.ITEM_NAME);
    }

    @Override
    public List<Object> getLore(ItemStack bukkitItemStack)
    {
        CraftItemStack craftItemStack = CraftItemStack_v26_1_2.castToCraftItemStack(bukkitItemStack);
        ItemLore lore = CraftItemStack_v26_1_2.getHandle(craftItemStack).get(DataComponents.LORE);
        if(lore == null)
            return null;
        return new ArrayList<>(lore.lines());
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setLore(ItemStack bukkitItemStack, List<?> lore)
    {
        CraftItemStack craftItemStack = CraftItemStack_v26_1_2.castToCraftItemStack(bukkitItemStack);
        if(lore == null)
            CraftItemStack_v26_1_2.getHandle(craftItemStack).remove(DataComponents.LORE);
        else
            CraftItemStack_v26_1_2.getHandle(craftItemStack).set(DataComponents.LORE, new ItemLore((List<Component>) lore));
    }

    @Override
    public boolean copyAttributeModifiers(ItemStack bukkitItemStackSource, ItemStack bukkitItemStackDestination)
    {
        CraftItemStack a = CraftItemStack_v26_1_2.castToCraftItemStack(bukkitItemStackSource);
        ItemAttributeModifiers modifiersA = CraftItemStack_v26_1_2.getHandle(a).get(DataComponents.ATTRIBUTE_MODIFIERS);
        if(modifiersA == null)
            return false;

        CraftItemStack b = CraftItemStack_v26_1_2.castToCraftItemStack(bukkitItemStackDestination);
        CraftItemStack_v26_1_2.getHandle(b).set(DataComponents.ATTRIBUTE_MODIFIERS, modifiersA);

        return true;
    }

    @Override
    public boolean copyLore(ItemStack bukkitItemStackSource, ItemStack bukkitItemStackDestination)
    {
        CraftItemStack a = CraftItemStack_v26_1_2.castToCraftItemStack(bukkitItemStackSource);
        ItemLore lore = CraftItemStack_v26_1_2.getHandle(a).get(DataComponents.LORE);
        if(lore == null)
            return false;

        CraftItemStack b = CraftItemStack_v26_1_2.castToCraftItemStack(bukkitItemStackDestination);
        CraftItemStack_v26_1_2.getHandle(b).set(DataComponents.LORE, lore);
        return true;
    }

    @Override
    public void appendLoreJson(ItemStack bukkitItem, List<String> endLinesJson)
    {
        CraftItemStack craftItemStack = CraftItemStack_v26_1_2.castToCraftItemStack(bukkitItem);
        net.minecraft.world.item.ItemStack itemStack = CraftItemStack_v26_1_2.getHandle(craftItemStack);

        List<Component> endLines = new ArrayList<>(endLinesJson.size());
        for (String lineJson : endLinesJson)
            endLines.add(CraftChatMessage.fromJSON(lineJson));

        ItemLore lore = itemStack.get(DataComponents.LORE);
        if(lore == null)
            itemStack.set(DataComponents.LORE, new ItemLore(endLines));
        else
        {
            List<Component> lines = new ArrayList<>(lore.lines().size() + endLines.size());
            lines.addAll(lore.lines());
            lines.addAll(endLines);
            itemStack.set(DataComponents.LORE, new ItemLore(lines));
        }
    }

    @Override
    public void setCustomName(ItemStack bukkitItem, String json)
    {
        CraftItemStack craftItemStack = CraftItemStack_v26_1_2.castToCraftItemStack(bukkitItem);
        net.minecraft.world.item.ItemStack itemStack = CraftItemStack_v26_1_2.getHandle(craftItemStack);
        itemStack.set(DataComponents.CUSTOM_NAME, CraftChatMessage.fromJSON(json));
    }

    @Override
    public void setItemName(ItemStack bukkitItem, String json)
    {
        CraftItemStack craftItemStack = CraftItemStack_v26_1_2.castToCraftItemStack(bukkitItem);
        net.minecraft.world.item.ItemStack itemStack = CraftItemStack_v26_1_2.getHandle(craftItemStack);
        itemStack.set(DataComponents.ITEM_NAME, CraftChatMessage.fromJSON(json));
    }

    @Override
    public Object getOrAddCustomDataComponent(ItemStack bukkitItem)
    {
        CraftItemStack craftItemStack = CraftItemStack_v26_1_2.castToCraftItemStack(bukkitItem);
        net.minecraft.world.item.ItemStack itemStack = CraftItemStack_v26_1_2.getHandle(craftItemStack);
        if(!itemStack.has(DataComponents.CUSTOM_DATA))
            itemStack.set(DataComponents.CUSTOM_DATA, CustomData.of(new CompoundTag()));
        return itemStack.get(DataComponents.CUSTOM_DATA);
    }
}
