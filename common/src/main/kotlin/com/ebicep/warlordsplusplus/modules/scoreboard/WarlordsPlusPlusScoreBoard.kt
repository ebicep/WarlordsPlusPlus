package com.ebicep.warlordsplusplus.modules.scoreboard

import com.ebicep.warlordsplusplus.config.Config
import com.ebicep.warlordsplusplus.event.TabListRenderEvent
import com.ebicep.warlordsplusplus.game.GameStateManager
import com.ebicep.warlordsplusplus.game.OtherWarlordsPlayer
import com.ebicep.warlordsplusplus.game.OtherWarlordsPlayers
import com.ebicep.warlordsplusplus.game.WarlordsPlayer
import com.ebicep.warlordsplusplus.modules.Module
import com.ebicep.warlordsplusplus.renderapi.api.RenderApiGui
import com.ebicep.warlordsplusplus.util.Colors
import com.ebicep.warlordsplusplus.util.Specialization
import com.ebicep.warlordsplusplus.util.Team
import com.ebicep.warlordsplusplus.util.WarlordClass
import net.minecraft.ChatFormatting
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.multiplayer.ClientPacketListener
import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionResult
import net.minecraft.world.scores.Objective
import java.util.*

object WarlordsPlusPlusScoreBoard : Module {

    private val showNewScoreboard: Boolean
        get() = Config.values.scoreboardEnabled.value
    private val showTopHeader: Boolean
        get() = Config.values.scoreboardShowTopHeader.value
    private val showOutline: Boolean
        get() = Config.values.scoreboardShowOutline.value
    private val showDiedToYouStoleKill: Boolean
        get() = Config.values.scoreboardShowDiedToYouStoleKill.value
    private val showDoneAndReceived: Boolean
        get() = Config.values.scoreboardShowDoneAndReceived.value
    private val splitScoreBoard: Boolean
        get() = Config.values.scoreboardSplitScoreBoard.value

    private val spaceBetweenSplit = 6


    init {
        var renderer: Renderer? = null
        TabListRenderEvent.TAB_LIST_RENDER_PRE.register { guiGraphics, i, scoreboard, objective ->
            if (renderer == null) {
                renderer = Renderer(guiGraphics)
            } else {
                renderer!!.poseStack = guiGraphics.pose()
                renderer!!.bufferSource = guiGraphics.bufferSource()
            }
            if (renderer!!.render()) {
                InteractionResult.FAIL
            } else {
                InteractionResult.PASS
            }
        }
    }

    class Renderer(private val guiGraphics: GuiGraphics) : RenderApiGui(guiGraphics.pose(), guiGraphics.bufferSource()) {
        override fun shouldRender(): Boolean {
            val scoreObjective: Objective? = mc.level!!.scoreboard.getDisplayObjective(0)
            val handler: ClientPacketListener = mc.player!!.connection

            return GameStateManager.inGame && GameStateManager.currentGameMode.isPvP() && showNewScoreboard &&
                    (mc.options.keyPlayerList.isDown && (!mc.isLocalServer || handler.onlinePlayers.size > 1 || scoreObjective != null))
        }

