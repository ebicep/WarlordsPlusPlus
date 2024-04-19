package com.ebicep.warlordsplusplus.features.hud.elements

import com.ebicep.chatplus.events.EventBus
import com.ebicep.warlordsplusplus.events.WarlordsGameEvents
import com.ebicep.warlordsplusplus.events.WarlordsPlayerEvents
import com.ebicep.warlordsplusplus.features.hud.AbstractHudElement
import com.ebicep.warlordsplusplus.util.ComponentBuilder
import net.minecraft.ChatFormatting
import net.minecraft.client.Minecraft
import net.minecraft.network.chat.MutableComponent

object RegenTimerHudElement : AbstractHudElement(0, 0) {

    private var regenTimer = 0

    init {
        EventBus.register<WarlordsPlayerEvents.DamageTakenEvent> {
            regenTimer = 10
        }
        EventBus.register<WarlordsGameEvents.SecondEvent> {
            if (regenTimer > 0) {
                regenTimer--
            }
        }
    }

    override fun shouldRender(): Boolean {
        return true
    }

    override fun getText(): MutableComponent? {
        val player = Minecraft.getInstance().player
        if (player?.health == player?.maxHealth) {
            return null
        }
        var text = "Regen: $regenTimer"
        val color: ChatFormatting
        when {
            regenTimer == 0 -> {
                text = "Regenning"
                color = ChatFormatting.GREEN
            }

            regenTimer < 3 -> {
                color = ChatFormatting.GREEN
            }

            regenTimer < 6 -> {
                color = ChatFormatting.YELLOW
            }

            else -> {
                color = ChatFormatting.RED
            }
        }
        return ComponentBuilder(text, color).create()
    }


}
