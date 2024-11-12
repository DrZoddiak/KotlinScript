package me.zodd

import kotlin.reflect.KClass
import kotlin.script.experimental.api.EvaluationResult
import kotlin.script.experimental.api.ResultWithDiagnostics
import kotlin.script.experimental.api.ScriptAcceptedLocation
import kotlin.script.experimental.api.SourceCode
import kotlin.script.experimental.api.acceptedLocations
import kotlin.script.experimental.api.compilerOptions
import kotlin.script.experimental.api.defaultImports
import kotlin.script.experimental.api.ide
import kotlin.script.experimental.host.toScriptSource
import kotlin.script.experimental.jvm.baseClassLoader
import kotlin.script.experimental.jvm.dependenciesFromClassloader
import kotlin.script.experimental.jvm.jvm
import kotlin.script.experimental.jvmhost.BasicJvmScriptingHost
import kotlin.script.experimental.jvmhost.createJvmCompilationConfigurationFromTemplate
import kotlin.script.experimental.jvmhost.createJvmEvaluationConfigurationFromTemplate

internal data class KotlinScript(val script: String) {
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
    )

    private fun mergeImports(): List<String> {
        val imports = mutableListOf<String>()
        imports.addAll(defaultImports)
        imports.addAll(spongeImports)
        return imports
    }

    private fun evaluationConfig(classloaderClass: KClass<*>) =
        createJvmEvaluationConfigurationFromTemplate<PluginScript> {
            jvm {
                baseClassLoader.put(classloaderClass.java.classLoader)
            }
        }

    private fun compileConfig(classloaderClass: KClass<*>) =
        createJvmCompilationConfigurationFromTemplate<PluginScript> {
            defaultImports(*mergeImports().toTypedArray())
            compilerOptions.append("-jvm-target=21")

            jvm {
                dependenciesFromClassloader(classLoader = classloaderClass.java.classLoader, wholeClasspath = true)
            }
            ide.acceptedLocations(ScriptAcceptedLocation.Everywhere)
        }

    fun eval(classloaderClass: KClass<*>): ResultWithDiagnostics<EvaluationResult> {
        return BasicJvmScriptingHost().eval(
            compile(),
            compileConfig(classloaderClass),
            evaluationConfig(classloaderClass)
        )
    }

    private fun compile(): SourceCode {
        return script.toScriptSource()
    }

}