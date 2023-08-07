package com.ebicep.warlordsplusplus.renderapi.api

import com.ebicep.warlordsplusplus.renderapi.RenderApi
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Axis
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.util.Mth
import net.minecraft.world.entity.player.Player
import net.minecraft.world.phys.Vec3
import org.joml.Quaternionf

abstract class RenderApiPlayer(
    poseStack: PoseStack,
    bufferSource: MultiBufferSource.BufferSource,
    var entity: Player,
    private val autoRotate: Boolean = true
) : RenderApi(poseStack, bufferSource) {

    override fun setupRender() {
        poseStack {
            translate(0.0, entity.nameTagOffsetY.toDouble(), 0.0)
            if (autoRotate) {
                val camera = Minecraft.getInstance().gameRenderer.mainCamera
                poseStack.mulPose(
                    Quaternionf()
                        .rotationX(-0.017453292F * camera.xRot)
                        .rotationY(-0.017453292F * camera.yRot)
                )
            }
            scaleForWorldRendering()
            RenderSystem.enableDepthTest() // so that text doesnt look weird (semi transparent)
            RenderSystem.enableBlend()

            render0()

            RenderSystem.disableDepthTest()
            RenderSystem.disableBlend()
        }
    }

    protected fun autoRotate(x: Double, z: Double, partialTick: Float) {
        val eye: Vec3 = Minecraft.getInstance().player!!.getEyePosition(partialTick)
        val rotY = Mth.atan2(x - eye.x, z - eye.z)
        poseStack.mulPose(Axis.YP.rotation(rotY.toFloat()))
    }

    protected fun autoRotateX(x: Double, partialTick: Float) {
        val eye: Vec3 = Minecraft.getInstance().player!!.getEyePosition(partialTick)
        val rotY = Mth.atan2(x - eye.x, 1.0)
        poseStack.mulPose(Axis.YP.rotation(rotY.toFloat()))
    }


}