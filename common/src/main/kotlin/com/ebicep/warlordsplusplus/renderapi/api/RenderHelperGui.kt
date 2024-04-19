package com.ebicep.warlordsplusplus.renderapi.api

import com.ebicep.warlordsplusplus.renderapi.RenderApi
import net.minecraft.client.gui.GuiGraphics

abstract class RenderHelperGui(guiGraphics: GuiGraphics? = null) : RenderApi(guiGraphics) {

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