package me.zodd

import org.bukkit.plugin.java.JavaPlugin

class Host : JavaPlugin() {

    override fun onEnable() {
        slF4JLogger.info("Loading Kotlin Scripting Host...")

        val imports = listOf(
            "kotlin.reflect.*",
            "kotlin.reflect.jvm.*",
            "me.zodd.*",
            "net.kyori.adventure.text.*",
            "org.bukkit.*",
            "org.slf4j.Logger"
        )

        // Initialize API for scripts
        API(this, slF4JLogger)

        ScriptLoader.loadScripts(
            imports,
            this@Host::class
        )

        slF4JLogger.info("Finished loading scripts...")
    }
}
