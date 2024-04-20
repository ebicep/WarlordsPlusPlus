package com.ebicep.warlordsplusplus.detectors

import com.ebicep.chatplus.events.EventBus
import com.ebicep.warlordsplusplus.events.WarlordsGameEvents
import com.ebicep.warlordsplusplus.events.WarlordsPlayerEvents
import com.ebicep.warlordsplusplus.game.GameModes
import com.ebicep.warlordsplusplus.game.GameStateManager
import com.ebicep.warlordsplusplus.game.OtherWarlordsPlayers
import com.ebicep.warlordsplusplus.util.Team

object TotalKillsDetector : Detector {

    private var blueKills = 0
    private var redKills = 0

    init {
        EventBus.register<WarlordsGameEvents.ResetEvent> {
            blueKills = 0
            redKills = 0
        }
        EventBus.register<WarlordsPlayerEvents.KillEvent> {
            OtherWarlordsPlayers.getPlayerByName(it.killer)?.let { killer ->
                if (killer.team == Team.BLUE) {
                    blueKills++
                } else if (killer.team == Team.RED) {
                    redKills++
                }
            }
        }
    }

    fun blueKills(): Int {
        return when (GameStateManager.currentGameMode) {
            GameModes.CTF -> {
                (GamePointsDetector.bluePoints - FlagStateDetector.blueCaps * 250) / 5
            }

            GameModes.TDM -> {
                GamePointsDetector.bluePoints / 5
            }

            else -> {
                blueKills
            }
        }
    }

    fun redKills(): Int {
        return when (GameStateManager.currentGameMode) {
            GameModes.CTF -> {
                (GamePointsDetector.redPoints - FlagStateDetector.redCaps * 250) / 5
            }

            GameModes.TDM -> {
                GamePointsDetector.redPoints / 5
            }

            else -> {
                redKills
            }
        }
    }

}