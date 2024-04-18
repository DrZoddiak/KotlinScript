package me.zodd

import org.bukkit.Bukkit
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.plugin.Plugin

object RegistrationHelper {
    inline fun <reified E : Event> registerListener(
        noinline executor: (event: E) -> Unit,
        eventClass: Class<out E> = E::class.java,
        listener: Listener = object : Listener {},
        priority: EventPriority = EventPriority.NORMAL,
        plugin: Plugin = API.container
    ) {

        Bukkit.getPluginManager().registerEvent(
            eventClass,
            listener,
            priority,
            { _, event -> executor.invoke(event as E) },
            plugin
        )
    }

}

