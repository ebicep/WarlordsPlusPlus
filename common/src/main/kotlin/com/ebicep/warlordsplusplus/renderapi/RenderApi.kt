package com.ebicep.warlordsplusplus.renderapi

import com.ebicep.warlordsplusplus.WarlordsPlusPlus
import com.ebicep.warlordsplusplus.util.Colors
import com.ebicep.warlordsplusplus.util.ImageRegistry
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.*
import com.mojang.math.Axis
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Font
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.player.LocalPlayer
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.client.renderer.LightTexture
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.network.chat.Component
import net.minecraft.world.phys.Vec3
import org.apache.logging.log4j.Level
import org.joml.Quaternionf

@Serializable
abstract class RenderApi : Render {

    @Transient
    abstract var guiGraphics: GuiGraphics?

    @Transient
    val poseStack: PoseStack
        get() = guiGraphics!!.pose()

    @Transient
    val bufferSource: MultiBufferSource.BufferSource
        get() = guiGraphics!!.bufferSource()

    companion object {
        val tesselator: Tesselator
            get() = Tesselator.getInstance()

        val bufferBuilder: BufferBuilder = tesselator.builder

        val mc: Minecraft
            get() = Minecraft.getInstance()

        val player: LocalPlayer?
            get() = mc.player

        val font: Font
            get() = mc.font
    }

    protected fun translateToPos(x: Double, y: Double, z: Double) {
        // Translate to world origin
        val offset: Vec3 = Minecraft.getInstance().gameRenderer.mainCamera.position
        poseStack.translate(x - offset.x, y - offset.y, z - offset.z)
    }

    fun scaleForWorldRendering() =
        poseStack.scale(-0.025f, -0.025f, -0.025f)

