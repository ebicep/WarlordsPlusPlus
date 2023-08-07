package com.ebicep.warlordsplusplus.detectors

import com.ebicep.warlordsplusplus.event.WarlordsGameEvents
import com.ebicep.warlordsplusplus.event.WarlordsGameEventsImpl
import dev.architectury.event.CompoundEventResult
import dev.architectury.event.events.client.ClientChatEvent
import net.minecraft.network.chat.ChatType
import net.minecraft.network.chat.Component

object DetectorEventHandler {

    init {
        DamageAndHealParser
        KillAssistParser
        GameEndDetector
        HitDetector
        RespawnTimerDetector
    }

}