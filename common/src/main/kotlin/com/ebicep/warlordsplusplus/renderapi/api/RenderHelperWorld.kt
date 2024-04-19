package com.ebicep.warlordsplusplus.renderapi.api

import com.ebicep.warlordsplusplus.renderapi.RenderApi
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.math.Axis
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.util.Mth
import net.minecraft.world.phys.Vec3


abstract class RenderHelperWorld(override var guiGraphics: GuiGraphics?) : RenderApi() {

    override fun setupRender() {
        createPose {
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
        val rotY = Mth.atan2(x - eye.x, 0.0)
        poseStack.mulPose(Axis.YP.rotation(rotY.toFloat()))
    }

    protected fun autoRotateZ(z: Double, partialTick: Float) {
        val eye: Vec3 = Minecraft.getInstance().player!!.getEyePosition(partialTick)
        val rotY = Mth.atan2(0.0 - eye.z, z - eye.z)
        poseStack.mulPose(Axis.YP.rotation(rotY.toFloat()))
    }

}