    fun renderImage(
        width: Float,
        height: Float,
        image: ImageRegistry,
        xCentered: Boolean = true
    ) {
        val resourceLocation = image.getResourceLocation()
        if (resourceLocation == null) {
            WarlordsPlusPlus.LOGGER.log(Level.ERROR, "Resource location for image ${image.name} is null")
            return
        }
        RenderSystem.enableDepthTest() // so that text doesnt look weird (semi transparent)
        RenderSystem.setShader { GameRenderer.getPositionTexShader() }
        RenderSystem.setShaderTexture(0, resourceLocation)
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX)
        val pPose = poseStack.last().pose()
        if (xCentered) {
            val w2 = width / 2
            bufferBuilder
                .vertex(pPose, -w2, 0f, 0f)
                .uv(0f, 0f)
                .endVertex()
            bufferBuilder
                .vertex(pPose, -w2, height, 0f)
                .uv(0f, 1f)
                .endVertex()
            bufferBuilder
                .vertex(pPose, w2, height, 0f)
                .uv(1f, 1f)
                .endVertex()
            bufferBuilder
                .vertex(pPose, w2, 0f, 0f)
                .uv(1f, 0f)
                .endVertex()
        } else {
            bufferBuilder
                .vertex(pPose, 0f, 0f, 0f)
                .uv(0f, 0f)
                .endVertex()
            bufferBuilder
                .vertex(pPose, 0f, height, 0f)
                .uv(0f, 1f)
                .endVertex()
            bufferBuilder
                .vertex(pPose, width, height, 0f)
                .uv(1f, 1f)
                .endVertex()
            bufferBuilder
                .vertex(pPose, width, 0f, 0f)
                .uv(1f, 0f)
                .endVertex()
        }
        tesselator.end()
        RenderSystem.disableDepthTest()
    }

    fun renderImage(
        width: Int,
        height: Int,
        image: ImageRegistry,
        XCentered: Boolean = true
    ) {
        renderImage(width.toFloat(), height.toFloat(), image, XCentered)
    }

    /**
     * Draws a rectangle
     */
    fun renderRect(width: Float, height: Float, color: Colors, alpha: Int = 255, z: Float = 0f) {
//        RenderSystem.enableDepthTest()
//        RenderSystem.setShader { GameRenderer.getPositionColorShader() }
//        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR)
        val pPose = poseStack.last().pose()
        val vertexConsumer = bufferSource.getBuffer(RenderType.gui())
        vertexConsumer
            .vertex(pPose, 0f, 0f, z)
            .color(color, alpha)
            .endVertex()
        vertexConsumer
            .vertex(pPose, 0f, height, z)
            .color(color, alpha)
            .endVertex()
        vertexConsumer
            .vertex(pPose, width, height, z)
            .color(color, alpha)
            .endVertex()
        vertexConsumer
            .vertex(pPose, width, 0f, z)
            .color(color, alpha)
            .endVertex()
//        tesselator.end()
//        RenderSystem.disableDepthTest()
        RenderSystem.disableDepthTest()
        bufferSource.endBatch()
        RenderSystem.enableDepthTest()
    }

    fun renderRect(width: Int, height: Int, color: Colors, alpha: Int = 255, z: Float = 0f) {
        renderRect(width.toFloat(), height.toFloat(), color, alpha, z)
    }

    fun renderRect(width: Double, height: Double, color: Colors, alpha: Int = 255, z: Float = 0f) {
        renderRect(width.toFloat(), height.toFloat(), color, alpha, z)
    }

    fun renderRectXCentered(width: Float, height: Float, color: Colors, alpha: Int = 255, z: Float = 0f) {
//        RenderSystem.enableDepthTest()
//        RenderSystem.setShader { GameRenderer.getPositionColorShader() }
        val pPose = poseStack.last().pose()
        val w2 = width / 2
        val vertexConsumer = bufferSource.getBuffer(RenderType.gui())
        vertexConsumer
            .vertex(pPose, -w2, 0f, z)
            .color(color, alpha)
            .endVertex()
        vertexConsumer
            .vertex(pPose, -w2, height, z)
            .color(color, alpha)
            .endVertex()
        vertexConsumer
            .vertex(pPose, w2, height, z)
            .color(color, alpha)
            .endVertex()
        vertexConsumer
            .vertex(pPose, w2, 0f, z)
            .color(color, alpha)
            .endVertex()
        RenderSystem.disableDepthTest()
        bufferSource.endBatch()
        RenderSystem.enableDepthTest()
    }

    fun VertexConsumer.color(color: Colors, alpha: Int = 255): VertexConsumer {
        return this.color(color.red, color.green, color.blue, alpha)
    }

    fun String.height(): Double = 8.0

    fun String.width(): Int =
        font.width(this)

    fun String.draw(seeThruBlocks: Boolean = false, shadow: Boolean = false, pX: Float = 0f, color: Colors = Colors.WHITE) {
        if (seeThruBlocks) {
            RenderSystem.disableDepthTest()
            RenderSystem.depthMask(true)
        }
        font.drawInBatch(
            this,
            pX,
            0f,
            color.FULL,
            shadow,
            poseStack.last().pose(),
            bufferSource,
            Font.DisplayMode.NORMAL,
            0,
            LightTexture.FULL_BRIGHT
        )
        if (seeThruBlocks) {
            RenderSystem.enableDepthTest()
            RenderSystem.depthMask(false)
        }
    }

    fun Component.draw(seeThruBlocks: Boolean = false, shadow: Boolean = false, pX: Float = 0f, color: Colors = Colors.WHITE) {
        if (seeThruBlocks) {
            RenderSystem.disableDepthTest()
            RenderSystem.depthMask(true)
        }
        font.drawInBatch(
            this,
            pX,
            0f,
            color.FULL,
            shadow,
            poseStack.last().pose(),
            bufferSource,
            Font.DisplayMode.NORMAL,
            0,
            LightTexture.FULL_BRIGHT
        )
        if (seeThruBlocks) {
            RenderSystem.enableDepthTest()
            RenderSystem.depthMask(false)
        }
    }

    fun String.drawLeft(seeThruBlocks: Boolean = false, shadow: Boolean = false, color: Colors = Colors.WHITE) {
        draw(seeThruBlocks, shadow, -this.width().toFloat(), color)
    }

    fun String.drawCentered(seeThruBlocks: Boolean = false, shadow: Boolean = false, color: Colors = Colors.WHITE) {
        draw(seeThruBlocks, shadow, -this.width() / 2.toFloat(), color)
    }

    fun String.drawWithBackground(backgroundColor: Colors, alpha: Int = 255, padding: Int = 1) {
        renderRect(this.width() + padding * 2f, 7 + padding * 2f, backgroundColor, alpha = alpha)
        translate(padding.toDouble(), padding.toDouble(), 0.0)
        draw()
        translate(-padding.toDouble(), -padding.toDouble(), 0.0)
    }

