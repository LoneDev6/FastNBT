plugins {
    `java-library`
    `maven-publish`
    signing
    id("com.gradleup.shadow")
    id("com.gradleup.nmcp")
}

dependencies {
    api(project(":FastNbt-core"))

    @Suppress("UNCHECKED_CAST")
    val adapterTargets = gradle.extra["adapterTargets"] as Map<String, Map<String, String>>
    for ((version, target) in adapterTargets) {
        val path = ":fastnbt_nms_$version"
        if (target.getValue("mappings") == "spigot")
            runtimeOnly(project(path = path, configuration = "reobf"))
        else
            runtimeOnly(project(path))
    }
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
