plugins {
    `java-library`
    id("io.papermc.paperweight.userdev")
}

dependencies {
    paperweight.paperDevBundle("26.1.1.build.29-alpha")
}

paperweight.reobfArtifactConfiguration =
    io.papermc.paperweight.userdev.ReobfArtifactConfiguration.MOJANG_PRODUCTION

java {
    disableAutoTargetJvm()
    toolchain.languageVersion = JavaLanguageVersion.of(25)
}

tasks.withType<JavaCompile>().configureEach {
    options.release = 21
}
