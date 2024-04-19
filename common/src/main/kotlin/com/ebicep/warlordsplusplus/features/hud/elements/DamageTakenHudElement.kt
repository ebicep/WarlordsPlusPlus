package com.ebicep.warlordsplusplus.features.hud.elements

import com.ebicep.warlordsplusplus.game.WarlordsPlayer
import com.ebicep.warlordsplusplus.util.ComponentBuilder
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.MutableComponent

object DamageTakenHudElement : AbstractHighlightedHudElement(0, 0) {

    override fun shouldRender(): Boolean {
        return true
    }

    override fun getComponent(): MutableComponent {
        return ComponentBuilder("Damage Taken: ${WarlordsPlayer.damageTakenCounter}", ChatFormatting.DARK_RED).create()
    }

}