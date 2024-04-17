package me.zodd

import java.io.File
import kotlin.script.experimental.api.EvaluationResult
import kotlin.script.experimental.api.ResultWithDiagnostics
import kotlin.script.experimental.api.ScriptDiagnostic
import kotlin.script.experimental.api.SourceCode
import kotlin.script.experimental.api.asSuccess
import kotlin.script.experimental.api.compilerOptions
import kotlin.script.experimental.api.defaultImports
import kotlin.script.experimental.api.onFailure
import kotlin.script.experimental.api.onSuccess
import kotlin.script.experimental.host.toScriptSource
import kotlin.script.experimental.jvm.dependenciesFromCurrentContext
import kotlin.script.experimental.jvm.jvm
import kotlin.script.experimental.jvmhost.BasicJvmScriptingHost
import kotlin.script.experimental.jvmhost.createJvmCompilationConfigurationFromTemplate

object ScriptCoreLogger {
    val logger: java.util.logging.Logger = java.util.logging.Logger.getGlobal()
}

interface KtScriptPluginContainer<C, L> {
    val container: C

    val logger: L
}

abstract class Script(private val script: String, private val defaultPlatformImports: List<String>) {
    private val defaultImports: List<String> = listOf(
        //Kotlin Packages
        "kotlin.reflect.*",
        "kotlin.reflect.jvm.*",
        //Kyori
        "net.kyori.adventure.text.*",
        //Plugin
        "me.zodd.*",
    )

    private fun mergeImports(): List<String> {
        val imports = mutableListOf<String>()
        imports.addAll(defaultImports)
        imports.addAll(defaultPlatformImports)
        return imports
    }

    private val configuration = createJvmCompilationConfigurationFromTemplate<PluginScript> {
        compilerOptions("-jvm-target", "17")
        defaultImports(*mergeImports().toTypedArray())
        jvm {
            dependenciesFromCurrentContext(
                wholeClasspath = true
            )
            compilerOptions.append("-Xadd-modules=ALL-MODULE-PATH")
        }
    }

    fun eval(): ResultWithDiagnostics<EvaluationResult> {
        return BasicJvmScriptingHost().eval(compile(), configuration, null)
    }

    private fun compile(): SourceCode {
        return script.toScriptSource()
    }
}

abstract class  ScriptLoader() {
    private val scriptDir = "config/scripting-host/scripts/"
    private val scriptFileDir = File(scriptDir)

    fun loadScripts() {
        scriptFileDir.mkdirs()
        scriptFileDir.listFiles()?.forEach { file ->
            ScriptCoreLogger.logger.info("Loading script : ${file.name}...")
            createScript(file.readText()).eval().logResult(file.name)
        }
    }

    abstract fun createScript(str: String): Script

    private fun ResultWithDiagnostics<EvaluationResult>.logResult(name: String) {
        onFailure {
            LogInfo(name, it.reports).printLog()
        }.onSuccess {
            ScriptCoreLogger.logger.info("Script: $name successfully loaded!")
            asSuccess()
        }
    }
}

internal data class LogInfo(val scriptName: String, val diagnostic: List<ScriptDiagnostic>) {
    private val fileExt = ".plugin.kts"
    private val id = scriptName.removeSuffix(fileExt).uppercase()

    //Known messages deemed of little value to the End User
    private val listOfKnownMessages = listOf(
        "Using JVM IR backend",
        "Using new faster version of JAR FS: it should make your build faster, but the new implementation is experimental",
    )

    private fun handleFailure(string: String): String? {
        //This may be valuable as a configurable option
        if (string.startsWith("Loading modules:")) {
            return null
        }
        listOfKnownMessages.forEach {
            if (string.contains(it)) return null
        }
        return string
    }

    private fun generateReport(): String {
        val messageMerge = mutableListOf<String>()
        diagnostic.forEach {
            val msg = handleFailure(it.message) ?: return@forEach
            messageMerge.add(msg)
        }
        return messageMerge.joinToString("\n")
    }

    fun printLog() {
        val logTitle = "[${id}]"
        ScriptCoreLogger.logger.info(
            "\n" + """
            ################$logTitle###############
            ${generateReport()}
            ###############################${"#".repeat(logTitle.length)}
            """.trimIndent()
        )
    }
}