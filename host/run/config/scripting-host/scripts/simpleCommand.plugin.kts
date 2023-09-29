import me.zodd.*
import me.zodd.dsl.command.CommandManager.keyedWith
import net.kyori.adventure.identity.Identity
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import org.spongepowered.api.command.parameter.*
import java.util.function.Predicate


//The ideal way to write commands would be using a closure
ScriptCommandManager {
    //In this way we can register multiple commands!
    command("foo") {
        /*
        This is the simplest command possible, but it doesn't do anything
        other than prevent an error message.
         */
        executes {
            /*
            * Commands are required to return a CommandResult
            * the api provides two convenience methods
            * success() and error(Component)
             */
            success()
        }
    }
    command("bar") {
        //We can add as many aliases as we like
        aliases += "bbar"
        aliases += "cbar"
        //You can even use a list if you provide many
        aliases += listOf("dbar", "ebar", "fbar")

        //Registering a permission is simple
        //Command closures also provide the command name to context : $it = "bar"
        permission = "plugin.command.$it"

        //For advanced requirements you may consider using an executionRequirement
        //Predicate<CommandCause>
        executionRequirement = Predicate {
            true
        }

        //Provide a description!
        description = "This command has an example for how it works"

        executes {
            //Anything wanted to be run during the command happens here!
            //You have full access to CommandContext from SpongeAPI
            sendMessageToCause("Bar has been executed")
            success()
        }
    }
    command("baz") {
        //Commands may have subcommands too!
        subcommand("buzz") {
            //So can sub-commands
            subcommand("braz") {
                //sub-sub-sub-commands!?
                subcommand("broz") {
                    executes {
                        sendMessageToCause("Listen we could do this all day...")
                        success()
                    }
                }
                executes {
                    sendMessageToCause("Sub-sub-commands!")
                    success()
                }
            }
            /*executes {
                sendMessageToCause("Buzz subcommand")
                success()
            }*/
        }
        executes {
            sendMessageToCause("Baz Command")
            success()
        }
    }
    command("razz") {
        //Commands also need to take argument to be useful!
        //We can do that by using a UnaryPlus "+"
        //This command will take a boolean
        +CommonParameters.BOOLEAN
        executes {
            sendMessageToCause("Razz command selected : ${requireOne(CommonParameters.BOOLEAN)}")
            success()
        }
    }
    command("rizz") {
        //Simple param
        val customParam = "key" withType Parameter.string()
        //Simple flag : -f/--force
        val customFlag = "force" buildFlag "plugin.flag.force"

        subcommand("dazzle") {
            val optionalParam = Parameter.world().key("world").optional().build()
            val anotherFlag = ("s" asFlag "plugin.flag.s").aliases("sour", "crank").build()
            executes {
                val param = one(optionalParam)
                if (!param.isPresent) {
                    sendMessageToCause("No world!")
                } else {
                    sendMessageToCause("World! ${param.get().key().formatted()}")
                }
                if (hasFlag(customFlag) && hasFlag(anotherFlag)) {
                    sendMessageToCause("Double flag mode!")
                } else {
                    sendMessageToCause("No flags!")
                }
                success()
            }
        }

        //Remember to use the "+"!
        +customParam
        +customFlag
        executes {
            if (hasFlag(customFlag)) {
                val param = requireOne(customParam)
                sendMessageToCause("You've forced our hand! -- Now you'll get it: ", param)
            } else {
                sendMessageToCause("We weren't forced into this, nothing to report!")
            }
            success()
        }
    }
}


ScriptCommandManager.command("simple") {
    executes {
        Logger.info("Simple Command runs!")
        success()
    }
}


//This is just to handle impl found here
//You may ignore this, it is not necessary for your scripts
fun CommandContext.sendMessageToCause(vararg msg: String) {
    val firstToken = msg.first()
    val remainingTokens = msg.filterNot { it.contentEquals(firstToken) }
    val baseMessage = Component.text(firstToken)
    var finalMessage: TextComponent = baseMessage
    remainingTokens.forEach { finalMessage = finalMessage.append(Component.text(it)) }
    cause().sendMessage(Identity.nil(), finalMessage)
}