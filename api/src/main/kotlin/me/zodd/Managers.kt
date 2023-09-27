package me.zodd

import org.apache.logging.log4j.Logger
import org.spongepowered.api.Server
import org.spongepowered.api.Sponge
import org.spongepowered.api.command.manager.CommandManager
import org.spongepowered.api.event.EventManager
import org.spongepowered.api.plugin.PluginManager
import org.spongepowered.api.scheduler.Scheduler
import org.spongepowered.api.service.ServiceProvider
import org.spongepowered.plugin.PluginContainer

inline val SpongeServer: Server get() = Sponge.server()
inline val SpongePluginManager: PluginManager get() = Sponge.pluginManager()
inline val SpongeCommandManager: CommandManager get() = Sponge.server().commandManager()
inline val SpongeEventManager: EventManager get() = Sponge.eventManager()
inline val SpongeServerServiceManager: ServiceProvider.ServerScoped get() = Sponge.server().serviceProvider()
inline val SpongeGameServiceManager: ServiceProvider.GameScoped get() = Sponge.serviceProvider()
inline val Scheduler: Scheduler get() = Sponge.server().scheduler()
inline val SpongeAsyncScheduler: Scheduler get() = Sponge.asyncScheduler()
inline val Container: PluginContainer get() = API.container
inline val Logger: Logger get() = API.logger
inline val ScriptCommandManager: me.zodd.CommandManager get() = CommandManager()