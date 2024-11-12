package me.zodd.dsl.command

import me.zodd.annotations.ScriptDsl
import net.kyori.adventure.text.Component
import org.spongepowered.api.command.Command
import org.spongepowered.api.command.CommandCause
import org.spongepowered.api.command.CommandResult
import org.spongepowered.api.command.parameter.Parameter
import org.spongepowered.api.command.parameter.managed.Flag
import org.spongepowered.api.command.parameter.CommandContext as SpongeContext
import java.util.function.Predicate

@ScriptDsl
class CommandBuilder : CommandArgument, CommandContext {

    companion object {
        internal val builtCommands = mutableListOf<DslCommand>()
    }

    var aliases: MutableList<String> = mutableListOf()
    var description: String = ""
    var permission: String? = null
    var terminal: Boolean? = null
    var executionRequirement: Predicate<CommandCause>? = null

    private var commandExecutor: (SpongeContext.() -> CommandResult)? = null

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

    fun executes(exec: SpongeContext.() -> CommandResult) {
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
            shortDescription(Component.text(description))
            executionRequirement?.let { executionRequirements(it) }
            terminal?.let { terminal(it) }
            commandExecutor?.let { executor(it) }
            permission?.let { permission(it) }
        }
        val command = DslCommand(aliases, spongeCommandBuilder.build())
        builtCommands.add(command)

        return command
    }
}