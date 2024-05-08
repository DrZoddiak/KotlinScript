import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.project

plugins {
    id("common")
    id("com.github.johnrengelman.shadow")
}
dependencies {
    shadow(project(":KotlinScript-script-definition"))
    shadow(kotlin("script-runtime"))
}

tasks {
    jar {
        enabled = false
        finalizedBy("shadowJar")
    }
    shadowJar {
        archiveClassifier.set("")
        configurations.add(project.configurations.shadow.get())
    }
}