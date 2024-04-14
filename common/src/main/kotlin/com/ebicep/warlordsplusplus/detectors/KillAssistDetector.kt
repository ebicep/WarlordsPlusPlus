package com.ebicep.warlordsplusplus.detectors

import com.ebicep.chatplus.events.EventBus
import com.ebicep.warlordsplusplus.events.WarlordsPlayerEvents

import com.ebicep.warlordsplusplus.game.GameStateManager
import dev.architectury.event.CompoundEventResult
import dev.architectury.event.events.client.ClientSystemMessageEvent
import net.minecraft.client.Minecraft
import net.minecraft.network.chat.Component

object KillAssistParser : Detector {

    init {
        ClientSystemMessageEvent.RECEIVED.register { component: Component ->
            if (GameStateManager.notInGame) {
                return@register CompoundEventResult.pass()
            }
            try {
                val textMessage: String = component.string
                when {
                    textMessage.contains("was killed by") -> {
                        val player = textMessage.substring(textMessage.indexOf("by") + 3)
                        val deathPlayer = textMessage.substring(0, textMessage.indexOf("was") - 1)
                        EventBus.post(
                            WarlordsPlayerEvents.KillEvent(
                                player,
                                deathPlayer
                            )
                        )
                    }

                    textMessage.contains("You were killed") -> {
                        val player = textMessage.substring(textMessage.indexOf("by ") + 3)
                        val deathPlayer = Minecraft.getInstance().player!!.scoreboardName
                        EventBus.post(
                            WarlordsPlayerEvents.KillEvent(player, deathPlayer)
                        )
                    }

                    textMessage.contains("You killed") -> {
                        val deathPlayer = textMessage.substring(textMessage.indexOf("killed ") + 7)
                        val player = Minecraft.getInstance().player!!.scoreboardName
                        EventBus.post(
                            WarlordsPlayerEvents.KillEvent(player, deathPlayer)
                        )
                    }

                    textMessage.contains("You assisted") -> {
                        val playerThatStoleKill =
                            textMessage.substring(textMessage.indexOf("You assisted ") + 13, textMessage.indexOf("in ") - 1)
                        EventBus.post(
                            WarlordsPlayerEvents.KillStealEvent(playerThatStoleKill)
                        )
                    }
                }
            } catch (throwable: Throwable) {
                throwable.printStackTrace()
            }
            CompoundEventResult.pass()
        }
    }

}
