import org.gradle.api.file.DuplicatesStrategy

plugins {
    id("common")
}


tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}