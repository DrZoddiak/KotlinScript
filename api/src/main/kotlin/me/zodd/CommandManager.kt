package me.zodd

import me.zodd.annotations.ScriptDsl
import net.kyori.adventure.text.Component
import org.spongepowered.api.command.Command
import org.spongepowered.api.command.CommandCause
import org.spongepowered.api.command.CommandResult
import org.spongepowered.api.command.parameter.CommandContext
import org.spongepowered.api.command.parameter.Parameter
import org.spongepowered.api.command.parameter.managed.Flag
import java.util.function.Predicate

@ScriptDsl
class CommandManager : DslArgument, DslContext {

    operator fun invoke(initializer: CommandManager.() -> Unit): List<DslCommand> {
        this.initializer()
        return CommandBuilder.builtCommands
    }

    fun command(name: String, initializer: CommandBuilder.(name: String) -> Unit): DslCommand {
        val builder = CommandBuilder()
        builder.aliases += name
        builder.initializer(name)
        return builder.buildCommand()
    }
}

@ScriptDsl
class CommandBuilder : DslArgument, DslContext {

    companion object {
        internal val builtCommands = mutableListOf<DslCommand>()
    }

    var aliases: MutableList<String> = mutableListOf()
    var description: String = ""
    var permission: String = ""
    var terminal: Boolean = false
    var executionRequirement: Predicate<CommandCause> = Predicate { true }

    private var commandExecutor: CommandContext.() -> CommandResult =
        { error(Component.text("Command executor not registered")) }

    private var parameters = mutableListOf<Parameter>()
    private var subcommands = mutableListOf<DslCommand>()
    private var flags = mutableListOf<Flag>()

    operator fun <T : Any> Parameter.Value<T>.unaryPlus() {
        parameters += this
    }

    operator fun Flag.unaryPlus() {
        flags += this
    }

    fun subcommand(name: String, initializer: CommandBuilder.(cmd: String) -> Unit): DslCommand {
        val subBuilder = CommandBuilder()
        subBuilder.aliases += name
        subBuilder.initializer(name)
        val command = subBuilder.buildCommand()
        subcommands += command
        return command
    }

    fun executes(exec: CommandContext.() -> CommandResult) {
        commandExecutor = exec
    }

    internal fun buildCommand(): DslCommand {
        val spongeCommandBuilder = Command.builder()

        spongeCommandBuilder.apply {
            subcommands.forEach {
                addChild(it.command, it.aliases)
            }
            addParameters(parameters)
            addFlags(flags)
            executionRequirements(executionRequirement)
            terminal(terminal)
            executor(commandExecutor)
            shortDescription(Component.text(description))
            permission(permission)
        }
        val command = DslCommand(aliases, spongeCommandBuilder.build())
        builtCommands.add(command)

        return command
    }
}


sealed interface DslContext {
    infix fun <T> CommandContext.requireOne(param: Parameter.Value<T>): T = this.requireOne(param)

    fun CommandContext.success(): CommandResult = CommandResult.success()

    infix fun CommandContext.error(errorMessage: Component): CommandResult = CommandResult.error(errorMessage)

    infix fun CommandContext.hasFlag(flag: Flag) = this.hasFlag(flag)
}

sealed interface DslFlag {
    infix fun String.buildFlag(permission: String): Flag {
        return asFlag(permission).build()
    }

    infix fun String.asFlag(permission: String): Flag.Builder {
        return Flag.builder().aliases(this.split(",")).setPermission(permission)
    }
}

sealed interface DslParameter {
    infix fun <T> String.withType(type: Parameter.Value.Builder<T>): Parameter.Value<T> {
        return type.key(this).build()
    }

    infix fun <T> Parameter.Value.Builder<T>.keyedWith(key: String): Parameter.Value<T> {
        return this.key(key).build()
    }
}

interface DslArgument : DslParameter, DslFlag

data class DslCommand(
    val aliases: List<String>,
    val command: Command.Parameterized,
) {
    val baseAlias = aliases[0]
    val remainingAliases = aliases.filterNot { it.contentEquals(baseAlias) }.toTypedArray()
}