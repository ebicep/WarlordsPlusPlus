package com.ebicep.warlordsplusplus.features.player.cooldown

import com.ebicep.chatplus.events.EventBus
import com.ebicep.warlordsplusplus.config.Config
import com.ebicep.warlordsplusplus.events.PlayerRenderEvent
import com.ebicep.warlordsplusplus.features.Feature
import com.ebicep.warlordsplusplus.game.GameStateManager
import com.ebicep.warlordsplusplus.game.OtherWarlordsPlayers
import com.ebicep.warlordsplusplus.render.RenderHelperPlayer
import com.ebicep.warlordsplusplus.util.Colors
import com.ebicep.warlordsplusplus.util.ImageRegistry
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import java.time.Instant

object CooldownRenderer : Feature {


    init {
        EventBus.register<PlayerRenderEvent> {
            val poseStack = it.poseStack
            val bufferSource = it.bufferSource
            Renderer(poseStack, bufferSource, it.entity).render()
            InteractionResult.PASS
        }
    }


    class Renderer(
        override var poseStack: PoseStack?,
        override var bufferSource: MultiBufferSource.BufferSource?,
        entity: Player
    ) : RenderHelperPlayer(poseStack, bufferSource, entity) {

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
            createPose {
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
            createPose {
                translateY(37)
                val width = 75f
                createPose {
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
            createPose {
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
            createPose {
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

