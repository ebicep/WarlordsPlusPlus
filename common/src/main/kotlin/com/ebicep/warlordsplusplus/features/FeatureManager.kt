package com.ebicep.warlordsplusplus.features

import com.ebicep.warlordsplusplus.features.chat.PrintStatsAfterGame
import com.ebicep.warlordsplusplus.features.hud.HudElementManager
import com.ebicep.warlordsplusplus.features.render.WarlordsPvEPacketHandler
import com.ebicep.warlordsplusplus.features.scoreboard.WarlordsPlusPlusScoreBoard

object FeatureManager {

    private val features = mutableListOf<Feature>()

    init {
        with(features) {
            add(HudElementManager)
            add(PrintStatsAfterGame)
            add(WarlordsPlusPlusScoreBoard)
            add(WarlordsPvEPacketHandler)
        }
    }

}