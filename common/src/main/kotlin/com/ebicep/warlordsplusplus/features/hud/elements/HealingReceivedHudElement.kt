package com.ebicep.warlordsplusplus.features.hud.elements

import com.ebicep.warlordsplusplus.game.WarlordsPlayer
import com.ebicep.warlordsplusplus.util.ComponentBuilder
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.MutableComponent

object HealingReceivedHudElement : AbstractHighlightedHudElement(0, 0) {

    override fun shouldRender(): Boolean {
        return true
    }

    override fun getComponent(): MutableComponent {
        return ComponentBuilder("Healing Received: ${WarlordsPlayer.healingReceivedCounter}", ChatFormatting.DARK_GREEN).create()
    }

}
