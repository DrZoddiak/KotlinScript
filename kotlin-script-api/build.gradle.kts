
plugins {
    id("api-plugin")
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly(kotlin("scripting-jvm-host"))
    compileOnly(project(":KotlinScript-script-definition"))
    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
    compileOnly("org.spongepowered:spongeapi:8.1.0")
}
