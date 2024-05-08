plugins {
    id("api-plugin")
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly(kotlin("scripting-jvm-host"))
    compileOnly(project(":KotlinScript-script-definition"))
    compileOnly(libs.paper)
    compileOnly("org.spongepowered:spongeapi:8.1.0")
}
