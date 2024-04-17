package me.zodd

import com.google.inject.Inject
import org.apache.logging.log4j.Logger
import org.spongepowered.api.config.DefaultConfig
import org.spongepowered.api.event.Listener
import org.spongepowered.api.event.lifecycle.ConstructPluginEvent
import org.spongepowered.api.event.lifecycle.RefreshGameEvent
import org.spongepowered.configurate.CommentedConfigurationNode
import org.spongepowered.configurate.ConfigurateException
import org.spongepowered.configurate.reference.ConfigurationReference
import org.spongepowered.plugin.PluginContainer
import org.spongepowered.plugin.builtin.jvm.Plugin

@Plugin("scripting-host")
class Host @Inject internal constructor(
    container: PluginContainer,
    private val logger: Logger,
    @DefaultConfig(sharedRoot = false)
    private val reference: ConfigurationReference<CommentedConfigurationNode>,
) {

    companion object {
        lateinit var logger: Logger
        lateinit var plugin: PluginContainer
        lateinit var config: ScriptingConfig
    }

    init {
        plugin = container
        Companion.logger = logger

        //Plugin references
        API(container, logger)
    }

    private fun loadConfig(): ScriptingConfig {
        val ref = reference.referenceTo(ScriptingConfig::class.java)
        reference.save()
        return ref.get() ?: throw ConfigurateException("ScriptConfig failed to load!")
    }

    @Listener
    fun onConstruct(event: ConstructPluginEvent) {
        logger.info("Loading Kotlin Scripting Host...")

        config = loadConfig()

        API(event.plugin(),logger)

        object : ScriptLoader() {
            val defaultImports = listOf(
                "org.spongepowered.api.*",
                "org.spongepowered.api.block.*",
                "org.spongepowered.api.command.*",
                "org.spongepowered.api.command.parameter.*",
                "org.spongepowered.api.command.parameter.managed.*",
                "org.spongepowered.api.data.*",
                "org.spongepowered.api.effect.particle.*",
                "org.spongepowered.api.effect.potion.*",
                "org.spongepowered.api.effect.sound.*",
                "org.spongepowered.api.effect.sound.music.*",
                "org.spongepowered.api.entity.*",
                "org.spongepowered.api.scheduler.*",
                "org.spongepowered.api.util.*",
                "org.apache.logging.log4j.Logger"
            )
            override fun createScript(str: String): Script = SpongeScript(str,defaultImports)
        }.loadScripts()

        logger.info("Finished loading scripts...")
    }

    @Listener
    fun onGameRefresh(event: RefreshGameEvent) {
        //todo: handle script reloads here
        config = loadConfig()
    }
}



