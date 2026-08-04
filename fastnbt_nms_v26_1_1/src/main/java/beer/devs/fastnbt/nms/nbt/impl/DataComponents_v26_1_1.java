package beer.devs.fastnbt.nms.nbt.impl;

import beer.devs.fastnbt.nms.nbt.IDataComponents;
import com.google.common.collect.ImmutableMultimap;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.craftbukkit.util.CraftChatMessage;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SuppressWarnings({"unused"})
public class DataComponents_v26_1_1 implements IDataComponents
{
    @Override
    public Object getCustomName(ItemStack bukkitItemStack)
    {
        CraftItemStack craftItemStack = CraftItemStack_v26_1_1.castToCraftItemStack(bukkitItemStack);
        return CraftItemStack_v26_1_1.getHandle(craftItemStack).get(DataComponents.CUSTOM_NAME);
    }

    @Override
    public String getCustomNameJson(ItemStack bukkitItemStack)
    {
        CraftItemStack craftItemStack = CraftItemStack_v26_1_1.castToCraftItemStack(bukkitItemStack);
        Component component = CraftItemStack_v26_1_1.getHandle(craftItemStack).get(DataComponents.CUSTOM_NAME);
        if(component == null)
            return null;
        return CraftChatMessage.toJSON(component);
    }

    @Override
    public Object getItemName(ItemStack bukkitItemStack)
    {
        CraftItemStack craftItemStack = CraftItemStack_v26_1_1.castToCraftItemStack(bukkitItemStack);
        return CraftItemStack_v26_1_1.getHandle(craftItemStack).get(DataComponents.ITEM_NAME);
    }

    @Override
    public String getItemNameJson(ItemStack bukkitItemStack)
    {
        CraftItemStack craftItemStack = CraftItemStack_v26_1_1.castToCraftItemStack(bukkitItemStack);
        Component component = CraftItemStack_v26_1_1.getHandle(craftItemStack).get(DataComponents.ITEM_NAME);
        if(component == null)
            return null;
        return CraftChatMessage.toJSON(component);
    }

    @Override
    public boolean hasEnchantment(ItemStack bukkitItemStack, String name)
    {
        CraftItemStack craftItemStack = CraftItemStack_v26_1_1.castToCraftItemStack(bukkitItemStack);
        ItemEnchantments enchantments = CraftItemStack_v26_1_1.getHandle(craftItemStack).get(DataComponents.ENCHANTMENTS);
        if (enchantments == null)
            return false;
        for (Holder<?> enchantment : enchantments.keySet())
        {
            if (name.equals(enchantment.getRegisteredName()))
                return true;
        }
        return false;
    }

    @Override
    public boolean hasLore(ItemStack bukkitItemStack, String line)
    {
        CraftItemStack craftItemStack = CraftItemStack_v26_1_1.castToCraftItemStack(bukkitItemStack);
        ItemLore lore = CraftItemStack_v26_1_1.getHandle(craftItemStack).get(DataComponents.LORE);
        if (lore == null)
            return false;
        for (Component component : lore.lines())
        {
            if (line.equals(CraftChatMessage.fromComponent(component)))
                return true;
        }
        return false;
    }

    @Override
    public boolean hasDisplayName(ItemStack bukkitItemStack, String name)
    {
        CraftItemStack craftItemStack = CraftItemStack_v26_1_1.castToCraftItemStack(bukkitItemStack);
        Component component = CraftItemStack_v26_1_1.getHandle(craftItemStack).get(DataComponents.CUSTOM_NAME);
        return component != null && name.equals(CraftChatMessage.fromComponent(component));
    }

    @Override
    public List<Object> getLore(ItemStack bukkitItemStack)
    {
        CraftItemStack craftItemStack = CraftItemStack_v26_1_1.castToCraftItemStack(bukkitItemStack);
        ItemLore lore = CraftItemStack_v26_1_1.getHandle(craftItemStack).get(DataComponents.LORE);
        if(lore == null)
            return null;
        return new ArrayList<>(lore.lines());
    }

