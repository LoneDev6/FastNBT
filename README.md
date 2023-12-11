# FastNBT

# Comparison to NBT API

## Benchmark
**FastNbt** is ~190% faster than [NBT API](https://github.com/tr7zw/Item-NBT-API).\
[Check the benchmark here](https://github.com/LoneDev6/FastNBT-Benchmark)

## Usability

**FastNbt** is easier to use compared to NBT API and requires less boilerplate code.

### Creating an head texture
```java
NItem nItem = new nItem(new ItemStack(Material.PLAYER_HEAD));
nItem.setSkull("dummy", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjc4ZWYyZTRjZjJjNDFhMmQxNGJmZGU5Y2FmZjEwMjE5ZjViMWJmNWIzNWE0OWViNTFjNjQ2Nzg4MmNiNWYwIn19fQ==");
```

### Renaming an item
```java
NItem nItem = new nItem(new ItemStack(Material.STONE));
nItem.setDisplayNameCompound("{\"text\":\"Example Compound Name\",\"color\":\"blue\"}");
```

### Setting an attribute modifier
```java
nItem.setAttributeModifier(
        "minecraft:generic.movement_speed",
        1,
        6,
        "bro",
        "mainhand",
        1337,
        1337
);
```
### Setting an attribute modifier (manual method)
```java
NTagList<?> attributes = getOrAddList("AttributeModifiers", NbtType.Compound);
NCompound attribute = attributes.getOrAddCompound();
attribute.setString("AttributeName", attributeName);
attribute.setInt("Operation", operation);
attribute.setInt("UUIDLeast", uuidLeast);
attribute.setInt("UUIDMost", uuidMost);
attribute.setDouble("Amount", amount);
attribute.setString("Name", name);
attribute.setString("Slot", slot);
attributes.add(attribute);
```

# Limitations

Currently supports only items.
