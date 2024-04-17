plugins {
    id("common")
    id("com.github.johnrengelman.shadow")
}

dependencies {
    shadow(project(":KotlinScript-script-definition"))
    shadow(kotlin("script-runtime"))
    shadow(project(":sponge-api"))
    shadow("org.spongepowered:spongeapi:8.1.0")
    shadow("net.kyori:adventure-api:4.14.0")
}

tasks.jar {
    enabled = false
    finalizedBy("shadowJar")
}

tasks.shadowJar {
    archiveClassifier.set("")
    configurations.add(project.configurations.shadow.get())
}