//    fun String.drawWithBackgroundAndWidth(backgroundColor: Colors, width: Int, alpha: Int = 255, padding: Int = 1): Int =
//        drawWithBackgroundAndWidth(backgroundColor, width, alpha, padding, padding, padding, padding)
//
//    fun String.drawWithBackgroundAndWidth(
//        backgroundColor: Colors,
//        width: Int,
//        alpha: Int = 255,
//        paddingLeft: Int = 1,
//        paddingRight: Int = 1,
//        paddingTop: Int = 1,
//        paddingBottom: Int = 1
//    ): Int {
//
//        val renderStrings = this.listFormattedStringToWidth(width - paddingLeft - paddingRight)
//
//        renderRect(width, renderStrings.size * 9 + paddingTop + paddingBottom, backgroundColor, alpha)
//
//        translateY(-paddingTop)
//        translateX(paddingLeft) {
//            renderStrings.forEach { s ->
//                s.draw()
//                translateY(-9)
//            }
//        }
//        return renderStrings.size * 9 + paddingTop + paddingBottom
//    }

    fun String.drawCenteredWithBackground(backgroundColor: Colors) {
        val width = this.width() + 2.0
        translateX(-width / 2)
        renderRect(width.toFloat(), 9f, backgroundColor)
        translate(1.0 + width / 2, 1.0, 0.0)
        draw()
        translate(-1.0, -1.0, 0.0)
    }

    inline fun createPose(fn: () -> Unit) {
        poseStack.pushPose()
        fn()
        poseStack.popPose()
    }

    fun rotate(angle: Float, x: Float, y: Float, z: Float) =
        poseStack.mulPose(Quaternionf().rotateAxis(Math.toRadians(angle.toDouble()).toFloat(), x, y, z)) //TODO CHECK

    fun rotateX(angle: Float) =
        poseStack.mulPose(Axis.XP.rotationDegrees(angle))

    fun rotateY(angle: Float) =
        poseStack.mulPose(Axis.YP.rotationDegrees(angle))

    fun rotateZ(angle: Float) =
        poseStack.mulPose(Axis.ZP.rotationDegrees(angle))

    //Translate

    fun translate(x: Int = 0, y: Int = 0, z: Int = 0) =
        translate(x.toDouble(), y.toDouble(), z.toDouble())

    fun translate(x: Double, y: Double = 0.0, z: Double = 0.0) =
        poseStack.translate(x, y, z)

    inline fun translate(x: Int, y: Int = 0, z: Int = 0, fn: () -> Unit) {
        translate(x, y, z)
        fn()
        translate(-x, -y, -z)
    }

    inline fun translate(x: Double = 0.0, y: Double = 0.0, z: Double = 0.0, fn: () -> Unit) {
        translate(x, y, z)
        fn()
        translate(-x, -y, -z)
    }

    inline fun translate(x: Int, y: Int, fn: () -> Unit) {
        translate(x, y, 0)
        fn()
        translate(-x, -y, 0)
    }

    inline fun translate(x: Double, y: Double, fn: () -> Unit) {
        translate(x, y, 0.0)
        fn()
        translate(-x, -y, 0.0)
    }

    fun translateX(x: Int) = translate(x, 0, 0)

    inline fun translateX(x: Int, fn: () -> Unit) {
        translate(x, 0, 0)
        fn()
        translate(-x, 0, 0)
    }

    fun translateX(x: Double) =
        translate(x, 0.0, 0.0)

    inline fun translateX(x: Double, fn: () -> Unit) {
        translate(x, 0.0, 0.0)
        fn()
        translate(-x, 0.0, 0.0)
    }

    fun translateY(y: Int) =
        translate(0, -y, 0)

    inline fun translateY(y: Int, fn: () -> Unit) {
        translate(0, y, 0)
        fn()
        translate(0, -y, 0)
    }

    fun translateY(y: Double) =
        translate(0.0, -y, 0.0)

    inline fun translateY(y: Double, fn: () -> Unit) {
        translate(0.0, y, 0.0)
        fn()
        translate(0.0, -y, 0.0)
    }

    fun translateZ(z: Int) =
        translate(0, 0, z)

    inline fun translateZ(z: Int, fn: () -> Unit) {
        translate(0, 0, z)
        fn()
        translate(0, 0, -z)
    }

    fun translateZ(z: Double) =
        translate(0.0, 0.0, z)

    inline fun translateZ(z: Double, fn: () -> Unit) {
        translate(0.0, 0.0, z)
        fn()
        translate(0.0, 0.0, -z)
    }

    fun scale(amount: Float) =
        poseStack.scale(amount, amount, -amount)

    fun scale(amount: Double) =
        scale(amount.toFloat())

    inline fun scale(amount: Double, fn: () -> Unit) {
        scale(amount)
        fn()
        scale(1 / amount)
    }

}