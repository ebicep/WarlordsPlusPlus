package com.ebicep.warlordsplusplus.events

import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.Camera
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.client.renderer.LightTexture
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.world.entity.PlayerRideableJumping
import net.minecraft.world.entity.player.Player
import net.minecraft.world.scores.Objective
import net.minecraft.world.scores.Scoreboard
import org.joml.Matrix4f

data class TabListRenderEvent(
    val guiGraphics: GuiGraphics,
    val i: Int,
    val scoreboard: Scoreboard,
    val objective: Objective?,
    var cancel: Boolean = false
)

data class PlayerRenderEvent(
    val poseStack: PoseStack,
    val bufferSource: MultiBufferSource.BufferSource,
    val entity: Player
)

data class LevelRenderEvent(
    val poseStack: PoseStack,
    val f: Float,
    val l: Long,
    val bl: Boolean,
    val camera: Camera,
    val gameRenderer: GameRenderer,
    val lightTexture: LightTexture,
    val matrix4f: Matrix4f
)

data class RenderJumpMeterEvent(
    val playerRideableJumping: PlayerRideableJumping,
    val guiGraphics: GuiGraphics,
    val i: Int,
    var cancel: Boolean = false
)
