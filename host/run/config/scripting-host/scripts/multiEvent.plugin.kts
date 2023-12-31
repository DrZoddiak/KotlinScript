import me.zodd.Logger
import me.zodd.onPlayerJoin
import me.zodd.onServerStarted
import me.zodd.onServerStarting
import net.kyori.adventure.text.Component

//For many simple things imports aren't required
//adventure.text, me.zodd.*, as well as bits of spongepowered.api
//Writing imports should always be preferred when in doubt.

/*
import me.zodd.Logger
import me.zodd.onServerStarted
import me.zodd.onServerStarting
*/

//Scripts can do multiple things
//noinspection UnresolvedReference
onServerStarted {
    Logger.info("Multi-Event-Server-Started")
}
onServerStarting {
    Logger.info("Multi-Event-Server-Starting")
}
onPlayerJoin {
    val player = player()
    if (!player.hasPlayedBefore()) {
        player.sendMessage(Component.text("Hello ${player.displayName()}"))
        return@onPlayerJoin
    }
    Logger.info("${player.name()} - ${player.firstJoined()}")
}