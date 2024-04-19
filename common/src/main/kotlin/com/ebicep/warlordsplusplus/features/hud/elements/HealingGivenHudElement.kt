package com.ebicep.warlordsplusplus.features.hud.elements

import com.ebicep.warlordsplusplus.features.hud.AbstractHudElement
import com.ebicep.warlordsplusplus.game.WarlordsPlayer
import com.ebicep.warlordsplusplus.util.ComponentBuilder
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.MutableComponent

object HealingGivenHudElement : AbstractHudElement(0, 0) {

    override fun shouldRender(): Boolean {
        return true
    }

    override fun getText(): MutableComponent {
        return ComponentBuilder("Healing: ${WarlordsPlayer.healingGivenCounter}", ChatFormatting.GREEN).create()
    }

}