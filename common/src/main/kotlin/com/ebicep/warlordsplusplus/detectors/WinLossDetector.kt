package com.ebicep.warlordsplusplus.detectors

import com.ebicep.chatplus.events.EventBus
import com.ebicep.warlordsplusplus.events.WarlordsGameEvents
import com.ebicep.warlordsplusplus.game.GameStateManager
import com.ebicep.warlordsplusplus.game.WarlordsPlayer
import com.ebicep.warlordsplusplus.util.Team
import dev.architectury.event.CompoundEventResult
import dev.architectury.event.events.client.ClientSystemMessageEvent
import net.minecraft.network.chat.Component

object WinLossDetector : Detector {

    init {
        ClientSystemMessageEvent.RECEIVED.register { component: Component ->
            val unformattedText = component.string

            if (!unformattedText.trimStart().startsWith("Winner")) {
                return@register CompoundEventResult.pass()
            }

            if (
                GameStateManager.inPvE && unformattedText.contains("PLAYERS") ||
                unformattedText.contains("BLU") && WarlordsPlayer.team == Team.BLUE ||
                unformattedText.contains("RED") && WarlordsPlayer.team == Team.RED
            ) {
                EventBus.post(WarlordsGameEvents.GameWinEvent())
            } else {
                EventBus.post(WarlordsGameEvents.GameLossEvent())
            }

            CompoundEventResult.pass()
        }
    }

}