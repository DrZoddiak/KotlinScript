plugins {
    id("runtime-plugin")
}

dependencies {
    shadow(project(":sponge-api"))
    shadow(libs.sponge8)
}