    @Override
    public List<String> getLoreJson(ItemStack bukkitItemStack)
    {
        CraftItemStack craftItemStack = CraftItemStack_v26_1_1.castToCraftItemStack(bukkitItemStack);
        ItemLore lore = CraftItemStack_v26_1_1.getHandle(craftItemStack).get(DataComponents.LORE);
        if(lore == null)
            return null;

        List<String> lines = new ArrayList<>(lore.lines().size());
        for (Component component : lore.lines())
            lines.add(CraftChatMessage.toJSON(component));
        return lines;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setLore(ItemStack bukkitItemStack, List<?> lore)
    {
        CraftItemStack craftItemStack = CraftItemStack_v26_1_1.castToCraftItemStack(bukkitItemStack);
        if(lore == null)
            CraftItemStack_v26_1_1.getHandle(craftItemStack).remove(DataComponents.LORE);
        else
            CraftItemStack_v26_1_1.getHandle(craftItemStack).set(DataComponents.LORE, new ItemLore((List<Component>) lore));
    }

    @Override
    public void setLoreJson(ItemStack bukkitItemStack, List<String> lore)
    {
        CraftItemStack craftItemStack = CraftItemStack_v26_1_1.castToCraftItemStack(bukkitItemStack);
        net.minecraft.world.item.ItemStack itemStack = CraftItemStack_v26_1_1.getHandle(craftItemStack);
        if(lore == null)
            itemStack.remove(DataComponents.LORE);
        else
        {
            List<Component> lines = new ArrayList<>(lore.size());
            for (String lineJson : lore)
                lines.add(CraftChatMessage.fromJSON(lineJson));
            itemStack.set(DataComponents.LORE, new ItemLore(lines));
        }
    }

    @Override
    public void setAttributeModifier(ItemStack bukkitItemStack,
                                     String attributeName,
                                     int operation,
                                     double amount,
                                     String modifierName,
                                     UUID uuid,
                                     int slotId)
    {
        CraftItemStack craftItemStack = CraftItemStack_v26_1_1.castToCraftItemStack(bukkitItemStack);
        net.minecraft.world.item.ItemStack itemStack = CraftItemStack_v26_1_1.getHandle(craftItemStack);
        ItemAttributeModifiers modifiers = itemStack.get(DataComponents.ATTRIBUTE_MODIFIERS);
        if (modifiers == null)
            modifiers = ItemAttributeModifiers.EMPTY;
        Identifier attributeId = Identifier.parse(attributeName);
        itemStack.set(DataComponents.ATTRIBUTE_MODIFIERS, modifiers.withModifierAdded(
                BuiltInRegistries.ATTRIBUTE.get(attributeId)
                        .orElseThrow(() -> new IllegalArgumentException("Unknown attribute: " + attributeName)),
                new net.minecraft.world.entity.ai.attributes.AttributeModifier(
                        Identifier.parse(uuid.toString()), amount,
                        net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.BY_ID.apply(operation)
                ),
                EquipmentSlotGroup.BY_ID.apply(slotId)
        ));
    }

    @Override
    public void setSkull(ItemStack bukkitItemStack, String name, UUID uuid, String value, String signature)
    {
        PropertyMap properties = new PropertyMap(ImmutableMultimap.of(
                "textures", new Property("textures", value, signature)
        ));
        CraftItemStack craftItemStack = CraftItemStack_v26_1_1.castToCraftItemStack(bukkitItemStack);
        CraftItemStack_v26_1_1.getHandle(craftItemStack).set(
                DataComponents.PROFILE,
                ResolvableProfile.createResolved(new GameProfile(uuid, name, properties))
        );
    }

    @Override
    public boolean copyAttributeModifiers(ItemStack bukkitItemStackSource, ItemStack bukkitItemStackDestination)
    {
        CraftItemStack a = CraftItemStack_v26_1_1.castToCraftItemStack(bukkitItemStackSource);
        ItemAttributeModifiers modifiersA = CraftItemStack_v26_1_1.getHandle(a).get(DataComponents.ATTRIBUTE_MODIFIERS);
        if(modifiersA == null)
            return false;

        CraftItemStack b = CraftItemStack_v26_1_1.castToCraftItemStack(bukkitItemStackDestination);
        CraftItemStack_v26_1_1.getHandle(b).set(DataComponents.ATTRIBUTE_MODIFIERS, modifiersA);

        return true;
    }

    @Override
    public boolean copyLore(ItemStack bukkitItemStackSource, ItemStack bukkitItemStackDestination)
    {
        CraftItemStack a = CraftItemStack_v26_1_1.castToCraftItemStack(bukkitItemStackSource);
        ItemLore lore = CraftItemStack_v26_1_1.getHandle(a).get(DataComponents.LORE);
        if(lore == null)
            return false;

        CraftItemStack b = CraftItemStack_v26_1_1.castToCraftItemStack(bukkitItemStackDestination);
        CraftItemStack_v26_1_1.getHandle(b).set(DataComponents.LORE, lore);
        return true;
    }

    @Override
    public void appendLoreJson(ItemStack bukkitItem, List<String> endLinesJson)
    {
        CraftItemStack craftItemStack = CraftItemStack_v26_1_1.castToCraftItemStack(bukkitItem);
        net.minecraft.world.item.ItemStack itemStack = CraftItemStack_v26_1_1.getHandle(craftItemStack);

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
        CraftItemStack craftItemStack = CraftItemStack_v26_1_1.castToCraftItemStack(bukkitItem);
        net.minecraft.world.item.ItemStack itemStack = CraftItemStack_v26_1_1.getHandle(craftItemStack);
        itemStack.set(DataComponents.CUSTOM_NAME, CraftChatMessage.fromJSON(json));
    }

    @Override
    public void setItemName(ItemStack bukkitItem, String json)
    {
        CraftItemStack craftItemStack = CraftItemStack_v26_1_1.castToCraftItemStack(bukkitItem);
        net.minecraft.world.item.ItemStack itemStack = CraftItemStack_v26_1_1.getHandle(craftItemStack);
        itemStack.set(DataComponents.ITEM_NAME, CraftChatMessage.fromJSON(json));
    }

    @Override
    public Object getOrAddCustomDataComponent(ItemStack bukkitItem)
    {
        CraftItemStack craftItemStack = CraftItemStack_v26_1_1.castToCraftItemStack(bukkitItem);
        net.minecraft.world.item.ItemStack itemStack = CraftItemStack_v26_1_1.getHandle(craftItemStack);
        if(!itemStack.has(DataComponents.CUSTOM_DATA))
            itemStack.set(DataComponents.CUSTOM_DATA, CustomData.of(new CompoundTag()));
        return itemStack.get(DataComponents.CUSTOM_DATA);
    }
}
