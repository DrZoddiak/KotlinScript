plugins {
    common
    alias(libs.plugins.shadow)
    alias(libs.plugins.sponge)
}

version = "0.1.6"

sponge {
    apiVersion("12.0.0")
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
    implementation(libs.sponge.configurate.kt) {
        isTransitive = false
    }
    implementation(libs.kotlin.reflect)
    implementation(libs.kotlin.scripting.jvm.host)
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