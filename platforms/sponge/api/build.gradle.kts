plugins {
    id("api-plugin")
}

dependencies {
    compileOnly(libs.sponge8)
    compileOnly(project(":KotlinScript-kotlin-script-api"))
}