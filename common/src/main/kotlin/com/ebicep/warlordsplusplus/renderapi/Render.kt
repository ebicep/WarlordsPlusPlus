package com.ebicep.warlordsplusplus.renderapi

import com.ebicep.warlordsplusplus.WarlordsPlusPlus
import com.ebicep.warlordsplusplus.features.hud.HudElementScreen
import net.minecraft.client.Minecraft

interface Render {

    fun render(): Boolean {
        if (!WarlordsPlusPlus.isEnabled()) {
            return false
        }
        if (!shouldRender() && !(Minecraft.getInstance().screen is HudElementScreen)) {
            return false
        }
        setupRender()
        return true
    }

    fun setupRender()

    fun shouldRender(): Boolean

    fun render0()

}