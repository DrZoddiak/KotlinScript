dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libraries.versions.toml"))
        }
        create("tooling") {
            from(files("../gradle/toolchain.versions.toml"))
        }
    }
}