package com.ebicep.warlordsplusplus.features.hud.elements

import com.ebicep.warlordsplusplus.util.Colors
import net.minecraft.client.Minecraft

sealed class AbstractHighlightedHudElement : AbstractHudElement() {

    override fun render0() {
        val text = getComponent() ?: return
        createPose {
            translateX(-2)
            guiGraphics?.fill(0, 0, Minecraft.getInstance().font.width(text) + 3, 11, Colors.DEF.convertToArgb(100))
        }
        createPose {
            translateY(-2)
            guiGraphics?.drawString(Minecraft.getInstance().font, text, 0, 0, 0, false)
        }
    }

}