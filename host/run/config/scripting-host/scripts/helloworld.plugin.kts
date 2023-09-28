import me.zodd.*

//Scripts automatically execute during plugin construction
Logger.info("Hello World!")

ScriptCommandManager {
    command("foo") {
        aliases += "baz"
        permission = "plugin.command.$it"
        executes {
            success()
        }
    }
}