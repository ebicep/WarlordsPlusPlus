package com.ebicep.warlordsplusplus.features.render

import com.ebicep.warlordsplusplus.detectors.RespawnTimerDetector
import com.ebicep.warlordsplusplus.features.Feature
import com.ebicep.warlordsplusplus.game.GameModes
import com.ebicep.warlordsplusplus.game.GameStateManager
import com.ebicep.warlordsplusplus.renderapi.api.RenderHelperGui
import com.ebicep.warlordsplusplus.util.ComponentBuilder
import net.minecraft.ChatFormatting
import net.minecraft.client.Minecraft
import net.minecraft.network.chat.MutableComponent

object RespawnTimerDisplay : RenderHelperGui(), Feature {

    private var x = Minecraft.getInstance().window.guiScaledHeight / 2
    private var y = Minecraft.getInstance().window.guiScaledWidth / 2 + 10

    override fun shouldRender(): Boolean {
        return true
    }

    override fun render0() {
        createPose {
            translate(x.toDouble(), y.toDouble())
            getTimerText()?.let { guiGraphics?.drawString(Minecraft.getInstance().font, it, 0, 0, 0, false) }
        }
    }

    fun getTimerText(): MutableComponent? {
        val respawnTimer = RespawnTimerDetector.respawnTimer

        when (GameStateManager.currentGameMode) {
            GameModes.CTF -> {
                return ComponentBuilder(
                    "$respawnTimer",
                    when {
                        respawnTimer < 5 -> ChatFormatting.RED
                        respawnTimer < 8 -> ChatFormatting.YELLOW
                        else -> ChatFormatting.GREEN
                    }
                ).create()
            }

            GameModes.DOM -> {
                return ComponentBuilder(
                    if (respawnTimer < 8) {
                        "${respawnTimer + 8}"
                    } else {
                        "$respawnTimer"
                    },
                    ChatFormatting.WHITE
                ).create()
            }

            else -> return null
        }
    }

}