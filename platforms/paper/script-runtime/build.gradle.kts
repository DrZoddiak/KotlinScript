plugins {
    id("runtime-plugin")
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    shadow(project(":paper-api"))
    shadow(libs.paper)
    shadow(libs.adventure.kt)
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