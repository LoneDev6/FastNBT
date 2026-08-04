plugins {
    `java-library`
    id("io.papermc.paperweight.userdev")
}

dependencies {
    paperweight.paperDevBundle("1.21.5-R0.1-SNAPSHOT")
}

tasks.withType<JavaCompile>().configureEach {
    options.release = 21
}
