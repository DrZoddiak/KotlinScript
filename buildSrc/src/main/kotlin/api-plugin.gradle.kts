import org.gradle.api.file.DuplicatesStrategy

plugins {
    id("common")
}

dependencies {
    compileOnly("net.kyori:adventure-api:4.14.0")
}

tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}