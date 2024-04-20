package com.ebicep.warlordsplusplus.render

import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.renderer.MultiBufferSource

abstract class RenderHelperHud(var guiGraphics: GuiGraphics? = null) : RenderHelper() {

    override var bufferSource: MultiBufferSource.BufferSource?
        get() = guiGraphics!!.bufferSource()
        set(value) {}

    override var poseStack: PoseStack?
        get() = guiGraphics!!.pose()
        set(value) {}


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