import io.leangen.geantyref.TypeToken
import org.spongepowered.api.Sponge
import org.spongepowered.api.event.Event
import org.spongepowered.api.event.EventListenerRegistration
import org.spongepowered.api.event.Order

object RegistrationHelper {
    fun <T : Event> registerListener(eventClass: TypeToken<T>, executor: (T) -> Unit) {
        Sponge.eventManager().registerListener(
            EventListenerRegistration.builder(eventClass)
                .order(Order.DEFAULT)
                .listener(executor::invoke)
                .plugin(Host.container)
                .build()
        )
    }

    inline fun <reified T : Event> registerListener(noinline executor: T.() -> Unit) =
        registerListener(typeToken<T>(), executor)

    inline fun <reified T : Event> typeToken() = object : TypeToken<T>() {}
}