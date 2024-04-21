package com.ebicep.warlordsplusplus.features

import com.ebicep.chatplus.events.EventBus
import com.ebicep.warlordsplusplus.events.RenderJumpMeterEvent
import net.minecraft.client.Minecraft

object ShowEnergyOnHorse : Feature {

    init {
        EventBus.register<RenderJumpMeterEvent> {
            Minecraft.getInstance().gui.renderExperienceBar(it.guiGraphics, it.i)
            it.cancel = true
        }
    }

}