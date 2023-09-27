//org.invalid is not on classpath so script will error
//Despite this error other scripts will continue to load
import me.zodd.Logger
import me.zodd.onServerStarted
import org.invalid

onServerStarted {
    Logger.info("Wont log because of script error")
}