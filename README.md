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
NOTE: FastNBT supports only Compound strings.
If you want to use legacy notation you have to call the Spigot API as usual.
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

# Maven
## Adding it to your project
```xml
 <repository>
    <id>matteodev</id>
    <url>https://www.matteodev.it/spigot/public/maven/</url>
</repository>
```
```xml
<dependency>
    <groupId>dev.lone</groupId>
    <artifactId>FastNbt-jar</artifactId>
    <version>RELEASE_VERSION</version>
</dependency>
```

## Shading
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
                        <pattern>dev.lone.fastnbt.</pattern>
                        <shadedPattern>YOUR_PACKAGE_HERE.libs.dev.lone.fastnbt.</shadedPattern>
                    </relocation>
                </relocations>
            </configuration>
        </execution>
    </executions>
</plugin>
```

# Updating

- Create a new module for the new NMS version and add the correct `paper-nms` **dependency**.
- Add the new module src folder into the `.gitignore`.
- Add the new NMS version to the `Version` enum.
- Add the new module in the modules list of `FastNbt` module and as dependency in the `FastNbt-jar` module.
- Edit `.mvn-exec/GenerateNms.js` file if needed.
- Generate the NMS methods using `mvn validate -DgenerateNms`

Should be all.

# LoneDev's Notes

## How to generate NMS automatically
`mvn validate -DgenerateNms`\
NOTE: remember to edit the `.mvn-exec/GenerateNms.js` file if needed

## How to deploy
`mvn deploy`