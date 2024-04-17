import org.jetbrains.kotlin.gradle.plugin.extraProperties

plugins {
    id("host-plugin")
    id("org.spongepowered.gradle.plugin") version "2.2.0"
}

sponge {
    apiVersion("8.2.0-SNAPSHOT")
    license("MIT")
    loader {
        name("java_plain")
        version("1.0")
    }
    plugin("scripting-host") {
        displayName("scripting-host")
        entrypoint("me.zodd.Host")
        description("A host plugin for running Kotlin Scripts")
    }
}

dependencies {
    shadow(project(":sponge-api"))
    api("net.kyori:adventure-api:4.14.0")
}

