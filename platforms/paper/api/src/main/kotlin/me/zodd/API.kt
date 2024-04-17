package me.zodd

import org.bukkit.plugin.Plugin
import java.util.logging.Logger

class API(
    override val container: Plugin,
    override val logger: Logger,
) : KtScriptPluginContainer<Plugin, Logger> {

    companion object {
        lateinit var container: Plugin
        lateinit var logger: Logger
    }

    init {
        Companion.container = container
        Companion.logger = logger
    }
}