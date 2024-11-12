plugins {
    id("common")
}

dependencies {
    compileOnly("org.spongepowered:spongeapi:12.0.0")
    compileOnly("net.kyori:adventure-api:4.14.0")
}

tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}