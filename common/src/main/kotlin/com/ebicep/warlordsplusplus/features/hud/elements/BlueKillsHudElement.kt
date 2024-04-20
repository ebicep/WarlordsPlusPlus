package com.ebicep.warlordsplusplus.features.hud.elements

import com.ebicep.warlordsplusplus.config.Config
import com.ebicep.warlordsplusplus.config.HudElementValues
import com.ebicep.warlordsplusplus.detectors.TotalKillsDetector
import com.ebicep.warlordsplusplus.game.GameStateManager
import com.ebicep.warlordsplusplus.util.ComponentBuilder
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.MutableComponent

data object BlueKillsHudElement : AbstractHighlightedHudElement() {

    override fun shouldRender(): Boolean {
        return GameStateManager.inGame
    }

    override fun getConfigValues(): HudElementValues {
        return Config.values.hudElements.blueKills
    }

    override fun getComponent(): MutableComponent {
        return ComponentBuilder("Blue Kills: ${TotalKillsDetector.blueKills()}", ChatFormatting.BLUE).create()
    }

}