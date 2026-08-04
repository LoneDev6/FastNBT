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

include(
    ":FastNbt-core",
    ":FastNbt-jar",
    ":fastnbt_nms_v1_17_R1",
    ":fastnbt_nms_v1_18_R2",
    ":fastnbt_nms_v1_19_R1",
    ":fastnbt_nms_v1_19_R2",
    ":fastnbt_nms_v1_19_R3",
    ":fastnbt_nms_v1_20_R1",
    ":fastnbt_nms_v1_20_R2",
    ":fastnbt_nms_v1_20_R3",
    ":fastnbt_nms_v1_20_4",
    ":fastnbt_nms_v1_20_6",
    ":fastnbt_nms_v1_21_1",
    ":fastnbt_nms_v1_21_3",
    ":fastnbt_nms_v1_21_4",
    ":fastnbt_nms_v1_21_5",
    ":fastnbt_nms_v1_21_6",
    ":fastnbt_nms_v1_21_7",
    ":fastnbt_nms_v1_21_8",
    ":fastnbt_nms_v1_21_10",
    ":fastnbt_nms_v1_21_11",
    ":fastnbt_nms_v26_1_1",
    ":fastnbt_nms_v26_1_2",
    ":fastnbt_nms_v26_2",
)
