package me.zodd

import org.bukkit.Bukkit
import org.bukkit.Server
import org.bukkit.plugin.PluginManager

val BukkitServer : Server get() = Bukkit.getServer()
val BukkitPluginManager : PluginManager get() = Bukkit.getPluginManager()
val BukkitServicesManager get() = Bukkit.getServicesManager()
val BukkitTaskScheduler get() = Bukkit.getScheduler()
val BukkitAsyncTaskScheduler get() = Bukkit.getAsyncScheduler()
val Container get() = API.container
val Logger get() = API.logger