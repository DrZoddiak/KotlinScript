import me.zodd.BukkitTaskScheduler
import me.zodd.onEntityDamageByEntity
import me.zodd.scheduleTask
import net.kyori.adventure.sound.Sound
import org.bukkit.entity.EntityType

onEntityDamageByEntity {
    if (damager.type != EntityType.PLAYER || entityType != EntityType.BEE) {
        return@onEntityDamageByEntity
    }

    val bee = entity

    val sound = Sound.sound(
        org.bukkit.Sound.ENTITY_CREEPER_PRIMED.key(),
        Sound.Source.HOSTILE,
        6f,
        1f,
    )

    bee.playSound(sound)

    BukkitTaskScheduler.scheduleTask(60) {
        bee.world.createExplosion(bee, 3f)
    }
}