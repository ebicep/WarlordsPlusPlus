package com.ebicep.warlordsplusplus.detectors

import com.ebicep.warlordsplusplus.event.WarlordsGameEvents
import com.ebicep.warlordsplusplus.event.WarlordsGameEventsImpl
import com.ebicep.warlordsplusplus.game.GameModes
import com.ebicep.warlordsplusplus.game.GameStateManager
import com.ebicep.warlordsplusplus.game.OtherWarlordsPlayers

object RespawnTimerDetector : Detector {

    var respawnTimer = 0

    init {
        WarlordsGameEventsImpl.RESET_EVENT.register { e: WarlordsGameEvents.ResetEvent ->
            respawnTimer = 18
        }
        WarlordsGameEventsImpl.SECOND_EVENT.register { e: WarlordsGameEvents.SecondEvent ->
            respawnTimer--
            if (GameStateManager.currentGameMode == GameModes.CTF) {
                val second = GameStateManager.second
                if (respawnTimer < 0) {
                    respawnTimer = 0
                }
                if (second % 12 == 0) {
                    respawnTimer = 12
                }
            } else if (GameStateManager.currentGameMode == GameModes.DOM) {
                if (respawnTimer < 0) {
                    respawnTimer = 17
                }
            }
            OtherWarlordsPlayers.playersMap.forEach { (_, value) ->
                if (!value.isDead) {
                    return@forEach
                }
                if (value.respawn == -1) {
                    return@forEach
                }
                value.respawn--
                if (value.respawn == 0) {
                    value.isDead = false
                    value.respawn = -1
                }
            }
        }
    }

}