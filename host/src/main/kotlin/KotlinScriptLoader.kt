import java.io.File
import kotlin.script.experimental.api.EvaluationResult
import kotlin.script.experimental.api.ResultWithDiagnostics
import kotlin.script.experimental.api.onFailure

internal object KotlinScriptLoader {
    private const val DIR = "config/scripting-host/scripts/"
    private val scriptFileDir = File(DIR)

    fun loadScripts() {
        scriptFileDir.listFiles()?.map { file ->
            Logger.info("Loading script : ${file.name}...")
            KotlinScript(file.readText()).eval().logErrors(file.name)
        }
    }

    private fun ResultWithDiagnostics<EvaluationResult>.logErrors(name: String) {
        onFailure {
            LogInfo(name, reports).printLog()
        }
    }
}
