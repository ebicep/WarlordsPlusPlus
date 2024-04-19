package com.ebicep.warlordsplusplus.features.hud.elements

import com.ebicep.chatplus.events.EventBus
import com.ebicep.warlordsplusplus.events.WarlordsPlayerEvents
import com.ebicep.warlordsplusplus.util.ComponentBuilder
import net.minecraft.ChatFormatting
import net.minecraft.client.Minecraft
import net.minecraft.network.chat.MutableComponent

object KDAHudElement : AbstractHighlightedHudElement(0, 0) {

    private var kills = 0
    private var deaths = 0
    private var assists = 0

    init {
        EventBus.register<WarlordsPlayerEvents.KillEvent> {
            if (it.killer == Minecraft.getInstance().player!!.scoreboardName) {
                kills++
            } else if (it.deathPlayer == Minecraft.getInstance().player!!.scoreboardName) {
                deaths++
            }
        }
        EventBus.register<WarlordsPlayerEvents.KillStealEvent> {
            assists++
        }
    }

    override fun shouldRender(): Boolean {
        return true
    }

    override fun getComponent(): MutableComponent {
        return ComponentBuilder("Total K/D/A: ", ChatFormatting.WHITE)
            .append("$kills", ChatFormatting.GREEN)
            .append("/")
            .append("$deaths", ChatFormatting.RED)
            .append("/")
            .append("$assists", ChatFormatting.YELLOW)
            .create()
    }


}