import org.spongepowered.plugin.metadata.model.PluginDependency

plugins {
    id("common")
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("org.spongepowered.gradle.plugin") version "2.2.0"
}

group = "me.zodd"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
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

    implementation("org.spongepowered:configurate-extra-kotlin:4.1.2") {
        isTransitive = false
    }

    shadow(kotlin("reflect"))
    shadow(project(":script-definition"))
    kotlinScriptDef(project(":script-definition"))
    shadow(kotlin("scripting-jvm-host"))
    shadow(kotlin("scripting-jvm"))
    shadow(kotlin("script-runtime"))

    //Allows scripts to build certain bits of API that requires Adventure
    shadow("net.kyori:adventure-api:4.14.0")
}

tasks.jar.get().enabled = false

tasks.shadowJar {
    archiveClassifier.set("")
    configurations.add(project.configurations.shadow.get())
    listOf(
        "io.github.classgraph", "javaslang", "nonapi.io.github.classgraph",
        "org.spongepowered.configurate.kotlin", "org.jetbrains.annotations",
        "org.jetbrains.concurrency", "org.jetbrains.jps", "org.jetbrains.org",
        "org.intellij", "gnu.trove", "com.sun.jna", "messages",
    ).forEach { relocate(it, "me.zodd.shaded.$it") }
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}