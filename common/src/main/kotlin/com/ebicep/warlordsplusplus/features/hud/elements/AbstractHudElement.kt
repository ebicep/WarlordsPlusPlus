package com.ebicep.warlordsplusplus.features.hud.elements

import com.ebicep.warlordsplusplus.config.HudElementValues
import com.ebicep.warlordsplusplus.renderapi.api.RenderHelperGui
import net.minecraft.client.Minecraft
import net.minecraft.network.chat.MutableComponent

sealed class AbstractHudElement : RenderHelperGui() {

    var enabled: Boolean
        get() = getConfigValues().enabled
        set(value) {
            getConfigValues().enabled = value
        }
    var x: Int
        get() = getConfigValues().x
        set(value) {
            getConfigValues().x = value
        }
    var y: Int
        get() = getConfigValues().y
        set(value) {
            getConfigValues().y = value
        }

    abstract fun getConfigValues(): HudElementValues

    abstract fun getComponent(): MutableComponent?

    fun width(): Int {
        val text = getComponent() ?: return 0
        return Minecraft.getInstance().font.width(text)
    }

    fun height(): Int {
        return 10
    }

}
