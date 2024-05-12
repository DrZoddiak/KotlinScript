package me.zodd

import net.kyori.adventure.sound.Sound
import org.bukkit.entity.Entity
import org.bukkit.scheduler.BukkitScheduler

fun BukkitScheduler.scheduleTask(delay: Long = 0, period: Long = -1, runnable: () -> Unit) {
    scheduleSyncRepeatingTask(Container, runnable, delay, period)
}

fun soundOf(fn: Sound.Builder.() -> Unit) = Sound.sound().apply(fn).build()

fun Entity.createExplosionAt(power: Float, setFire: Boolean = false, breakBlocks: Boolean = true) {
    world.createExplosion(this, power, setFire, breakBlocks)
}