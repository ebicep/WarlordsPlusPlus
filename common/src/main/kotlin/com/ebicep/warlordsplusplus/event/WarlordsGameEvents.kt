package com.ebicep.warlordsplusplus.event

import com.ebicep.chatplus.events.Event
import java.time.Instant

class WarlordsGameEvents {

    data class ResetEvent(val time: Instant? = Instant.now()) : Event
    data class GameEndEvent(val time: Instant? = Instant.now()) : Event
    data class MinuteEvent(val minute: Int) : Event
    data class SecondEvent(val second: Int) : Event

}