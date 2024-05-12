import me.zodd.BukkitTaskScheduler
import me.zodd.Logger
import me.zodd.scheduleTask

BukkitTaskScheduler.scheduleTask {
    Logger.info("Runs Once")
}

BukkitTaskScheduler.scheduleTask(20) {
    Logger.info("Runs Once after 20 ticks (1 second)")
}

BukkitTaskScheduler.scheduleTask(20, 60) {
    Logger.info("Repeats every 60 ticks (3 seconds), after a 20 tick delay")
}