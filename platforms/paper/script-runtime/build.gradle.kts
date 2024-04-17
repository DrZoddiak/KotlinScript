plugins {
    id("runtime-plugin")
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    shadow(project(":paper-api"))
    shadow("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
}

