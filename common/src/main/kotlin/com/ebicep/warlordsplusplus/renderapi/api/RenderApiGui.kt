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

}