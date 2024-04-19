package com.ebicep.warlordsplusplus.features.hud.elements

import com.ebicep.chatplus.events.EventBus
import com.ebicep.warlordsplusplus.events.WarlordsGameEvents
import com.ebicep.warlordsplusplus.features.hud.AbstractHudElement
import com.ebicep.warlordsplusplus.util.ComponentBuilder
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.MutableComponent

object StreakHudElement : AbstractHudElement(0, 0) {

    private var streak = 0

    init {
        EventBus.register<WarlordsGameEvents.GameWinEvent> {
            if (streak > 0) {
                streak++
            } else {
                streak = 1
            }
        }
        EventBus.register<WarlordsGameEvents.GameLossEvent> {
            if (streak < 0) {
                streak--
            } else {
                streak = -1
            }
        }
    }

    override fun shouldRender(): Boolean {
        return true
    }

    override fun getText(): MutableComponent {
        val text: String
        val color: ChatFormatting
        if (streak > 0) {
            text = "Win Streak: $streak"
            color = ChatFormatting.GREEN
        } else if (streak < 0) {
            text = "Loss Streak: ${-streak}"
            color = ChatFormatting.RED
        } else {
            text = "No Streak"
            color = ChatFormatting.WHITE
        }
        return ComponentBuilder(text, color).create()
    }


}