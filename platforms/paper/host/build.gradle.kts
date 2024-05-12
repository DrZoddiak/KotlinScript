plugins {
    id("host-plugin")
    id("io.papermc.paperweight.userdev") version "1.7.1"
    id("xyz.jpenilla.run-paper") version "2.2.0"
}

dependencies {
    shadow(project(":paper-api"))
    paperweight.paperDevBundle(libs.versions.paper.api)
}

tasks {
    assemble {
        dependsOn(reobfJar)
    }
    runServer {
        minecraftVersion(libs.versions.minecraft.get())
    }
}
