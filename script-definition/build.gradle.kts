plugins {
    id("common")
}

group = "me.zodd"

repositories {
    mavenCentral()
}

dependencies {
    api(kotlin("scripting-jvm"))
}