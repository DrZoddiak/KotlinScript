package me.zodd

import com.destroystokyo.paper.event.entity.EntityRemoveFromWorldEvent
import io.papermc.paper.event.entity.EntityMoveEvent
import org.bukkit.entity.EntityType
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockMultiPlaceEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.entity.EntitySpawnEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerLoginEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.event.server.ServerLoadEvent

// INTERACTION

// === EVENTS ===

fun onPlayerInteract(executor: PlayerInteractEvent.() -> Unit) = RegistrationHelper.registerListener(executor)

fun onBlockInteract(fn: PlayerInteractEvent.() -> Unit) = onPlayerInteract {
    if (hasBlock()) {
        fn()
    }
}

fun onBlockLeftClick(fn: () -> Unit) = onBlockInteract {
    if (action.isLeftClick) {
        fn()
    }
}

fun onBlockRightClick(fn: () -> Unit) = onBlockInteract {
    if (action.isRightClick) {
        fn()
    }
}

fun onEntityInteract(fn: PlayerInteractEntityEvent.() -> Unit) = RegistrationHelper.registerListener(fn)
fun onEntityRightClick(fn: PlayerInteractEntityEvent.() -> Unit) = onEntityInteract(fn)

fun onEntityDamageByEntity(fn: EntityDamageByEntityEvent.() -> Unit) = RegistrationHelper.registerListener(fn)
fun onEntityLeftClick(fn: EntityDamageByEntityEvent.() -> Unit) = onEntityDamageByEntity(fn)

//// MOVE

fun onEntityMove(fn: EntityMoveEvent.() -> Unit) = RegistrationHelper.registerListener(fn)

fun onPlayerMove(fn: PlayerMoveEvent.() -> Unit) = RegistrationHelper.registerListener(fn)

//// BLOCKS

fun onBlockBreak(fn: BlockBreakEvent.() -> Unit) = RegistrationHelper.registerListener(fn)

fun onBlockPlace(fn: BlockPlaceEvent.() -> Unit) = RegistrationHelper.registerListener(fn)

fun onMultiBlockPlace(fn: BlockMultiPlaceEvent.() -> Unit) = RegistrationHelper.registerListener(fn)

//// ENTITIES

fun onEntitySpawn(fn: EntitySpawnEvent.() -> Unit) = RegistrationHelper.registerListener(fn)

fun onEntityDamage(executor: EntityDamageEvent.() -> Unit) = RegistrationHelper.registerListener(executor)

fun onEntityDeath(fn: EntityDeathEvent.() -> Unit) = RegistrationHelper.registerListener(fn)

fun onEntityRemove(fn: EntityRemoveFromWorldEvent.() -> Unit) = RegistrationHelper.registerListener(fn)

//// PLAYERS

fun onPlayerLogin(executor: PlayerLoginEvent.() -> Unit) = RegistrationHelper.registerListener(executor)

fun onPlayerJoin(executor: PlayerJoinEvent.() -> Unit) = RegistrationHelper.registerListener(executor)

fun onPlayerLeave(executor: PlayerQuitEvent.() -> Unit) = RegistrationHelper.registerListener(executor)

fun onPlayerDamage(fn: EntityDamageEvent.() -> Unit) = onEntityDamage {
    if (entityType == EntityType.PLAYER) {
        fn()
    }
}

fun onPlayerDeath(fn: PlayerDeathEvent.() -> Unit) = RegistrationHelper.registerListener(fn)

//// SERVER

fun onServerLoad(executor: ServerLoadEvent.() -> Unit) = RegistrationHelper.registerListener(executor)

//// OTHERS

//// fun onScriptsUnload(executor: UnloadScriptsEvent.() -> Unit) = registerListener(executor)
