plugins {
    `java-library`
    `maven-publish`
    signing
    id("com.gradleup.shadow")
    id("com.gradleup.nmcp")
}

dependencies {
    api(project(":FastNbt-core"))

    runtimeOnly(project(path = ":fastnbt_nms_v1_17_R1", configuration = "reobf"))
    runtimeOnly(project(path = ":fastnbt_nms_v1_18_R2", configuration = "reobf"))
    runtimeOnly(project(path = ":fastnbt_nms_v1_19_R1", configuration = "reobf"))
    runtimeOnly(project(path = ":fastnbt_nms_v1_19_R2", configuration = "reobf"))
    runtimeOnly(project(path = ":fastnbt_nms_v1_19_R3", configuration = "reobf"))
    runtimeOnly(project(path = ":fastnbt_nms_v1_20_R1", configuration = "reobf"))
    runtimeOnly(project(path = ":fastnbt_nms_v1_20_R2", configuration = "reobf"))
    runtimeOnly(project(path = ":fastnbt_nms_v1_20_R3", configuration = "reobf"))
    runtimeOnly(project(path = ":fastnbt_nms_v1_20_4", configuration = "reobf"))
    runtimeOnly(project(path = ":fastnbt_nms_v1_20_6", configuration = "reobf"))
    runtimeOnly(project(path = ":fastnbt_nms_v1_21_1", configuration = "reobf"))
    runtimeOnly(project(path = ":fastnbt_nms_v1_21_3", configuration = "reobf"))
    runtimeOnly(project(path = ":fastnbt_nms_v1_21_4", configuration = "reobf"))
    runtimeOnly(project(path = ":fastnbt_nms_v1_21_5", configuration = "reobf"))
    runtimeOnly(project(path = ":fastnbt_nms_v1_21_6", configuration = "reobf"))
    runtimeOnly(project(path = ":fastnbt_nms_v1_21_7", configuration = "reobf"))
    runtimeOnly(project(path = ":fastnbt_nms_v1_21_8", configuration = "reobf"))
    runtimeOnly(project(path = ":fastnbt_nms_v1_21_10", configuration = "reobf"))
    runtimeOnly(project(path = ":fastnbt_nms_v1_21_11", configuration = "reobf"))
    runtimeOnly(project(":fastnbt_nms_v26_1_1"))
    runtimeOnly(project(":fastnbt_nms_v26_1_2"))
    runtimeOnly(project(":fastnbt_nms_v26_2"))
    runtimeOnly(project(":fastnbt_nms_v26_3"))
}

tasks.jar {
    enabled = false
}

tasks.shadowJar {
    archiveBaseName = "FastNbt"
    archiveClassifier = ""
    archiveVersion = ""
    destinationDirectory = rootProject.layout.projectDirectory.dir("output")

    manifest {
        attributes["paperweight-mappings-namespace"] = "spigot"
    }

    exclude("META-INF/*.SF", "META-INF/*.DSA", "META-INF/*.RSA")
}

tasks.assemble {
    dependsOn(tasks.shadowJar)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifactId = "FastNbt-jar"
            artifact(tasks.shadowJar)
            artifact(project(":FastNbt-core").tasks.named("sourcesJar"))
            artifact(project(":FastNbt-core").tasks.named("javadocJar"))

            pom {
                name = "FastNbt-jar"
                description = "Spigot library to edit items NBT very fast"
                url = "https://github.com/LoneDev6/FastNBT"

                licenses {
                    license {
                        name = "The Apache Software License, Version 2.0"
                        url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
                        distribution = "repo"
                    }
                }

                developers {
                    developer {
                        name = "LoneDev"
                        email = "info@matteodev.it"
                        url = "https://github.com/LoneDev6/"
                    }
                }

                scm {
                    connection = "scm:git:https://github.com/LoneDev6/FastNBT.git"
                    developerConnection = "scm:git:https://github.com/LoneDev6/FastNBT.git"
                    url = "https://github.com/LoneDev6/FastNBT"
                }
            }
        }
    }
}

signing {
    val signingKey = providers.gradleProperty("signingKey")
        .orElse(providers.environmentVariable("SIGNING_KEY"))
    val signingPassword = providers.gradleProperty("signingPassword")
        .orElse(providers.environmentVariable("SIGNING_PASSWORD"))

    if (signingKey.isPresent) {
        useInMemoryPgpKeys(signingKey.get(), signingPassword.orNull)
    } else {
        useGpgCmd()
    }

    sign(publishing.publications["maven"])
}
