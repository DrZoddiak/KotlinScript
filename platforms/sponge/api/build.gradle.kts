plugins {
    id("api-plugin")
}

dependencies {
    compileOnly("org.spongepowered:spongeapi:8.1.0")
    compileOnly(project(":KotlinScript-kotlin-script-api"))
}