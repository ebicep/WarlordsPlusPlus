package com.ebicep.warlordsplusplus

import com.ebicep.warlordsplusplus.config.Config
import com.ebicep.warlordsplusplus.config.ConfigScreen
import com.ebicep.warlordsplusplus.detectors.DetectorManager
import com.ebicep.warlordsplusplus.features.FeatureManager
import com.ebicep.warlordsplusplus.game.GameStateManager
import dev.architectury.event.events.client.ClientTickEvent
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

const val MOD_ID = "warlordsplusplus"

object WarlordsPlusPlus {

    val LOGGER: Logger = LogManager.getLogger(MOD_ID)

    fun init() {
        GameStateManager
        DetectorManager
        FeatureManager

        Config.load()

        ClientTickEvent.CLIENT_POST.register {
            ConfigScreen.handleOpenScreen()
        }
    }

    fun isEnabled(): Boolean {
        return Config.values.enabled.value
    }
}