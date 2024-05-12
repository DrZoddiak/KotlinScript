import me.zodd.Logger
import me.zodd.onServerLoad

/*  This script has an error
 *  When the script loads it will show the error
 *  in console, and the rest of your scripts should
 *  continue to load
 */
onServerLoad {
    // This should be Logger.info("Hello World!")
    Logger("Hello World!")
}