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
        }
        ClientTickEvent.CLIENT_POST.register {
            if (openScreen) {
                openScreen = false
                Minecraft.getInstance().setScreen(HudElementScreen())
            }
        }
        ClientGuiEvent.RENDER_HUD.register { guiGraphics, tickDelta ->
            hudElements.forEach {
                if (it.renderHelper == null) {
                    return@register
                }
                it.renderHelper!!.guiGraphics = guiGraphics
                it.renderHelper!!.createPose {
                    it.renderHelper!!.translate(it.x.toDouble(), it.y.toDouble())
                    it.renderHelper!!.render()
                }
            }
        }
    }

    fun openEditScreen() {
        openScreen = true
    }

}