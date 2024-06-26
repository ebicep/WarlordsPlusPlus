package com.ebicep.warlordsplusplus.detectors

import com.ebicep.chatplus.events.EventBus
import com.ebicep.warlordsplusplus.events.WarlordsGameEvents

import dev.architectury.event.CompoundEventResult
import dev.architectury.event.events.client.ClientSystemMessageEvent
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent

object GameEndDetector : Detector {

    var divider = ""
    var dividerComponent: MutableComponent = Component.empty()
        get() = Component.literal(divider).withStyle {
            it.withColor(ChatFormatting.GREEN).withBold(true)
        }
    var canPost = false
    var counter = 0


    init {
        EventBus.register<WarlordsGameEvents.ResetEvent> {
            canPost = false
            counter = 0
        }
        ClientSystemMessageEvent.RECEIVED.register { component: Component ->
            val unformattedText = component.string

            if (unformattedText.contains("YOUR STATISTICS")) {
                canPost = true
                counter = 0
            }
            if (unformattedText.contains("▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬")) {
                divider = unformattedText
                counter++
                if (counter > 0 && canPost) {
                    EventBus.post(WarlordsGameEvents.GameEndEvent())
                    canPost = false
                }
            }
            CompoundEventResult.pass()
        }
    }
}