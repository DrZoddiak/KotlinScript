package me.zodd

import org.bukkit.plugin.java.JavaPlugin
import kotlin.script.experimental.api.ScriptAcceptedLocation
import kotlin.script.experimental.api.acceptedLocations
import kotlin.script.experimental.api.compilerOptions
import kotlin.script.experimental.api.defaultImports
import kotlin.script.experimental.api.ide
import kotlin.script.experimental.jvm.baseClassLoader
import kotlin.script.experimental.jvm.dependenciesFromClassContext
import kotlin.script.experimental.jvm.dependenciesFromClassloader
import kotlin.script.experimental.jvm.dependenciesFromCurrentContext
import kotlin.script.experimental.jvm.jvm
import kotlin.script.experimental.jvm.loadDependencies
import kotlin.script.experimental.jvmhost.createJvmCompilationConfigurationFromTemplate
import kotlin.script.experimental.jvmhost.createJvmEvaluationConfigurationFromTemplate

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

        val compConfig = createJvmCompilationConfigurationFromTemplate<PluginScript> {
            ide.acceptedLocations(ScriptAcceptedLocation.Everywhere)
            compilerOptions("-jvm-target=21")
            defaultImports(imports)
            jvm {
                dependenciesFromCurrentContext(wholeClasspath = true)
                compilerOptions.append("-Xadd-modules=ALL-MODULE-PATH")
            }
        }

        val evalConfig = createJvmEvaluationConfigurationFromTemplate<PluginScript> {
            jvm {
                baseClassLoader.put(this@Host.classLoader)
            }
        }

        ScriptLoader.loadScripts(
            API(this, slF4JLogger),
            imports,
            compConfig,
            evalConfig
        )

        slF4JLogger.info("Finished loading scripts...")
    }
}

