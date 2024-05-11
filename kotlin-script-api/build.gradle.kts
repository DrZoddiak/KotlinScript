plugins {
    id("api-plugin")
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly(kotlin("scripting-jvm-host"))
    compileOnly(project(":KotlinScript-script-definition"))
}
