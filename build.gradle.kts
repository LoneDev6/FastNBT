plugins {
    base
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.21" apply false
    id("com.gradleup.shadow") version "9.4.1" apply false
    id("com.gradleup.nmcp.aggregation") version "1.6.1"
    id("com.gradleup.nmcp") version "1.6.1" apply false
}

group = "beer.devs"
version = "1.4.25"

@Suppress("UNCHECKED_CAST")
val adapterTargets = gradle.extra["adapterTargets"] as Map<String, Map<String, String>>

dependencies {
    nmcpAggregation(project(":FastNbt-jar"))
}

nmcpAggregation {
    centralPortal {
        username = providers.gradleProperty("mavenCentralUsername")
            .orElse(providers.environmentVariable("MAVEN_CENTRAL_USERNAME"))
            .getOrElse("")
        password = providers.gradleProperty("mavenCentralPassword")
            .orElse(providers.environmentVariable("MAVEN_CENTRAL_PASSWORD"))
            .getOrElse("")
        publishingType = "USER_MANAGED"
        publicationName = "$group:FastNbt-jar:$version"
        publishAllChecksums.set(true)
    }
}

subprojects {
    group = rootProject.group
    version = rootProject.version

    tasks.withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
    }

}

tasks.assemble {
    dependsOn(":FastNbt-jar:shadowJar")
}

tasks.check {
    dependsOn(":FastNbt-core:test")
    dependsOn(adapterTargets.keys.map { ":fastnbt_nms_$it:check" })
}

tasks.named("publishAggregationToCentralPortal") {
    mustRunAfter(tasks.named("build"))
}

tasks.register("deployMavenCentral") {
    group = "publishing"
    description = "Builds, validates and uploads FastNbt-jar to the Maven Central Portal"
    dependsOn(tasks.named("build"), tasks.named("publishAggregationToCentralPortal"))
}
