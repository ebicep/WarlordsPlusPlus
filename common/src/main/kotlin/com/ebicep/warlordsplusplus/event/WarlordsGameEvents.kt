package com.ebicep.warlordsplusplus.event

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import java.time.Instant
import java.util.function.Function

class WarlordsGameEvents {

    data class ResetEvent(val time: Instant? = Instant.now())
    data class GameEndEvent(val time: Instant? = Instant.now())
    data class MinuteEvent(val minute: Int)
    data class SecondEvent(val second: Int)

}