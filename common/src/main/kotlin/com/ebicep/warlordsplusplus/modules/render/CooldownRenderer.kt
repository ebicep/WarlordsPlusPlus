package com.ebicep.warlordsplusplus.modules.render

import com.ebicep.warlordsplusplus.config.Config
import com.ebicep.warlordsplusplus.event.PlayerRenderEvent
import com.ebicep.warlordsplusplus.game.GameStateManager
import com.ebicep.warlordsplusplus.game.OtherWarlordsPlayers
import com.ebicep.warlordsplusplus.modules.Module
import com.ebicep.warlordsplusplus.renderapi.api.RenderApiPlayer
import com.ebicep.warlordsplusplus.util.Colors
import com.ebicep.warlordsplusplus.util.ImageRegistry
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import java.time.Instant

object CooldownRenderer : Module {


    init {
        var renderer: Renderer? = null
        PlayerRenderEvent.PLAYER_RENDER_POST.register { poseStack, bufferSource, entity ->
            if (renderer == null) {
                renderer = Renderer(poseStack, bufferSource, entity)
            } else {
                renderer!!.poseStack = poseStack
                renderer!!.bufferSource = bufferSource
                renderer!!.entity = entity
            }
            renderer!!.render()
            InteractionResult.PASS
        }
    }


    class Renderer(
        poseStack: PoseStack,
        bufferSource: MultiBufferSource.BufferSource,
        entity: Player
    ) : RenderApiPlayer(poseStack, bufferSource, entity) {

        override fun shouldRender(): Boolean {
            return Config.values.renderPlayerInfo.value &&
                    GameStateManager.inWarlords2 &&
                    GameStateManager.inGame &&
                    entity != Minecraft.getInstance().player &&
                    (GameStateManager.inPvE || !OtherWarlordsPlayers.playersMap.containsKey(Minecraft.getInstance().player?.scoreboardName))
        }

        override fun render0() {
            val otherWarlordsPlayer = OtherWarlordsPlayers.playersMap[entity.scoreboardName]
            if (otherWarlordsPlayer == null) {
                OtherWarlordsPlayers.getOtherWarlordsPlayers()
                return
            }
            if (otherWarlordsPlayer.lastUpdated.isBefore(Instant.now().minusSeconds(5))) {
                return
            }
            poseStack {
                translateX(-36.5)
                translateY(75.0)
                scale(.4)

                drawAbility(otherWarlordsPlayer.redCooldown, ImageRegistry.RED_ABILITY)
                translateX(60)
                drawAbility(otherWarlordsPlayer.purpleCooldown, ImageRegistry.PURPLE_ABILITY)
                translateX(60)
                drawAbility(otherWarlordsPlayer.blueCooldown, ImageRegistry.BLUE_ABILITY)
                translateX(60)
                drawAbility(otherWarlordsPlayer.orangeCooldown, ImageRegistry.ORANGE_ABILITY)
            }
            poseStack {
                translateY(37)
                val width = 75f
                poseStack {
                    translateY(.5)
                    renderRectXCentered(width + 2, 6f, Colors.BLACK)
                }
                translateZ(.01)
                renderRectXCentered(width, 5f, Colors.GRAY)
                translateX(-width / 2 - .5)
                translateZ(.01)
                renderRect(
                    ((otherWarlordsPlayer.currentEnergy.toDouble() / otherWarlordsPlayer.maxEnergy) * width).toFloat(),
                    5f,
                    Colors.GOLD
                )
            }
            poseStack {
                translateY(39.5)
                translateZ(.05)
                scale(1.5)
                otherWarlordsPlayer.currentEnergy.toString().drawCentered(color = Colors.GREEN)
            }
        }

        private fun drawAbility(cooldown: Int, image: ImageRegistry) {
            if (cooldown == 0) {
                renderImage(75, 75, image)
            } else {
                renderCooldown(cooldown)
            }
        }

        private fun renderCooldown(cooldown: Int) {
            poseStack {
                scale(.9)
                translateX(5)
                translateY(-7)
                renderImage(70, 70, ImageRegistry.COOLDOWN)
                translate(-3.0, 45.0, .1)
                scale(4.2)
                val str = if (cooldown < 10) " $cooldown" else "$cooldown"
                str.draw()
            }
        }

    }
}