        override fun render0() {
            val thePlayer = player ?: return

            var players = OtherWarlordsPlayers.getOtherWarlordsPlayers(thePlayer.connection.onlinePlayers)
            players = listOf(
                OtherWarlordsPlayer("Heatran", UUID.randomUUID()).apply {
                    kills = 1
                    deaths = 2
                    damageDone = 300
                    damageReceived = 40
                    healingDone = 0
                    healingReceived = 0
                    warlordClass = WarlordClass.MAGE
                    spec = Specialization.CRYOMANCER
                    team = Team.BLUE
                    level = 7
                    left = false
                },
                OtherWarlordsPlayer("John_Br", UUID.randomUUID()).apply {
                    kills = 10
                    deaths = 0
                    damageDone = 0
                    damageReceived = 0
                    healingDone = 100
                    healingReceived = 1000
                    warlordClass = WarlordClass.WARRIOR
                    spec = Specialization.REVENANT
                    team = Team.RED
                    level = 90
                    left = false
                },
                OtherWarlordsPlayer("_RealDeal_", UUID.randomUUID()).apply {
                    kills = 0
                    deaths = 204
                    damageDone = 0
                    damageReceived = 0
                    healingDone = 3234
                    healingReceived = 0
                    warlordClass = WarlordClass.PALADIN
                    spec = Specialization.AVENGER
                    team = Team.RED
                    level = 56
                    left = false
                },
                OtherWarlordsPlayer("JohnSmith", UUID.randomUUID()).apply {
                    kills = 100
                    deaths = 25
                    damageDone = 30
                    damageReceived = 406
                    healingDone = 0
                    healingReceived = 0
                    warlordClass = WarlordClass.MAGE
                    spec = Specialization.PYROMANCER
                    team = Team.BLUE
                    level = 70
                    left = false
                },
            )

            val teamBlue = players.filter { it.team == Team.BLUE }.sortedByDescending { it.level }
            val teamRed = players.filter { it.team == Team.RED }.sortedByDescending { it.level }

            val mostKillsBlue = if (teamBlue.isEmpty()) 0 else teamBlue.map { it.kills }.sorted().reversed()[0]
            val mostKillsRed = if (teamRed.isEmpty()) 0 else teamRed.map { it.kills }.sorted().reversed()[0]
            val mostDeathsBlue = if (teamBlue.isEmpty()) 0 else teamBlue.map { it.deaths }.sorted().reversed()[0]
            val mostDeathsRed = if (teamRed.isEmpty()) 0 else teamRed.map { it.deaths }.sorted().reversed()[0]

            var width = 460

//        if (!showDoneAndReceived) {
//            showDiedToYouStoleKill = false
//        }

            if (!showDiedToYouStoleKill) {
                width -= 133
            } else if (!showTopHeader) {
                width -= 100
            }
            if (!showDoneAndReceived) {
                width -= 105
            }
            //xStart += moveScoreboard
            //scaling width to fit the screen, width is 100 when guiScaledWidth screen is 640, make it proportional
            val guiScale = scaledWidth / 1050.0
            width = (width * guiScale).toInt()
            val unscaledWidth = width / guiScale
            //scale(guiScale)

            var xStart = xCenter - width - spaceBetweenSplit / 2
            val yStart = 25

            GameStateManager.currentGameMode.getScale()?.let { scale ->
                poseStack.scale(scale.toFloat(), scale.toFloat(), 1f)
                xStart = (xStart / scale).toInt()
            }

            var xLevel = 2.0
            var xName = 53.0
            var xKills = 100.0
            var xDeaths = 35.0
            var xDone = 40.0
            var xReceived = 50.0
            var xKilled = 60.0

            if (!showTopHeader) {
                xDeaths = 25.0
                xDone = 30.0
            }

            fun renderHeader() {
                translateY(-3)
                translateX(xLevel + xName)
                "Name".draw()
                if (!showDoneAndReceived && !showDiedToYouStoleKill) {
                    translateX(xKills)
                    "K".draw()
                    translateX(xDeaths)
                    "D".draw()
                } else {
                    translateX(xKills)
                    "Kills".draw()
                    translateX(xDeaths)
                    "Deaths".draw()
                }
                if (showDoneAndReceived) {
                    translateX(xDone)
                    "Given".draw()
                    if (showTopHeader) {
                        if (showDiedToYouStoleKill) {
                            translateX(xReceived)
                        } else {
                            translateX(xReceived - 12.5)
                        }
                        "Received".draw()
                    }
                }
                if (showDiedToYouStoleKill) {
                    translateX(xKilled)
                    "DiedToYou/StoleKill".draw()
                }
            }

            if (showTopHeader) {
                translate(xStart, yStart)
                poseStack {
                    scale(guiScale)
                    renderRect(unscaledWidth, 13.0, Colors.DEF)
                    translateZ(-2)
                    renderHeader()
                }
                if (splitScoreBoard) {
                    poseStack {
                        poseStack {
                            scale(guiScale)
                            translateX(unscaledWidth + spaceBetweenSplit)
                            renderRect(unscaledWidth, 13.0, Colors.DEF)
                            translateZ(-2)
                            renderHeader()
                        }
                    }
                }
            } else {
                translate(xStart, yStart - 15)
            }


            fun renderLine(index: Int, p: OtherWarlordsPlayer) {
                val isThePlayer = p.uuid == thePlayer.uuid
                val dead = p.isDead
                val hasMostDeaths = if (p.team == Team.BLUE) p.deaths == mostDeathsBlue else p.deaths == mostDeathsRed
                val hasMostKills = if (p.team == Team.BLUE) p.kills == mostKillsBlue else p.kills == mostKillsRed

                if (showOutline) {
                    translateY(-2) {
                        renderRect(unscaledWidth, 1.25, Colors.DEF)
                        renderRect(1.25, 11.0, Colors.DEF)
                    }
                    translate(unscaledWidth - 1.25, -2.0) {
                        renderRect(1.25, 11.0, Colors.DEF)
                    }
                    translateY(8.75) {
                        renderRect(unscaledWidth, 1.25, Colors.DEF)
                    }
                } else {
                    if (index % 2 == 1) {
                        translateY(-1.2) {
                            renderRect(unscaledWidth, 10.75, Colors.DEF, alpha = 40)
                        }
                    }
                }

                poseStack {
                    translate(xLevel, .5, -2.0)
                    Component.empty()
                        .append(Component.literal(p.warlordClass.shortName)
                            .withStyle { it.withColor(p.levelColor) }
                        )
                        .append(" ${p.level.toString().padStart(2, '0')} ")
                        .append(if (isThePlayer) WarlordsPlayer.spec.iconComponent else p.spec.iconComponent)
                        .draw()
                    translateX(xName)
                    Component.empty()
                        .append(Component.literal(if (p.hasFlag) "⚐ " else "")
                            .withStyle { it.withColor(p.team.color) }
                        )
                        .append(Component.literal("${if (dead && !GameStateManager.inWarlords2) "${p.respawn} " else ""}${p.name}")
                            .withStyle {
                                it.withColor(
                                    if (isThePlayer) ChatFormatting.GREEN
                                    else if (dead) ChatFormatting.GRAY
                                    else p.team.color
                                ).withStrikethrough(p.left)
                            }
                        )
                        .draw()
                    translateX(xKills)
                    Component.literal(p.kills.toString())
                        .withStyle {
                            it.withColor(
                                if (hasMostKills) ChatFormatting.GOLD
                                else null
                            )
                        }
                        .draw()
                    translateX(xDeaths)
                    Component.literal(p.deaths.toString())
                        .withStyle {
                            it.withColor(
                                if (hasMostDeaths) ChatFormatting.DARK_RED
                                else null
                            )
                        }
                        .draw()
                    if (WarlordsPlayer.team == p.team) {
                        if (showDoneAndReceived) {
                            translateX(xDone)
                            Component.literal(p.healingReceived.toString()).withStyle { it.withColor(ChatFormatting.GREEN) }.draw()
                            translateX(xReceived)
                            Component.literal(p.healingDone.toString()).withStyle { it.withColor(ChatFormatting.DARK_GREEN) }.draw()
                        }
                        if (showDiedToYouStoleKill) {
                            translateX(xKilled)
                            Component.literal(p.stoleKill.toString()).draw()
                        }
                    } else {
                        if (showDoneAndReceived) {
                            translateX(xDone)
                            Component.literal(p.damageReceived.toString()).withStyle { it.withColor(ChatFormatting.RED) }.draw()
                            translateX(xReceived)
                            Component.literal(p.damageDone.toString()).withStyle { it.withColor(ChatFormatting.DARK_RED) }.draw()
                        }
                        if (showDiedToYouStoleKill) {
                            translateX(xKilled)
                            Component.literal(p.died.toString()).draw()
                        }
                    }
                }

                translateY(-10.75)
            }

            scale(guiScale)
            if (splitScoreBoard) {
                translateY(-14)
                poseStack {
                    renderRect(unscaledWidth, 10.75 * teamBlue.size + 1, Colors.DEF, 100)
                    translateY(-2)
                    translateZ(-20)
//                    translateY(-20) {
//                        guiGraphics.fill(10, 40, 20, 60, Colors.DEF.convertToArgb(100))
//                    }
                    teamBlue.forEachIndexed(::renderLine)
                }
                translateX(unscaledWidth + spaceBetweenSplit)
                poseStack {
                    renderRect(unscaledWidth, 10.75 * teamRed.size + 1, Colors.DEF, 100)
                    translateY(-2)
                    translateZ(-20)
                    teamRed.forEachIndexed(::renderLine)
                }
            } else {
                translateY(-14)
                renderRect(unscaledWidth, 10.75 * teamBlue.size + 1, Colors.DEF, 100)
                translateY(-2)
                translateZ(-20)
                teamBlue.forEachIndexed(::renderLine)

                translateY(-1)
                renderRect(unscaledWidth, 10.75 * teamRed.size + 1, Colors.DEF, 100)
                translateY(-2)
                translateZ(-20)
                teamRed.forEachIndexed(::renderLine)
            }
        }
    }


}