plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation(libs.kotlin.gradle)
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}