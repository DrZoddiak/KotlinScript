import me.zodd.onEntityDamage
import org.bukkit.damage.DamageType
import org.bukkit.entity.EntityType

// Prevent fall damage for players with a permission
onEntityDamage {
    if (!entityType.isPlayer()) {
        return@onEntityDamage
    }
    if (damageSource.damageType != DamageType.FALL) {
        return@onEntityDamage
    }
    if (entity.hasPermission("examplescript.falldamage.negate"))

    damage = 0.0
}

// Scripts can use all of Kotlin's regular features
fun EntityType.isPlayer() = this == EntityType.PLAYER

class Bizz
object Foo
typealias Bazz = Foo

