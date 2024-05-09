package me.zodd

import org.bukkit.Bukkit
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.plugin.Plugin

object RegistrationHelper {
    /**
     * A simple event listener register wrapper
     * @param E Where E is the Event to register
     * @param executor Executor runs when the event fires
     * @param eventClass the event class to register
     * @param listener A listener Dummy object
     * @param priority The priority for the event to fire
     * @param plugin The Plugin instance to register the listener to
     */
    inline fun <reified E : Event> registerListener(
        noinline executor: E.() -> Unit,
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

