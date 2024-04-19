package com.ebicep.warlordsplusplus.features.hud.elements

import com.ebicep.warlordsplusplus.features.hud.AbstractHudElement
import com.ebicep.warlordsplusplus.game.WarlordsPlayer
import com.ebicep.warlordsplusplus.renderapi.api.RenderHelperGui
import com.ebicep.warlordsplusplus.util.Colors
import net.minecraft.client.Minecraft

class HealingReceivedHudElement : AbstractHudElement(Renderer(), 0, 0) {

    class Renderer : RenderHelperGui() {
        override fun shouldRender(): Boolean {
            return true
        }

        override fun render0() {
            val damage = "Healing Received: ${WarlordsPlayer.healingReceivedCounter}"
            createPose {
                translateX(-2)
                guiGraphics?.fill(0, 0, Minecraft.getInstance().font.width(damage) + 3, 11, Colors.DEF.convertToArgb(100))
            }
            createPose {
                translateY(-2)
                guiGraphics?.drawString(Minecraft.getInstance().font, damage, 0, 0, Colors.DARK_GREEN.convertToArgb(), false)
            }
        }
    }

    override fun width(): Int {
        return Minecraft.getInstance().font.width("Healing Received: ")
    }

    override fun height(): Int {
        return 10
    }

}