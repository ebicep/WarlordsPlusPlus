package com.ebicep.warlordsplusplus.features

import com.mojang.blaze3d.platform.GlStateManager
import com.mojang.blaze3d.systems.RenderSystem
import dev.architectury.event.events.client.ClientGuiEvent
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.Mth

object RenderVignette : Feature {

    private var VIGNETTE_LOCATION = ResourceLocation("textures/misc/vignette.png")

    init {
        ClientGuiEvent.RENDER_HUD.register { guiGraphics, _ ->
            Minecraft.getInstance().gui.overlayMessageString?.let {
                if (it.string.contains("VENE") || it.string.contains("INTR")) {
                    renderVignette(guiGraphics, 0, 0, 255)
                }
            }

        }
    }

    private fun renderVignette(
        guiGraphics: GuiGraphics,
        red: Int,
        green: Int,
        blue: Int,
    ) {
        RenderSystem.enableBlend()
        RenderSystem.disableDepthTest()
        RenderSystem.depthMask(false)
        RenderSystem.blendFuncSeparate(
            GlStateManager.SourceFactor.ONE,
            GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR,
            GlStateManager.SourceFactor.ZERO,
            GlStateManager.DestFactor.ZERO
        )

        guiGraphics.setColor(
            Mth.clamp(red / 255f, 0f, 1f),
            Mth.clamp(green / 255f, 0f, 1f),
            Mth.clamp(blue / 255f, 0f, 1f),
            1f
        )

        guiGraphics.blit(
            VIGNETTE_LOCATION,
            0,
            0,
            -90,
            0.0f,
            0.0f,
            guiGraphics.guiWidth(),
            guiGraphics.guiHeight(),
            guiGraphics.guiWidth(),
            guiGraphics.guiHeight(),
        )

        RenderSystem.depthMask(true)
        RenderSystem.enableDepthTest()
        guiGraphics.setColor(1.0f, 1.0f, 1.0f, 1.0f)
        RenderSystem.defaultBlendFunc()
    }

}