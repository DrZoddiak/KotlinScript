package me.zodd

import io.github.classgraph.ClassGraphClassLoader
import kotlin.script.experimental.api.EvaluationResult
import kotlin.script.experimental.api.ResultWithDiagnostics
import kotlin.script.experimental.api.SourceCode
import kotlin.script.experimental.host.toScriptSource
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
        return imports.map { "import $it" }
    }

    private fun compile(): SourceCode {
        return """
            ${mergeImports().asString()}
            $script
        """.toScriptSource()
    }

    private val classloader: ClassLoader = ClassGraphClassLoader.getSystemClassLoader()

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

    private fun List<*>.asString(): String {
        return if (isEmpty()) "// empty" else joinToString("\n")
    }
}