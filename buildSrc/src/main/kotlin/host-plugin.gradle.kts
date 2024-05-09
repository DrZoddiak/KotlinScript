plugins {
    id("common")
    id("com.github.johnrengelman.shadow")
}

dependencies {
    implementation("org.spongepowered:configurate-extra-kotlin:4.1.2") {
        isTransitive = false
    }
    compileOnly(kotlin("reflect"))
    shadow(kotlin("scripting-jvm-host"))
    shadow(project(":KotlinScript-script-definition"))
    shadow(project(":KotlinScript-kotlin-script-api"))
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