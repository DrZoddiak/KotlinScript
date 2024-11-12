plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.20")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}