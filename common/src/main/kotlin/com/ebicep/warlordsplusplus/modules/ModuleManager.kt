package com.ebicep.warlordsplusplus.modules

import com.ebicep.warlordsplusplus.modules.chat.PrintStatsAfterGame
import com.ebicep.warlordsplusplus.modules.render.CooldownRenderer
import com.ebicep.warlordsplusplus.modules.scoreboard.WarlordsPlusPlusScoreBoard

object ModuleManager {

    private val modules = mutableListOf<Module>()

    init {
        with(modules) {
            add(PrintStatsAfterGame)
            add(WarlordsPlusPlusScoreBoard)
            add(CooldownRenderer)
        }
    }

}