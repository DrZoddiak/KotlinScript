plugins {
    id("host-plugin")
    id("io.papermc.paperweight.userdev") version "1.5.15"
}

dependencies {
    shadow(project(":paper-api"))

    paperweight.paperDevBundle("1.20.4-R0.1-SNAPSHOT")

    api("net.kyori:adventure-api:4.14.0")
}

tasks {
    assemble {
        dependsOn(reobfJar)
    }
}