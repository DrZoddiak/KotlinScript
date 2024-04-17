package me.zodd

import org.bukkit.plugin.java.JavaPlugin

class Host : JavaPlugin() {
    override fun onEnable() {
        API(this, logger)

        logger.info("Loading Kotlin Scripting Host...")

        object : ScriptLoader() {
            val defaults = listOf("org.bukkit.*")
            override fun createScript(str: String): Script = PaperScript(str,defaults)
        }.loadScripts()

        logger.info("Finished loading scripts...")
    }
}