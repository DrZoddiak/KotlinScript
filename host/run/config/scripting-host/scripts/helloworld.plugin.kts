import me.zodd.*
import net.kyori.adventure.identity.Identity
import net.kyori.adventure.text.Component

//Scripts automatically execute during plugin construction
Logger.info("Hello World!")

ScriptCommandManager {
    command("foo") {
        aliases += "baz"
        permission = "plugin.command.$it"
        executes {
            this.sendMessage(Identity.nil(), Component.text("Hello World!"))
            success()
        }
    }
}