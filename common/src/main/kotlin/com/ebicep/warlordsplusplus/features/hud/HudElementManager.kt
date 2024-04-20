package com.ebicep.warlordsplusplus.features.hud

import com.ebicep.warlordsplusplus.features.Feature
import com.ebicep.warlordsplusplus.features.hud.elements.*
import dev.architectury.event.events.client.ClientGuiEvent
import dev.architectury.event.events.client.ClientTickEvent
import net.minecraft.client.Minecraft

object HudElementManager : Feature {

    private var openScreen = false
    val hudElements = mutableListOf<AbstractHudElement>()

    init {
        with(hudElements) {
            add(DamageDoneHudElement)
            add(DamageTakenHudElement)
            add(HealingGivenHudElement)
            add(HealingReceivedHudElement)
            add(KDAHudElement)
            add(WLHudElement)
            add(StreakHudElement)
            add(RegenTimerHudElement)
            add(BluePointsHudElement)
            add(RedPointsHudElement)
            add(BlueKillsHudElement)
            add(RedKillsHudElement)

            add(RespawnTimerDisplay)
        }
        ClientTickEvent.CLIENT_POST.register {
            if (openScreen) {
                openScreen = false
                Minecraft.getInstance().setScreen(HudElementScreen())
            }
        }
        ClientGuiEvent.RENDER_HUD.register { guiGraphics, _ ->
            hudElements.forEach {
                it.guiGraphics = guiGraphics
                it.createPose {
                    it.translate(it.x.toDouble(), it.y.toDouble())
                    it.render()
                }
            }
        }
    }

    fun openEditScreen() {
        openScreen = true
    }

}