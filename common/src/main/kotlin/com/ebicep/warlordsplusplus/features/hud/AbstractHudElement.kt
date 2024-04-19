package com.ebicep.warlordsplusplus.features.hud

import com.ebicep.warlordsplusplus.renderapi.api.RenderHelperGui
import kotlinx.serialization.Serializable
import net.minecraft.client.Minecraft
import net.minecraft.network.chat.MutableComponent

@Serializable
abstract class AbstractHudElement(var x: Int = 0, var y: Int = 0) : RenderHelperGui() {

    abstract fun getComponent(): MutableComponent?

    fun width(): Int {
        val text = getComponent() ?: return 0
        return Minecraft.getInstance().font.width(text)
    }

    fun height(): Int {
        return 10
    }

}