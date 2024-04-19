package com.ebicep.warlordsplusplus.renderapi.api

import com.ebicep.warlordsplusplus.renderapi.RenderApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import net.minecraft.client.gui.GuiGraphics

@Serializable
abstract class RenderHelperGui(@Transient override var guiGraphics: GuiGraphics? = null) : RenderApi() {

    override fun setupRender() {
        createPose {
            render0()
        }
    }

    companion object {

        val scaledWidth: Int
            get() = mc.window.guiScaledWidth
        val scaledHeight: Int
            get() = mc.window.guiScaledHeight
        val xCenter: Int
            get() = scaledWidth / 2
        val yCenter: Int
            get() = scaledHeight / 2

    }

}