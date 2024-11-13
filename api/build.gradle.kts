plugins {
    common
}

dependencies {
    compileOnly(libs.bundles.spongeAventure)
}

tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}