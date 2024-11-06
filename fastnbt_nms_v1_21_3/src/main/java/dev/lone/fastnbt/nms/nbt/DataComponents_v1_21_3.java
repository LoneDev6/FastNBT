package dev.lone.fastnbt.nms.nbt;

import dev.lone.fastnbt.nms.Implementation;
import dev.lone.fastnbt.nms.Version;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static dev.lone.fastnbt.nms.nbt.CraftItemStack_v1_21_3.castToCraftItemStack;
import static dev.lone.fastnbt.nms.nbt.CraftItemStack_v1_21_3.getHandle;

@Implementation.CyclicDependency(type = IDataComponents.class, version = Version.v1_21_3)
@SuppressWarnings({"unused"})
public class DataComponents_v1_21_3 implements IDataComponents
{
    @Override
    public Object getCustomName(ItemStack bukkitItemStack)
    {
        CraftItemStack craftItemStack = castToCraftItemStack(bukkitItemStack);
        return getHandle(craftItemStack).get(DataComponents.CUSTOM_NAME);
    }

    @Override
    public String getCustomNameJson(ItemStack bukkitItemStack)
    {
        CraftItemStack craftItemStack = castToCraftItemStack(bukkitItemStack);
        Component component = getHandle(craftItemStack).get(DataComponents.CUSTOM_NAME);
        if(component == null)
            return null;
        return CraftChatMessage.toJSON(component);
    }

    @Override
    public Object getItemName(ItemStack bukkitItemStack)
    {
        CraftItemStack craftItemStack = castToCraftItemStack(bukkitItemStack);
        return getHandle(craftItemStack).get(DataComponents.ITEM_NAME);
    }

    @Override
    public List<Object> getLore(ItemStack bukkitItemStack)
    {
        CraftItemStack craftItemStack = castToCraftItemStack(bukkitItemStack);
        ItemLore lore = getHandle(craftItemStack).get(DataComponents.LORE);
        if(lore == null)
            return null;
        return new ArrayList<>(lore.lines());
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setLore(ItemStack bukkitItemStack, List<?> lore)
    {
        CraftItemStack craftItemStack = castToCraftItemStack(bukkitItemStack);
        if(lore == null)
            getHandle(craftItemStack).remove(DataComponents.LORE);
        else
            getHandle(craftItemStack).set(DataComponents.LORE, new ItemLore((List<Component>) lore));
    }

    @Override
    public boolean copyAttributeModifiers(ItemStack bukkitItemStackSource, ItemStack bukkitItemStackDestination)
    {
        CraftItemStack a = castToCraftItemStack(bukkitItemStackSource);
        ItemAttributeModifiers modifiersA = getHandle(a).get(DataComponents.ATTRIBUTE_MODIFIERS);
        if(modifiersA == null)
            return false;

        CraftItemStack b = castToCraftItemStack(bukkitItemStackSource);
        ItemAttributeModifiers modifiersB = getHandle(b).get(DataComponents.ATTRIBUTE_MODIFIERS);
        if(modifiersB != null)
        {
            for (ItemAttributeModifiers.Entry modifier : modifiersA.modifiers())
            {
                modifiersB.modifiers().add(modifier);
            }
        }
        else
        {
            getHandle(b).set(DataComponents.ATTRIBUTE_MODIFIERS, modifiersA);
        }

        return true;
    }

    @Override
    public boolean copyLore(ItemStack bukkitItemStackSource, ItemStack bukkitItemStackDestination)
    {
        CraftItemStack a = castToCraftItemStack(bukkitItemStackSource);
        CraftItemStack b = castToCraftItemStack(bukkitItemStackSource);
        getHandle(b).set(DataComponents.LORE, getHandle(a).get(DataComponents.LORE));
        return true;
    }

    @Override
    public void appendLoreJson(ItemStack bukkitItem, List<String> endLinesJson)
    {
        CraftItemStack craftItemStack = castToCraftItemStack(bukkitItem);
        net.minecraft.world.item.ItemStack itemStack = getHandle(craftItemStack);

        List<Component> endLines = new ArrayList<>();
        for (String lineJson : endLinesJson)
            endLines.add(CraftChatMessage.fromJSON(lineJson));

        ItemLore lore = itemStack.get(DataComponents.LORE);
        if(lore == null)
            itemStack.set(DataComponents.LORE, new ItemLore(endLines));
        else
            itemStack.set(DataComponents.LORE, new ItemLore(Stream.concat(lore.lines().parallelStream(), endLines.parallelStream()).collect(Collectors.toList())));
    }

    @Override
    public void setCustomName(ItemStack bukkitItem, String json)
    {
        CraftItemStack craftItemStack = castToCraftItemStack(bukkitItem);
        net.minecraft.world.item.ItemStack itemStack = getHandle(craftItemStack);
        itemStack.set(DataComponents.CUSTOM_NAME, CraftChatMessage.fromJSON(json));
    }

    @Override
    public void setItemName(ItemStack bukkitItem, String json)
    {
        CraftItemStack craftItemStack = castToCraftItemStack(bukkitItem);
        net.minecraft.world.item.ItemStack itemStack = getHandle(craftItemStack);
        itemStack.set(DataComponents.ITEM_NAME, CraftChatMessage.fromJSON(json));
    }

    @Override
    public Object getOrAddCustomDataComponent(ItemStack bukkitItem)
    {
        CraftItemStack craftItemStack = castToCraftItemStack(bukkitItem);
        net.minecraft.world.item.ItemStack itemStack = getHandle(craftItemStack);
        if(!itemStack.has(DataComponents.CUSTOM_DATA))
            itemStack.set(DataComponents.CUSTOM_DATA, CustomData.of(new CompoundTag()));
        return itemStack.get(DataComponents.CUSTOM_DATA);
    }
}
