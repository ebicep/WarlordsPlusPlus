package com.ebicep.warlordsplusplus.renderapi.api

import com.ebicep.warlordsplusplus.renderapi.RenderApi
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.renderer.MultiBufferSource

abstract class RenderApiGui(poseStack: PoseStack, bufferSource: MultiBufferSource.BufferSource) : RenderApi(poseStack, bufferSource) {

    override fun setupRender() {
        poseStack {
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