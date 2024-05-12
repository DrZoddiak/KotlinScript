plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation(tooling.kotlin)
    implementation(tooling.shadow)

}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}