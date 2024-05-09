plugins {
    kotlin("jvm")
}

group = "me.zodd"
version = "0.1.91"

repositories {
    mavenCentral()
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}