plugins {
    `java-library`
    id("io.papermc.paperweight.userdev")
}

dependencies {
    paperweight.paperDevBundle("1.17.1-R0.1-SNAPSHOT")
}

tasks.withType<JavaCompile>().configureEach {
    options.release = 16
}
