package me.zodd

import org.apache.logging.log4j.Logger
import org.spongepowered.plugin.PluginContainer

abstract class API(
    final override val container: PluginContainer,
    final override val logger: Logger
) : KtScriptPluginContainer<PluginContainer, Logger> {

    companion object {
        lateinit var container: PluginContainer
        lateinit var logger: Logger
    }

    init {
        Companion.container = container
        Companion.logger = logger
    }
}