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

- Create a new module for the NMS version and configure its `paperweight.paperDevBundle` dependency.
- Add the new NMS version to the `Version` enum.
- Include the module in `settings.gradle.kts` and add it to `FastNbt-jar/build.gradle.kts`.

Should be all.

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
