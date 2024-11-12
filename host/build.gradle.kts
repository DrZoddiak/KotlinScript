plugins {
    id("common")
    id("com.gradleup.shadow") version "8.3.5"
    id("org.spongepowered.gradle.plugin") version "2.2.0"
}

version = "0.1.6"

sponge {
    apiVersion("12.0.0-SNAPSHOT")
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
    implementation("org.spongepowered:configurate-extra-kotlin:4.1.2") {
        isTransitive = false
    }
    implementation(kotlin("reflect"))
    implementation(kotlin("scripting-jvm-host"))
    implementation(project(":script-definition"))
    implementation(project(":api"))
}

tasks.jar.get().enabled = false

tasks.shadowJar {
    archiveClassifier.set("")

    listOf(
        "org.spongepowered.configurate.kotlin", "javaslang", "gnu.trove", "kotlinx",
        "org.intellij", "org.jetbrains", "messages", "misc"
    ).forEach { relocate(it, "me.zodd.shaded.$it") }

    mergeServiceFiles()
}