package com.ebicep.warlordsplusplus.features.hud.elements

import com.ebicep.warlordsplusplus.config.Config
import com.ebicep.warlordsplusplus.config.HudElementValues
import com.ebicep.warlordsplusplus.game.GameStateManager
import com.ebicep.warlordsplusplus.game.WarlordsPlayer
import com.ebicep.warlordsplusplus.util.ComponentBuilder
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.MutableComponent

data object HealingReceivedHudElement : AbstractHighlightedHudElement() {

    override fun shouldRender(): Boolean {
        return GameStateManager.inGame
    }

    override fun getConfigValues(): HudElementValues {
        return Config.values.hudElements.healingReceived
    }

    override fun getComponent(): MutableComponent {
        return ComponentBuilder("Healing Received: ${WarlordsPlayer.healingReceivedCounter}", ChatFormatting.DARK_GREEN).create()
    }

}
