package me.zodd

import org.bukkit.event.Event
import org.bukkit.event.Listener
import org.bukkit.event.server.ServerLoadEvent

// === EVENTS ===

// INTERACTION


//fun onBlockLeftClick(executor: (Listener,PlayerInteractEvent) -> Unit) = RegistrationHelper.registerListener(executor)
//
//fun onBlockRightClick(executor: InteractBlockEvent.Secondary.() -> Unit) = RegistrationHelper.registerListener(executor)
//
//fun onEntityLeftClick(executor: InteractEntityEvent.Primary.() -> Unit) = RegistrationHelper.registerListener(executor)
//
//fun onEntityRightClick(executor: InteractEntityEvent.Secondary.() -> Unit) =
//    RegistrationHelper.registerListener(executor)
//
//// MOVE
//
//fun onEntityMove(executor: MoveEntityEvent.() -> Unit) = RegistrationHelper.registerListener(executor)
//
//fun onPlayerMove(executor: MoveEntityEvent.() -> Unit) =
//    onEntityMove { if (entity().type() == EntityTypes.PLAYER) executor() }
//
//// BLOCKS
//
//fun onBlockChange(executor: ChangeBlockEvent.() -> Unit) = RegistrationHelper.registerListener(executor)
//
//// ENTITIES
//
//fun onEntitySpawn(executor: SpawnEntityEvent.() -> Unit) = RegistrationHelper.registerListener(executor)
//
//fun onEntityDamage(executor: DamageEntityEvent.() -> Unit) = RegistrationHelper.registerListener(executor)
//
//fun onEntityRemove(executor: DestructEntityEvent.() -> Unit) = RegistrationHelper.registerListener(executor)
//
//fun onEntityDeath(executor: DestructEntityEvent.Death.() -> Unit) = RegistrationHelper.registerListener(executor)
//
//// PLAYERS
//
//fun onPlayerLogin(executor: ServerSideConnectionEvent.Login.() -> Unit) = RegistrationHelper.registerListener(executor)
//
//fun onPlayerJoin(executor: ServerSideConnectionEvent.Join.() -> Unit) = RegistrationHelper.registerListener(executor)
//
//fun onPlayerLeave(executor: ServerSideConnectionEvent.Disconnect.() -> Unit) =
//    RegistrationHelper.registerListener(executor)
//
//fun onPlayerDamage(executor: DamageEntityEvent.() -> Unit) = onEntityDamage { if (entity() is Player) executor() }
//
//fun onPlayerDeath(executor: DestructEntityEvent.Death.() -> Unit) =
//    onEntityDeath { if (entity() is Player) executor() }
//
//// SERVER
//
//fun onServerStarting(executor: (ServerLoadEvent) -> Unit) = RegistrationHelper.registerListener(executor)
//
//fun onServerStarted(executor: StartedEngineEvent<Engine>.() -> Unit) = RegistrationHelper.registerListener(executor)
//
//fun onServerStopping(executor: StoppingEngineEvent<Engine>.() -> Unit) = RegistrationHelper.registerListener(executor)
//
//fun onServerStopped(executor: StoppedGameEvent.() -> Unit) = RegistrationHelper.registerListener(executor)
//
//// COMMANDS
//
//fun onRegisterCommandsParameterized(executor: RegisterCommandEvent<Parameterized>.() -> Unit) =
//    RegistrationHelper.registerListener(executor)
//
//// OTHERS
//
////fun onScriptsUnload(executor: UnloadScriptsEvent.() -> Unit) = registerListener(executor)
//