package me.zodd

import org.apache.logging.log4j.Logger
import org.spongepowered.api.Server
import org.spongepowered.api.Sponge
import org.spongepowered.api.command.manager.CommandManager
import org.spongepowered.api.event.EventManager
import org.spongepowered.api.plugin.PluginManager
import org.spongepowered.api.scheduler.Scheduler
import org.spongepowered.api.service.ServiceProvider

inline val Server: Server get() = Sponge.server()
inline val ScriptPlugin: Host.Companion get() = Host
inline val PluginManager: PluginManager get() = Sponge.pluginManager()
inline val CommandManager: CommandManager get() = Sponge.server().commandManager()
inline val EventManager: EventManager get() = Sponge.eventManager()
inline val ServiceServerManager: ServiceProvider.ServerScoped get() = Sponge.server().serviceProvider()
inline val ServiceGameManager: ServiceProvider.GameScoped get() = Sponge.serviceProvider()
inline val Scheduler: Scheduler get() = Sponge.server().scheduler()
inline val AsyncScheduler: Scheduler get() = Sponge.asyncScheduler()
inline val Logger: Logger get() = Host.logger
inline val ScriptCommandManager: me.zodd.dsl.command.CommandManager get() = me.zodd.dsl.command.CommandManager()

