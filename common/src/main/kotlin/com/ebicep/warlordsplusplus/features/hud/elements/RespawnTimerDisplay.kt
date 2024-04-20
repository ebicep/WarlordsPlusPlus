package com.ebicep.warlordsplusplus.features.hud.elements

import com.ebicep.warlordsplusplus.config.Config
import com.ebicep.warlordsplusplus.config.HudElementValues
import com.ebicep.warlordsplusplus.detectors.RespawnTimerDetector
import com.ebicep.warlordsplusplus.game.GameModes
import com.ebicep.warlordsplusplus.game.GameStateManager
import com.ebicep.warlordsplusplus.util.ComponentBuilder
import net.minecraft.ChatFormatting
import net.minecraft.client.Minecraft
import net.minecraft.network.chat.MutableComponent

data object RespawnTimerDisplay : AbstractHudElement() {

    override fun shouldRender(): Boolean {
        return GameStateManager.inGame
    }

    override fun getConfigValues(): HudElementValues {
        return Config.values.hudElements.respawnTimer
    }

    override fun getComponent(): MutableComponent? {
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

    override fun render0() {
        getComponent()?.let {
            guiGraphics?.drawString(Minecraft.getInstance().font, it, 0, 0, 0, false)
        }
    }

}