//org.invalid is not on classpath so script will error
import org.invalid

onServerStarted {
    Logger.info("Wont log because of script error")
}