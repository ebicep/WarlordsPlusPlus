package com.ebicep.warlordsplusplus.events

import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.world.entity.player.Player
import net.minecraft.world.scores.Objective
import net.minecraft.world.scores.Scoreboard

data class TabListRenderEvent(
    val guiGraphics: GuiGraphics,
    val i: Int,
    val scoreboard: Scoreboard,
    val objective: Objective?,
    var returnFunction: Boolean = false
)

data class PlayerRenderEvent(
    val poseStack: PoseStack,
    val bufferSource: MultiBufferSource.BufferSource,
    val entity: Player
)
