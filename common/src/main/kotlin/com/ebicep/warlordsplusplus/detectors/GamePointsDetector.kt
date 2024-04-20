package com.ebicep.warlordsplusplus.detectors

import com.ebicep.chatplus.events.EventBus
import com.ebicep.warlordsplusplus.events.WarlordsGameEvents
import com.ebicep.warlordsplusplus.game.GameModes
import com.ebicep.warlordsplusplus.game.GameStateManager
import com.ebicep.warlordsplusplus.util.ScoreboardUtils
import dev.architectury.event.events.client.ClientTickEvent

object GamePointsDetector : Detector {

    var bluePoints = 0
    var redPoints = 0

    init {
        EventBus.register<WarlordsGameEvents.ResetEvent> {
            bluePoints = 0
            redPoints = 0
        }
        ClientTickEvent.CLIENT_POST.register {
            if (GameStateManager.notInGame) {
                return@register
            }
            var pointsIndex = -1
            when (GameStateManager.currentGameMode) {
                GameModes.CTF, GameModes.DOM -> {
                    pointsIndex = 13
                }

                GameModes.TDM -> {
                    pointsIndex = 11
                }

                else -> {

                }
            }
            if (pointsIndex == -1) {
                return@register
            }

            ScoreboardUtils.getAt(pointsIndex)?.let {
                bluePoints = getPoints(it)
            }
            ScoreboardUtils.getAt(pointsIndex - 1)?.let {
                redPoints = getPoints(it)
            }
        }
    }

    // RED: 195/1000
    private fun getPoints(it: String) = it.substring(it.indexOf(": ") + 2, it.indexOf("/")).toInt()


}