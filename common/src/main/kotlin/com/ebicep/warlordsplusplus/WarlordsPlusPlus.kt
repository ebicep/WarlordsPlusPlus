package com.ebicep.warlordsplusplus

import com.ebicep.warlordsplusplus.ExpectPlatform.getConfigDirectory
import com.ebicep.warlordsplusplus.channel.WarlordsPvEPacketHandler
import com.ebicep.warlordsplusplus.detectors.DetectorEventHandler
import dev.architectury.event.CompoundEventResult
import dev.architectury.event.events.client.ClientSystemMessageEvent
import net.minecraft.network.chat.Component
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

const val MOD_ID = "warlordsplusplus"

object WarlordsPlusPlus {

    val LOGGER: Logger = LogManager.getLogger(MOD_ID)

    fun init() {
        LOGGER.info("CONFIG DIR: ${getConfigDirectory().toAbsolutePath().normalize()}")
        WarlordsPvEPacketHandler
        DetectorEventHandler

        ClientSystemMessageEvent.RECEIVED.register { component: Component ->
            LOGGER.info("TEST")
            CompoundEventResult.pass()
        }
    }

    fun isEnabled(): Boolean {
        return true
    }
}