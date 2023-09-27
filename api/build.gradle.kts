plugins {
    id("common")
}

dependencies {
    compileOnly("org.spongepowered:spongeapi:8.1.0")
    compileOnly("net.kyori:adventure-api:4.14.0")
}

tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}