plugins {
    id("api-plugin")
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly(libs.paper)
    compileOnly(project(":KotlinScript-kotlin-script-api"))
}
