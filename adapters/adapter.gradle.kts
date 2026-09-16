plugins {
    `java-library`
    id("io.papermc.paperweight.userdev")
}

@Suppress("UNCHECKED_CAST")
val adapterTargets = gradle.extra["adapterTargets"] as Map<String, Map<String, String>>
val targetVersion = project.name.removePrefix("fastnbt_nms_")
val target = adapterTargets.getValue(targetVersion)
val wrappers = setOf("CompoundTag", "CraftItemStack", "DataComponents", "DataFixer", "ListTag", "NBTUtilsModern", "NbtIo")
val requiredWrappers = wrappers - setOf("DataComponents", "NBTUtilsModern")
val metadata = setOf("devBundle", "javaRelease", "javaToolchain", "mappings")
check(target.keys.containsAll(requiredWrappers + (metadata - "javaToolchain"))) {
    "Missing adapter configuration for $targetVersion"
}
check((target.keys - wrappers - metadata).isEmpty()) { "Unknown adapter configuration for $targetVersion" }
check(target.getValue("mappings") in setOf("spigot", "mojang")) { "Unknown mappings for $targetVersion" }

dependencies {
    compileOnly(project(":FastNbt-core"))
    compileOnly("commons-lang:commons-lang:2.6")
    paperweight.paperDevBundle(target.getValue("devBundle"))
}

if (target.getValue("mappings") == "mojang") {
    paperweight.reobfArtifactConfiguration =
        io.papermc.paperweight.userdev.ReobfArtifactConfiguration.MOJANG_PRODUCTION
    java.disableAutoTargetJvm()
}
target["javaToolchain"]?.let {
    java.toolchain.languageVersion = JavaLanguageVersion.of(it.toInt())
}
tasks.withType<JavaCompile>().configureEach {
    options.release = target.getValue("javaRelease").toInt()
}

val generateNmsSources by tasks.registering(Sync::class) {
    val version = targetVersion
    description = "Generates the exact $targetVersion NMS adapter sources"
    into(layout.buildDirectory.dir("generated/sources/nms/main/java"))
    filteringCharset = "UTF-8"
    includeEmptyDirs = false
    duplicatesStrategy = DuplicatesStrategy.FAIL
    inputs.property("targetVersion", targetVersion)
    inputs.properties(target.filterKeys { it in wrappers })
    for ((wrapper, sourceVersion) in target.filterKeys { it in wrappers }.toSortedMap()) {
        val source = rootProject.file("adapters/sources/$wrapper/${wrapper}_$sourceVersion.java")
        check(source.isFile) { "Missing $wrapper source for $targetVersion: $source" }
        from(source) {
            into("beer/devs/fastnbt/nms/nbt/impl")
            rename { it.replace(sourceVersion, version) }
            filter { line: String -> line.replace(sourceVersion, version) }
        }
    }
}
sourceSets.main {
    java.setSrcDirs(listOf(generateNmsSources))
}

if (target.containsKey("DataComponents")) {
    val mergeTest = sourceSets.create("mergeTest") {
        java.srcDir(rootProject.file("adapters/tests"))
        compileClasspath += sourceSets.main.get().output + configurations.compileClasspath.get()
        runtimeClasspath += compileClasspath
    }
    val testItemMerge by tasks.registering(JavaExec::class) {
        group = "verification"
        description = "Checks native item merging against the exact $targetVersion adapter"
        dependsOn(mergeTest.classesTaskName)
        classpath = mergeTest.runtimeClasspath
        mainClass = "beer.devs.fastnbt.nms.nbt.impl.ItemMergeCheck"
        args(targetVersion)
        maxHeapSize = "512m"
        enableAssertions = true
        val directory = layout.buildDirectory.dir("merge-test").get().asFile
        workingDir = directory
        doFirst { directory.mkdirs() }
    }
    tasks.check {
        dependsOn(testItemMerge)
    }
}
