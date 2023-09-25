//Scripts can import
import java.time.Duration

onServerStarted {
    val time = 5L
    val seconds = Duration.ofSeconds(time)
    ScriptPlugin.logger.info("Server has started! -- $time Second delay test begin --")

    val task = Task.builder()
        .execute { ->
            ScriptPlugin.logger.info("Task Test Message -- $time second delay")
        }
        .delay(seconds)
        .plugin(ScriptPlugin.container)
        .build()

    Scheduler.submit(task)
}

