package me.zodd

import org.bukkit.plugin.Plugin
import org.slf4j.Logger

data class API(
    override val container: Plugin,
    override val logger: Logger,
) : KtScriptPluginContainer<Plugin, Logger> {

    companion object {
        lateinit var container: Plugin

        lateinit var logger: Logger
    }

    init {
        println("Being Created")
        Companion.container = container
        Companion.logger = logger
    }


}