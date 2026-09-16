plugins {
    `java-library`
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_9
    targetCompatibility = JavaVersion.VERSION_1_9
}

tasks.withType<JavaCompile>().configureEach {
    options.release = 9
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.17.1-R0.1-SNAPSHOT")
    compileOnly("org.jetbrains:annotations:24.1.0")
    compileOnly("com.google.code.gson:gson:2.8.0")

    testImplementation("junit:junit:4.13.2")
    testImplementation("org.spigotmc:spigot-api:1.17.1-R0.1-SNAPSHOT")
}

tasks.test {
    useJUnit()
    enableAssertions = true
    @Suppress("UNCHECKED_CAST")
    val adapterTargets = gradle.extra["adapterTargets"] as Map<String, Map<String, String>>
    systemProperty("fastnbt.adapterVersions", adapterTargets.keys.joinToString(","))
}

tasks.register<Jar>("sourcesJar") {
    archiveClassifier = "sources"
    from(sourceSets["main"].allSource)
}

tasks.register<Jar>("javadocJar") {
    archiveClassifier = "javadoc"
    from(tasks.javadoc)
}
