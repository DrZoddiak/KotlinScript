package me.zodd

import org.bukkit.event.server.ServerLoadEvent
import org.bukkit.plugin.java.JavaPlugin

class Host : JavaPlugin() {
    override fun onEnable() {
        API(this, logger)

        logger.info("Loading Kotlin Scripting Host...")

        ScriptLoader.loadScripts(listOf("org.bukkit.*"))

        onServerLoad {
            logger.info("Hello world!")
        }

        logger.info("Finished loading scripts...")
    }
}
fun onServerLoad(executor: (ServerLoadEvent) -> Unit) = RegistrationHelper.registerListener(executor)
