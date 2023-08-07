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

        val width: Int
            get() = mc.window.guiScaledWidth
        val height: Int
            get() = mc.window.guiScaledHeight
        val xCenter: Int
            get() = width / 2
        val yCenter: Int
            get() = height / 2

    }

}