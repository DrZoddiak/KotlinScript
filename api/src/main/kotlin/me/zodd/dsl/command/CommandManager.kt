package me.zodd.dsl.command

import me.zodd.RegistrationHelper
import me.zodd.annotations.ScriptDsl

@ScriptDsl
object CommandManager : CommandArgument, CommandContext {

    operator fun invoke(initializer: CommandManager.() -> Unit): List<DslCommand> {
        this.initializer()
        RegistrationHelper.registerCommand(CommandBuilder.builtCommands)
        return CommandBuilder.builtCommands
    }

    fun command(name: String, initializer: CommandBuilder.(name: String) -> Unit): DslCommand {
        val builder = CommandBuilder()
        builder.aliases += name
        builder.initializer(name)
        return builder.buildCommand()
    }
}