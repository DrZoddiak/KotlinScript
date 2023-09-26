import me.zodd.ScriptCommandManager
import me.zodd.ScriptPlugin

//Scripts automatically execute during plugin construction
ScriptPlugin.logger.info("Hello World!")

ScriptCommandManager {
    command("foo") {
        aliases += "baz"
        permission = "plugin.command.$it"
        executes {
            success()
        }
    }
}