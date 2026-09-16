<hr>
<h3 align="center">
<a href="https://lonedev6.github.io/FastNBT/beer/devs/fastnbt/nms/nbt/package-summary.html">☕ JavaDocs</a>
</h3>
<hr> 

## Dependency

[![FastNbt-jar](https://img.shields.io/badge/dynamic/xml?url=https%3A%2F%2Frepo1.maven.org%2Fmaven2%2Fbeer%2Fdevs%2FFastNbt-jar%2Fmaven-metadata.xml&query=%2Fmetadata%2Fversioning%2Flatest&label=version&color=blue)](https://central.sonatype.com/artifact/beer.devs/FastNbt-jar)


# Comparison to NBT API

## Installations
<img src="https://pstats.devs.beer/signatures/bukkit/FastNbt.svg" width="800">
<img src="https://bstats.org/signatures/bukkit/ItemNBTAPI.svg" width="800">

## Benchmark
**FastNbt** is ~190% faster than [NBT API](https://github.com/tr7zw/Item-NBT-API).\
[Check the benchmark here](https://github.com/LoneDev6/FastNBT-Benchmark)

## Usability

**FastNbt** is easier to use compared to NBT API and requires less boilerplate code.

### Creating a head texture
FastNBT writes `SkullOwner` NBT or the native profile Data Component automatically.
```java
NItem nItem = new NItem(new ItemStack(Material.PLAYER_HEAD));
nItem.setSkull("dummy", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjc4ZWYyZTRjZjJjNDFhMmQxNGJmZGU5Y2FmZjEwMjE5ZjViMWJmNWIzNWE0OWViNTFjNjQ2Nzg4MmNiNWYwIn19fQ==");
```

### Renaming an item
FastNBT accepts JSON text components for item names.
Use the Bukkit API as usual for legacy strings.
```java
NItem nItem = new NItem(new ItemStack(Material.STONE));
nItem.setCustomNameJson("{\"text\":\"Example Name\",\"color\":\"blue\"}");
```

### Setting an attribute modifier
FastNBT writes legacy NBT or modern Data Components automatically, depending on the server version.
```java
NItem nItem = new NItem(new ItemStack(Material.DIAMOND_BOOTS));
nItem.setAttributeModifier(
        AttributeName.MOVEMENT_SPEED,
        AttributeOperation.ADD,
        0.1,
        "movement_speed",
        "feet",
        1337,
        1337
);
```
Operations are `ADD`, `MULTIPLY_BASE`, and `MULTIPLY_TOTAL`.
`AttributeName` contains every vanilla attribute and resolves its exact NMS name for the current Minecraft version.
Legacy names such as `attackDamage`, `generic.attackDamage`, and `horse.jumpStrength` are accepted and converted automatically.
Using an attribute before the version that introduced it throws `IllegalArgumentException`.
The existing `String` and integer-operation overload remains available for compatibility.

### Setting an attribute modifier manually (Minecraft 1.20.4 and older)
Use the high-level method above for multi-version code. For direct legacy NBT access:
```java
UUID uuid = new UUID(1337, 1337);
NCompound attribute = nItem.getOrAddList("AttributeModifiers", NBTType.Compound).addCompound();
attribute.setString("AttributeName", AttributeName.MOVEMENT_SPEED.getName());
attribute.setInt("Operation", AttributeOperation.ADD.getId());
attribute.setUUID("UUID", uuid);
attribute.setDouble("Amount", 0.1);
attribute.setString("Name", "movement_speed");
attribute.setString("Slot", "feet");
nItem.save();
```

## Modern item merging

`NItem.merge` copies the source item's present components when their values
differ from that source item's defaults. It discovers component types from the
item itself, so newly added types do not require a manual merge list. Absent
components, explicit removals and default values do not clear or reset values
on the destination.

`CUSTOM_DATA` is merged recursively, including when the destination has none.
Source values win on conflicting keys; copied NBT does not share mutable tags
with the source item. `./gradlew check --no-daemon` exercises this behavior
against every configured modern adapter using its mapped Paper classes.

# Limitations

Currently, supports only items.

# Adding it to your project

![Maven Central](https://img.shields.io/maven-central/v/beer.devs/FastNbt-jar?label=Maven%20Central&style=flat-square)


## Method 1 - Direct use on Spigot
This is the easiest way.

### Step 1 - `plugin.yml`
```yml
name: Your Plugin
author: You
# ....
libraries:
  - beer.devs:FastNbt-jar:VERSION
```

### Step 2 - Gradle Kotlin DSL
```kotlin
dependencies {
    compileOnly("beer.devs:FastNbt-jar:VERSION")
}
```

## Method 2 - Direct use on Paper
This is the easiest way, but requires some special steps.

### Step 1
Shade libby into your JAR, read more [here](https://github.com/AlessioDP/libby).

### Step 2 - `plugin.yml`
Add the lib into `libraries-libby` of your `plugin.yml` and specify the `--remap` flag.
```yml
name: Your Plugin
author: You
# ....
libraries-libby:
  - beer.devs:FastNbt-jar:VERSION --remap
```

### Step 3
You need to include this [LibsLoader](https://gist.github.com/LoneDev6/27fed334fc3ef013e666a7371a6b3551) class in your plugin and call in `onLoad`.\
This will load the libraries you specified in the `plugin.yml` file.
```java
new LibsLoader(this).loadAll();
```

### Step 4 - Gradle Kotlin DSL
Same as Method 1

## Method 3 - Shading

You can shade the library in your plugin if you want to use it without connecting to Maven Central.

### Shading Configuration - Gradle Kotlin DSL
```kotlin
plugins {
    id("com.gradleup.shadow") version "9.4.1"
}

dependencies {
    implementation("beer.devs:FastNbt-jar:VERSION")
}

tasks.shadowJar {
    relocate("beer.devs.fastnbt", "YOUR_PACKAGE_HERE.libs.beer.devs.fastnbt")
}
```

-----

# Updating

## Adapter layout

- `adapters/versions/<version>/adapter.properties` declares the exact target's
  Paper dev bundle, Java release/toolchain, output mappings and source variant
  for each wrapper. This is the only adapter build configuration to maintain.
- `adapters/sources/<wrapper>/<wrapper>_<source-version>.java` contains the
  canonical Java variants. Each target selects its variants explicitly;
  no version is selected by proximity or protocol number.
- `adapters/adapter.gradle.kts` is the shared build script. Gradle `Sync`
  generates sources under each version's `build/generated/sources/nms/main/java`
  by replacing only the selected source version suffix in filenames and text.

Edit the canonical sources, not the generated files. A change to a shared
variant affects every target selecting it. When behavior differs, add a new
variant for that wrapper and select it only for the affected targets.

The Gradle project names remain `:fastnbt_nms_<version>`. Java package names,
public APIs, Maven coordinates and the final `output/FastNbt.jar` are unchanged.
Each target still compiles against its own dev bundle; legacy targets use
`reobf` artifacts, while targets declaring `mappings=mojang` use their normal JAR.

## Adding a version

1. Add `adapters/versions/<version>/adapter.properties`, using an existing target
   as a starting point. Set `devBundle`, `javaRelease`, `mappings` (`spigot` or
   `mojang`) and, where required, `javaToolchain`.
2. Select each wrapper's source version explicitly. `CompoundTag`,
   `CraftItemStack`, `DataFixer`, `ListTag` and `NbtIo` are required;
   `DataComponents` and `NBTUtilsModern` are included where needed.
3. Add the version to the public `Version` enum, preserving the existing order.
   Settings and the final JAR discover the target from its manifest automatically.
4. Run `./gradlew check :FastNbt-jar:shadowJar --no-daemon`. The check compiles
   every adapter, runs the core tests and verifies that configured targets match
   supported enum entries, excluding documented historical versions and aliases.
5. Verify new NMS behavior on Spigot and Paper, including NBT round trips and
   item copy/mirror semantics. Successful generation alone does not prove runtime
   compatibility.

# LoneDev's Notes

## Deploying to Maven Central

Create a Central Portal user token and put it in `~/.gradle/gradle.properties`:
```properties
mavenCentralUsername=TOKEN_USERNAME
mavenCentralPassword=TOKEN_PASSWORD
```

The deploy uses the default GPG key through `gpg-agent`. To select one explicitly, add:
```properties
signing.gnupg.keyName=KEY_ID
```

Run `.scripts/deploy_maven.sh` or the `Deploy to Maven Central` IntelliJ run configuration.
The deployment is validated and left for manual release at https://central.sonatype.com/publishing/deployments.

## Editing to the repository
- Clone it
- Make your changes
- Run `./gradlew build`
- Use the generated `output/FastNbt.jar`

## Updating Javadocs

In order to update Javadocs you have to build locally, as old NMS jars are not available and can't be easily included on Github.
- Run `./gradlew :FastNbt-core:javadoc`
- Get the generated Javadocs from `FastNbt-core/build/docs/javadoc/`
- Push the contents into the `javadoc` branch


---

## Solving `java.lang.NoClassDefFoundError: ji$a` and similar

This happens when mappings are out of date. Refresh Paperweight's dependencies and rebuild:
`./gradlew build --refresh-dependencies`
