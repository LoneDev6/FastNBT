pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://repo.papermc.io/repository/maven-snapshots/") {
            mavenContent { snapshotsOnly() }
        }
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }
}

rootProject.name = "FastNbt"

include(":FastNbt-core", ":FastNbt-jar")

val adapterTargets = file("adapters/versions").listFiles()!!
    .filter { it.isDirectory }
    .sortedBy { it.name }
    .associate { directory ->
        val properties = java.util.Properties().apply {
            directory.resolve("adapter.properties").inputStream().use { load(it) }
        }
        val projectPath = ":fastnbt_nms_${directory.name}"
        include(projectPath)
        project(projectPath).apply {
            projectDir = directory
            buildFileName = "../../adapter.gradle.kts"
        }
        directory.name to properties.entries.associate { it.key.toString() to it.value.toString() }
    }
check(adapterTargets.isNotEmpty()) { "No NMS adapters configured" }
gradle.extra["adapterTargets"] = adapterTargets
