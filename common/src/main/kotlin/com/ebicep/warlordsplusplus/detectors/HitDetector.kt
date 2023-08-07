package com.ebicep.warlordsplusplus.detectors

import com.ebicep.warlordsplusplus.event.WarlordsPlayerEvents
import com.ebicep.warlordsplusplus.event.WarlordsPlayerEventsImpl
import com.ebicep.warlordsplusplus.game.GameStateManager
import dev.architectury.event.CompoundEventResult
import dev.architectury.event.events.client.ClientSystemMessageEvent
import net.minecraft.network.chat.Component

object HitDetector {

    init {
        ClientSystemMessageEvent.RECEIVED.register { component: Component ->
            if (GameStateManager.notInGame) {
                return@register CompoundEventResult.pass()
            }
            val msg: String = component.string
            if (msg.contains("You hit")) {
                WarlordsPlayerEventsImpl.HIT_EVENT.invoker().onHit(
                    WarlordsPlayerEvents.HitEvent(
                        msg.substring(msg.indexOf("hit ") + 4, msg.indexOf("for") - 1),
                        GameStateManager.minute
                    )
                )
            }
            CompoundEventResult.pass()
        }
    }

}