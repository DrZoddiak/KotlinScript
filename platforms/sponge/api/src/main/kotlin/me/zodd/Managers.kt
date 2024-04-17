package me.zodd

import me.zodd.dsl.command.CommandManager
import org.apache.logging.log4j.Logger
import org.spongepowered.api.Server
import org.spongepowered.api.Sponge
import org.spongepowered.api.event.EventManager
import org.spongepowered.api.plugin.PluginManager
import org.spongepowered.api.scheduler.Scheduler
import org.spongepowered.api.service.ServiceProvider
import org.spongepowered.plugin.PluginContainer
import org.spongepowered.api.command.manager.CommandManager as SpongeCommandManager

val SpongeServer: Server get() = Sponge.server()
val SpongePluginManager: PluginManager get() = Sponge.pluginManager()
val SpongeCommandManager: SpongeCommandManager get() = Sponge.server().commandManager()
val SpongeEventManager: EventManager get() = Sponge.eventManager()
val SpongeServerServiceManager: ServiceProvider.ServerScoped get() = Sponge.server().serviceProvider()
val SpongeGameServiceManager: ServiceProvider.GameScoped get() = Sponge.serviceProvider()
val SpongeScheduler: Scheduler get() = Sponge.server().scheduler()
val SpongeAsyncScheduler: Scheduler get() = Sponge.asyncScheduler()
val Container: PluginContainer get() = API.container
val Logger: Logger get() = API.logger
val ScriptCommandManager: CommandManager get() = CommandManager