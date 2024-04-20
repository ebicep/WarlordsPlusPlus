package com.ebicep.warlordsplusplus.events

import com.ebicep.chatplus.events.Event
import java.time.Instant

object WarlordsGameEvents {

    data class ResetEvent(val time: Instant? = Instant.now()) : Event
    data class GameEndEvent(val time: Instant? = Instant.now()) : Event
    data class GameWinEvent(val time: Instant? = Instant.now()) : Event
    data class GameLossEvent(val time: Instant? = Instant.now()) : Event
    data class MinuteEvent(val minute: Int) : Event
    data class SecondEvent(val second: Int) : Event
    data class FlagPickedUpEvent(val picker: String) : Event
    data class FlagDroppedEvent(val dropper: String) : Event
    data class FlagReturnedEvent(val returner: String) : Event
    data class FlagCapturedEvent(val capper: String) : Event

}