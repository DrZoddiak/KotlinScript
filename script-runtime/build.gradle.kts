plugins {
    common
    alias(libs.plugins.shadow)
}

dependencies {
    shadow(project(":script-definition"))
    shadow(project(":api"))
    shadow(libs.kotlin.scripting.runtime)
    shadow(libs.bundles.spongeAventure)
}

tasks.jar {
    enabled = false
    dependsOn("shadowJar")
}

tasks.shadowJar {
    archiveClassifier.set("")
    configurations.add(project.configurations.shadow.get())
}