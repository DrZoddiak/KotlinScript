package me.zodd

import kotlin.script.experimental.api.EvaluationResult
import kotlin.script.experimental.api.ResultWithDiagnostics
import kotlin.script.experimental.api.SourceCode
import kotlin.script.experimental.api.compilerOptions
import kotlin.script.experimental.api.defaultImports
import kotlin.script.experimental.host.toScriptSource
import kotlin.script.experimental.jvm.dependenciesFromClassloader
import kotlin.script.experimental.jvm.jvm
import kotlin.script.experimental.jvmhost.BasicJvmScriptingHost
import kotlin.script.experimental.jvmhost.createJvmCompilationConfigurationFromTemplate

internal data class KotlinScript(val script: String) {
    private val classloader: ClassLoader = ClassLoader.getSystemClassLoader()

    private val spongeImports = listOf(
        "",
        "block",
        "command",
        "command.parameter",
        "command.parameter.managed",
        "data",
        "effect.particle",
        "effect.potion",
        "effect.sound",
        "effect.sound.music",
        "entity",
        "scheduler",
        "util",
    ).map {
        if (it.isEmpty())
            "org.spongepowered.api.*"
        else
            "org.spongepowered.api.$it.*"
    }

    private val defaultImports = listOf(
        //Kotlin Packages
        "kotlin.reflect.*",
        "kotlin.reflect.jvm.*",
        //Sponge Packages
        "org.apache.logging.log4j.Logger",
        //Kyori
        "net.kyori.adventure.text.*",
        "me.zodd.*",
        "me.zodd.Manager.Server",
        "me.zodd.Manager.Plugin",
        "me.zodd.Manager.PluginManager",
        "me.zodd.Manager.CommandManager",
        "me.zodd.Manager.EventManager",
        "me.zodd.Manager.ServerServiceManager",
        "me.zodd.Manager.GameServiceManager",
        "me.zodd.Manager.Scheduler",
        "me.zodd.Manager.AsyncScheduler",
        "me.zodd.Manager.Logger",
        "me.zodd.Manager.ScriptCommandManager",
    )

    private fun mergeImports(): List<String> {
        val imports = mutableListOf<String>()
        imports.addAll(defaultImports)
        imports.addAll(spongeImports)
        return imports
    }

    private val configuration = createJvmCompilationConfigurationFromTemplate<PluginScript> {
        if (Host.config.extraLogging) {
            Logger.info(
                """
            #######################CLASSPATH#######################
            ${classloader.definedPackages.joinToString("\n") { it.name }}
            ######################################################
            """.trimIndent()
            )
        }

        compilerOptions("-jvm-target", "17")
        defaultImports(*mergeImports().toTypedArray())
        jvm {
            dependenciesFromClassloader(
                "host",
                classLoader = classloader,
                wholeClasspath = true
            )
        }
    }

    fun eval(): ResultWithDiagnostics<EvaluationResult> {
        return BasicJvmScriptingHost().eval(compile(), configuration, null)
    }

    private fun compile(): SourceCode {
        return script.toScriptSource()
    }

}