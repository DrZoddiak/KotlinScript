plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation(tooling.kotlin)
    implementation(tooling.shadow)
    implementation(files(tooling.javaClass.superclass.protectionDomain.codeSource.location))

}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}