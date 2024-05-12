import me.zodd.Logger
import me.zodd.onPlayerDeath
import me.zodd.onPlayerJoin
import me.zodd.onServerLoad

// You can include as many events as you want inside of scripts

// Fires during OnEnable
Logger.info("Script constructed")

onServerLoad {
    Logger.info("Server has loaded!")
}

onPlayerJoin {
    Logger.info("Player ${player.name} has joined the server!")
}

onPlayerDeath {
    Logger.info("Player ${player.name} has died")
}