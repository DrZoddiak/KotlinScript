pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://repo.papermc.io/repository/maven-public/")
    }
}

rootProject.name = "KotlinScript"

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

val modules = listOf(
    "host",
    "api",
    "script-runtime"
)

val folderPath = "platforms/"

listOf(
    "sponge",
    "paper"
).forEach { platform ->
    modules.forEach {
        includeProject(it, "$folderPath$platform")
    }
}

val projectName: String = rootProject.name

fun includeProject(name: String, folder: String? = null) {
    include(name) {
        val dir = folder?.removePrefix(folderPath) ?: projectName
        this.name = "${dir}-$name"
        if (folder != null) {
            this.projectDir = file("$folder/$name")
        }
    }
}

fun include(name: String, block: ProjectDescriptor.() -> Unit) {
    include(name)
    project(":$name").apply(block)
}

listOf("script-definition","kotlin-script-api").forEach(::includeProject)