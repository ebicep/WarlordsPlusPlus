package com.ebicep.warlordsplusplus.detectors

import com.ebicep.chatplus.events.EventBus
import com.ebicep.warlordsplusplus.WarlordsPlusPlus
import com.ebicep.warlordsplusplus.events.WarlordsGameEvents
import com.ebicep.warlordsplusplus.game.GameStateManager
import dev.architectury.event.CompoundEventResult
import dev.architectury.event.events.client.ClientSystemMessageEvent
import net.minecraft.network.chat.Component

object FlagStateDetector : Detector {

    var blueCaps = 0
    var redCaps = 0

    init {
        EventBus.register<WarlordsGameEvents.ResetEvent> {
            blueCaps = 0
            redCaps = 0
        }
        ClientSystemMessageEvent.RECEIVED.register { component: Component ->
            if (GameStateManager.notInGame) {
                return@register CompoundEventResult.pass()
            }
            val unformattedText = component.string
            if (unformattedText.contains(":")) {
                return@register CompoundEventResult.pass()
            }
            val player = unformattedText.substring(0, unformattedText.indexOf(" "))
            when {
                unformattedText.contains(" picked up the ") -> {
                    WarlordsPlusPlus.LOGGER.info("Flag picked up by $player")
                    EventBus.post(WarlordsGameEvents.FlagPickedUpEvent(player))
                }

                unformattedText.contains(" has dropped the ") -> {
                    WarlordsPlusPlus.LOGGER.info("Flag dropped by $player")
                    EventBus.post(WarlordsGameEvents.FlagDroppedEvent(player))
                }

                unformattedText.contains(" has returned the ") -> {
                    WarlordsPlusPlus.LOGGER.info("Flag returned by $player")
                    EventBus.post(WarlordsGameEvents.FlagReturnedEvent(player))
                }

                unformattedText.contains(" captured the ") -> {
                    WarlordsPlusPlus.LOGGER.info("Flag captured by $player")
                    if (unformattedText.contains("BLU")) {
                        redCaps++
                    } else if (unformattedText.contains("RED")) {
                        blueCaps++
                    }
                    EventBus.post(WarlordsGameEvents.FlagCapturedEvent(player))
                }
            }
            // TODO use this detection so it wont detect player chat messages
//            if (component.contains(Component.literal(" has captured the ").withStyle(ChatFormatting.YELLOW))) {
//
//            }

            CompoundEventResult.pass()
        }

    }

}