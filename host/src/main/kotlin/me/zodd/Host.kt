package me.zodd

import com.google.inject.Inject
import org.apache.logging.log4j.Logger
import org.spongepowered.api.config.DefaultConfig
import org.spongepowered.api.event.Listener
import org.spongepowered.api.event.lifecycle.*
import org.spongepowered.configurate.CommentedConfigurationNode
import org.spongepowered.configurate.ConfigurateException
import org.spongepowered.configurate.reference.ConfigurationReference
import org.spongepowered.plugin.PluginContainer
import org.spongepowered.plugin.builtin.jvm.Plugin

@Plugin("scripting-host")
class Host @Inject internal constructor(
    container: PluginContainer,
    logger: Logger,
    @DefaultConfig(sharedRoot = false)
    private val reference: ConfigurationReference<CommentedConfigurationNode>,
) {

    companion object {
        lateinit var container: PluginContainer
        lateinit var logger: Logger
        lateinit var config: ScriptingConfig
    }

    init {
        Companion.container = container
        Companion.logger = logger
    }

    private fun loadConfig(): ScriptingConfig {
        val ref = reference.referenceTo(ScriptingConfig::class.java)
        reference.save()
        return ref.get() ?: throw ConfigurateException("Whoops!")
    }

    @Listener
    fun onConstruct(event: ConstructPluginEvent) {
        Logger.info("Loading Kotlin Scripting Host...")

        config = loadConfig()

        KotlinScriptLoader.loadScripts()
    }
}



