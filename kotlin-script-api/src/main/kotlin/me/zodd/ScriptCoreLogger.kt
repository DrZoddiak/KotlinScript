package me.zodd

import java.io.File
import kotlin.reflect.KClass
import kotlin.script.experimental.api.EvaluationResult
import kotlin.script.experimental.api.ResultWithDiagnostics
import kotlin.script.experimental.api.ScriptCompilationConfiguration
import kotlin.script.experimental.api.ScriptDiagnostic
import kotlin.script.experimental.api.ScriptEvaluationConfiguration
import kotlin.script.experimental.api.SourceCode
import kotlin.script.experimental.api.asSuccess
import kotlin.script.experimental.api.compilationConfiguration
import kotlin.script.experimental.api.compilerOptions
import kotlin.script.experimental.api.defaultImports
import kotlin.script.experimental.api.hostConfiguration
import kotlin.script.experimental.api.onFailure
import kotlin.script.experimental.api.onSuccess
import kotlin.script.experimental.api.providedProperties
import kotlin.script.experimental.api.refineConfigurationBeforeEvaluate
import kotlin.script.experimental.api.scriptExecutionWrapper
import kotlin.script.experimental.host.toScriptSource
import kotlin.script.experimental.jvm.baseClassLoader
import kotlin.script.experimental.jvm.dependenciesFromClassContext
import kotlin.script.experimental.jvm.dependenciesFromClassloader
import kotlin.script.experimental.jvm.dependenciesFromCurrentContext
import kotlin.script.experimental.jvm.jvm
import kotlin.script.experimental.jvm.loadDependencies
import kotlin.script.experimental.jvm.util.classpathFromClass
import kotlin.script.experimental.jvm.util.classpathFromClassloader
import kotlin.script.experimental.jvmhost.BasicJvmScriptingHost
import kotlin.script.experimental.jvmhost.createJvmCompilationConfigurationFromTemplate
import kotlin.script.experimental.jvmhost.createJvmEvaluationConfigurationFromTemplate

// todo: Find a better logging solution
// This only affects loading, plugins use their platforms logger.
object ScriptCoreLogger {
    val logger: java.util.logging.Logger = java.util.logging.Logger.getGlobal()
}

/**
 * Represents a Container for Kotlin Scripts
 */
interface KtScriptPluginContainer<C, L> {
    // The platforms container
    val container: C

    // The logger the Platform will use
    val logger: L
}

/**
 * Represents a single Script File
 */
class Script(
    private val script: String,
    private val defaultPlatformImports: List<String>,
    private val scriptContainer: KtScriptPluginContainer<*, *>
) {
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
        compilerOptions("-jvm-target=21")
        defaultImports(*mergeImports().toTypedArray())
        jvm {

            dependenciesFromCurrentContext(wholeClasspath = true)

            // https://youtrack.jetbrains.com/issue/KT-57907
            compilerOptions.append("-Xadd-modules=ALL-MODULE-PATH")
        }
    }

    private val evalConfig = createJvmEvaluationConfigurationFromTemplate<PluginScript> {

//        scriptExecutionWrapper {
//            val api = scriptContainer
//        }

    }

    fun eval(): ResultWithDiagnostics<EvaluationResult> {
        return BasicJvmScriptingHost().eval(compile(), configuration, evalConfig)
    }

    fun eval(
        configuration: ScriptCompilationConfiguration,
        evalConfig: ScriptEvaluationConfiguration
    ): ResultWithDiagnostics<EvaluationResult> {
        return BasicJvmScriptingHost().eval(compile(), configuration, evalConfig)
    }

    private fun compile(): SourceCode {
        return script.toScriptSource()
    }
}

/**
 * The loader for scripts
 */
object ScriptLoader {
    private const val SCRIPT_DIR = "config/scripting-host/scripts/"
    private val scriptFileDir = File(SCRIPT_DIR)

    /**
     * defaultPlatformImports should be a list of common imports for scripts to use
     * This will allow scripts to omit these imports
     */
    fun loadScripts(container: KtScriptPluginContainer<*, *>, defaultPlatformImports: List<String>) {
        scriptFileDir.mkdirs()
        scriptFileDir.listFiles()?.forEach { file ->
            ScriptCoreLogger.logger.info("Loading script : ${file.name}...")
            Script(file.readText(), defaultPlatformImports, container).eval().logResult(file.name)
        }
    }

    fun loadScripts(
        container: KtScriptPluginContainer<*, *>,
        defaultPlatformImports: List<String>,
        configuration: ScriptCompilationConfiguration,
        evalConfig: ScriptEvaluationConfiguration,
    ) {
        scriptFileDir.mkdirs()
        scriptFileDir.listFiles()?.forEach { file ->
            ScriptCoreLogger.logger.info("Loading script : ${file.name}...")
            Script(file.readText(), defaultPlatformImports, container).eval(configuration, evalConfig)
                .logResult(file.name)
        }
    }

    private fun ResultWithDiagnostics<EvaluationResult>.logResult(name: String) {
        onFailure {
            LogInfo(name, it.reports).printLog()
        }.onSuccess {
            ScriptCoreLogger.logger.info("Script: $name successfully loaded! - {${it}}")
            asSuccess()
        }
    }
}

internal data class LogInfo(val scriptName: String, val diagnostic: List<ScriptDiagnostic>) {
    private val fileExt = ".plugin.kts"
    private val id = scriptName.removeSuffix(fileExt).uppercase()

    //Known messages deemed of little value to the End User
    private val listOfKnownMessages = listOf(
        "Using JDK home inferred",
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