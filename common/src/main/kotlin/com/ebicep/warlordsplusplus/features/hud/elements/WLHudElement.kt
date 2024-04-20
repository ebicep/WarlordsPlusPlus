package com.ebicep.warlordsplusplus.features.hud.elements

import com.ebicep.chatplus.events.EventBus
import com.ebicep.warlordsplusplus.config.Config
import com.ebicep.warlordsplusplus.config.HudElementValues
import com.ebicep.warlordsplusplus.events.WarlordsGameEvents
import com.ebicep.warlordsplusplus.game.GameStateManager
import com.ebicep.warlordsplusplus.util.ComponentBuilder
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.MutableComponent

data object WLHudElement : AbstractHighlightedHudElement() {

    private var wins = 0
    private var losses = 0

    init {
        EventBus.register<WarlordsGameEvents.GameWinEvent> {
            wins++
        }
        EventBus.register<WarlordsGameEvents.GameLossEvent> {
            losses++
        }
    }

    override fun shouldRender(): Boolean {
        return GameStateManager.notInGame
    }

    override fun getConfigValues(): HudElementValues {
        return Config.values.hudElements.wl
    }

    override fun getComponent(): MutableComponent {
        return ComponentBuilder("Total W/L: ", ChatFormatting.WHITE)
            .append("$wins", ChatFormatting.GREEN)
            .append("/")
            .append("$losses", ChatFormatting.RED)
            .create()
    }


}