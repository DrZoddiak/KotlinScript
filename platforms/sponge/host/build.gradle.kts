plugins {
    id("host-plugin")
    id("org.spongepowered.gradle.plugin") version "2.2.0"
}

sponge {
    apiVersion(libs.versions.sponge8.api.get())
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
}

