package com.ebicep.warlordsplusplus.render

import com.ebicep.warlordsplusplus.events.LevelRenderEvent
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Axis
import net.minecraft.client.Camera
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.util.Mth
import net.minecraft.world.phys.Vec3


abstract class RenderHelperWorld(var levelRenderEvent: LevelRenderEvent? = null) : RenderHelper() {

    override var poseStack: PoseStack?
        get() = levelRenderEvent!!.poseStack
        set(value) {}
    override var bufferSource: MultiBufferSource.BufferSource?
        get() = mc.renderBuffers().bufferSource()
        set(value) {}

    override fun setupRender() {
        createPose {
//            RenderSystem.enableDepthTest() // so that text doesnt look weird (semi transparent)
//            RenderSystem.enableBlend()
//            RenderSystem.disableCull()
            render0()
//            RenderSystem.disableDepthTest()
//            RenderSystem.disableBlend()
//            RenderSystem.enableCull()
        }
    }

    fun cameraYrot(camera: Camera): Float {
        return camera.yRot - 180.0f
    }

    fun cameraXRot(camera: Camera): Float {
        return -camera.xRot
    }

    protected fun translateToPos(x: Double, y: Double, z: Double) {
        val camera = levelRenderEvent!!.camera
        translate(
            x - camera.position.x,
            y - camera.position.y,
            z - camera.position.z
        )
    }

    protected fun scaleForWorldRendering(scale: Float) {
        scale(scale * -0.025f, scale * -0.025f, -1f)
    }

    protected fun autoRotate() {
        poseStack!!.mulPose(levelRenderEvent!!.camera.rotation())
    }

    protected fun autoRotateX(x: Double) {
        val eye: Vec3 = Minecraft.getInstance().player!!.getEyePosition(levelRenderEvent!!.f)
        val rotY = Mth.atan2(x - eye.x, 0.0)
        poseStack!!.mulPose(Axis.YP.rotation(rotY.toFloat()))
    }

    protected fun autoRotateZ(z: Double) {
        val eye: Vec3 = Minecraft.getInstance().player!!.getEyePosition(levelRenderEvent!!.f)
        val rotY = Mth.atan2(0.0 - eye.z, z - eye.z)
        poseStack!!.mulPose(Axis.YP.rotation(rotY.toFloat()))
    }

}