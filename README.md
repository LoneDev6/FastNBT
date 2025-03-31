# FastNBT
<hr>
<h3 align="center">
<a href="https://lonedev6.github.io/FastNBT/beer/devs/fastnbt/nms/nbt/package-summary.html">☕ JavaDocs</a>
</h3>
<hr> 

# Comparison to NBT API

## Installations
<img src="https://pstats.devs.beer/signatures/bukkit/FastNbt.svg" width="800">
<img src="https://bstats.org/signatures/bukkit/ItemNBTAPI.svg" width="800">

## Benchmark
**FastNbt** is ~190% faster than [NBT API](https://github.com/tr7zw/Item-NBT-API).\
[Check the benchmark here](https://github.com/LoneDev6/FastNBT-Benchmark)

## Usability

**FastNbt** is easier to use compared to NBT API and requires less boilerplate code.

### Creating an head texture
```java
NItem nItem = new nItem(new ItemStack(Material.PLAYER_HEAD));
nItem.setSkull("dummy", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjc4ZWYyZTRjZjJjNDFhMmQxNGJmZGU5Y2FmZjEwMjE5ZjViMWJmNWIzNWE0OWViNTFjNjQ2Nzg4MmNiNWYwIn19fQ==");
nItem.save(); // If finished editing
```

### Renaming an item
NOTE: FastNBT supports only Compound strings.
If you want to use legacy notation you have to call the Spigot API as usual.
```java
NItem nItem = new nItem(new ItemStack(Material.STONE));
nItem.setDisplayNameCompound("{\"text\":\"Example Compound Name\",\"color\":\"blue\"}");
nItem.save(); // If finished editing
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
nItem.save(); // If finished editing
```
### Setting an attribute modifier (manual method)
```java
NList attributes = nItem.getOrAddList("AttributeModifiers", NBTType.Compound);
NCompound attribute = new NCompound();
attribute.setString("AttributeName", attributeName);
attribute.setInt("Operation", operation);
attribute.setInt("UUIDLeast", uuidLeast);
attribute.setInt("UUIDMost", uuidMost);
attribute.setDouble("Amount", amount);
attribute.setString("Name", name);
attribute.setString("Slot", slot);
attributes.addCompound(attribute);
```

# Limitations

Currently, supports only items.

# Adding it to your project

## Plugin.yml
This is the easiest way.
```yml
name: Your Plugin
author: You
# ....
libraries:
  - beer.devs:FastNbt-jar:1.4.5
```

## Maven
```xml
<dependency>
    <groupId>beer.devs</groupId>
    <artifactId>FastNbt-jar</artifactId>
    <version>1.4.5</version>
    <scope>provided</scope>
</dependency>
```

### Shading
```xml
 <plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-shade-plugin</artifactId>
    <version>3.5.0</version>
    <executions>
        <execution>
            <phase>package</phase>
            <goals>
                <goal>shade</goal>
            </goals>
            <configuration>
                <relocations>
                    <relocation>
                        <pattern>beer.devs.fastnbt.</pattern>
                        <shadedPattern>YOUR_PACKAGE_HERE.libs.beer.devs.fastnbt.</shadedPattern>
                    </relocation>
                </relocations>
            </configuration>
        </execution>
    </executions>
</plugin>
```

# Updating

- Create a new module for the new NMS version and add the correct `paper-nms` **dependency**.
- Add the new NMS version to the `Version` enum.
- Add the new module in the modules list of `FastNbt` module and as dependency in the `FastNbt-jar` module.
- Also create the relative `_mojangmap` and `_spigotmap` modules.

Should be all.


## Why the Mojang and Spigot maps are separated?
Paper user Mojang mapped JAR. At the same time Paper itself remaps the plugins JARs at runtime, thinking that they
all are Spigot mapped. This causes issues because it remaps the already remapped classes.
This is the only solution I found to avoid this issue, in the future I might come up with something else.

# LoneDev's Notes

## How to publish to the maven repository
`mvn deploy`

## How to install locally
`mvn install`

## Editing to the repository
- Clone it
- Change paths in `pom.xml` -> `maven-antrun-plugin` based on your directories, or comment the plugin if you don't need it
- Make your changes
- Run `mvn install` in order to access the plugin as dependency in your projects
- Run Maven `clean package` and get the generated jar from `output` folder

## Updating Javadocs

In order to update Javadocs you have to build locally, as old NMS jars are not available and can't be easily included on Github.
- Run the command `mvn clean install javadoc:javadoc -pl FastNbt-core -am`
- Get the generated javadocs from `.cache/targets/FastNbt-core/target/reports/apidocs/`
- Push the contents into the `javadoc` branch
