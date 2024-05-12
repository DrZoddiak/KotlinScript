package me.zodd

import org.bukkit.scheduler.BukkitScheduler

fun BukkitScheduler.scheduleTask(delay: Long = 0, period: Long = -1, runnable: () -> Unit) {
    scheduleSyncRepeatingTask(Container, runnable, delay, period)
}
