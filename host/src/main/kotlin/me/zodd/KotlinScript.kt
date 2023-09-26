package me.zodd

import java.io.File
import java.net.JarURLConnection
import java.net.URL
import kotlin.script.experimental.api.*
import kotlin.script.experimental.host.toScriptSource
import kotlin.script.experimental.jvm.dependenciesFromClassContext
import kotlin.script.experimental.jvm.dependenciesFromClassloader
import kotlin.script.experimental.jvm.jvm
import kotlin.script.experimental.jvmhost.BasicJvmScriptingHost
import kotlin.script.experimental.jvmhost.createJvmCompilationConfigurationFromTemplate

internal data class KotlinScript(
    val script: String,
) {
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
        "me.zodd.*"
    )

    private fun mergeImports(): List<String> {
        val imports = mutableListOf<String>()
        imports.addAll(defaultImports)
        imports.addAll(spongeImports)
        return imports
    }

    private fun compile(): SourceCode {
        return script.toScriptSource()
    }

    private val classloader: ClassLoader = ClassLoader.getSystemClassLoader()

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
}