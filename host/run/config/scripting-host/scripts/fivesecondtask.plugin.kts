//Scripts can import
import me.zodd.*
import org.spongepowered.api.scheduler.Task
import java.time.Duration

onServerStarted {
    val time = 5L
    val seconds = Duration.ofSeconds(time)
    Logger.info("Server has started! -- $time Second delay test begin --")

    val task = Task.builder()
        .execute { ->
            Logger.info("Task Test Message -- $time second delay")
        }
        .delay(seconds)
        .plugin(Container)
        .build()

    Scheduler.submit(task)
}

