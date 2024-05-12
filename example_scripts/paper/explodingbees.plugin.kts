import me.zodd.BukkitTaskScheduler
import me.zodd.createExplosionAt
import me.zodd.onEntityDamageByEntity
import me.zodd.scheduleTask
import me.zodd.soundOf
import net.kyori.adventure.sound.Sound
import org.bukkit.entity.EntityType

onEntityDamageByEntity {
    if (damager.type != EntityType.PLAYER || entityType != EntityType.BEE) {
        return@onEntityDamageByEntity
    }

    val bee = entity

    val sound = soundOf {
        type(org.bukkit.Sound.ENTITY_CREEPER_PRIMED)
        source(Sound.Source.HOSTILE)
        volume(6f)
        pitch(1f)
    }

    bee.playSound(sound)

    BukkitTaskScheduler.scheduleTask(60) {
        bee.createExplosionAt(3f)
    }
}