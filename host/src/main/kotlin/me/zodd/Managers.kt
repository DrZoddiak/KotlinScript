package me.zodd

import org.apache.logging.log4j.Logger
import org.spongepowered.api.Server
import org.spongepowered.api.Sponge
import org.spongepowered.api.command.manager.CommandManager
import org.spongepowered.api.event.EventManager
import org.spongepowered.api.plugin.PluginManager
import org.spongepowered.api.scheduler.Scheduler
import org.spongepowered.api.service.ServiceProvider

val Server: Server get() = Sponge.server()
val ScriptPlugin: Host.Companion get() = Host
val PluginManager: PluginManager get() = Sponge.pluginManager()
val CommandManager: CommandManager get() = Sponge.server().commandManager()
val EventManager: EventManager get() = Sponge.eventManager()
val ServiceServerManager: ServiceProvider.ServerScoped get() = Sponge.server().serviceProvider()
val ServiceGameManager: ServiceProvider.GameScoped get() = Sponge.serviceProvider()
val Scheduler: Scheduler get() = Sponge.server().scheduler()
val AsyncScheduler: Scheduler get() = Sponge.asyncScheduler()
val Logger: Logger get() = Host.logger