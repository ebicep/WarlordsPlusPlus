package com.ebicep.warlordsplusplus.features

import com.ebicep.warlordsplusplus.features.chat.PrintStatsAfterGame
import com.ebicep.warlordsplusplus.features.hud.HudElementManager
import com.ebicep.warlordsplusplus.features.player.cooldown.WarlordsPvEPacketHandler
import com.ebicep.warlordsplusplus.features.scoreboard.WarlordsPlusPlusScoreBoard
import com.ebicep.warlordsplusplus.features.world.WorldRenderManager

object FeatureManager {

    private val features = mutableListOf<Feature>()

    init {
        with(features) {
            add(HudElementManager)
            add(WorldRenderManager)
            add(PrintStatsAfterGame)
            add(WarlordsPlusPlusScoreBoard)
            add(ShowEnergyOnHorse)
            add(RenderVignette)


            add(WarlordsPvEPacketHandler)
        }
    }

}