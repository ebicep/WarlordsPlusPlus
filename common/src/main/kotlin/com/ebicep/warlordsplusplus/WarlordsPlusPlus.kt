package com.ebicep.warlordsplusplus

import com.ebicep.warlordsplusplus.ExpectPlatform.getConfigDirectory
import com.ebicep.warlordsplusplus.config.Config
import com.ebicep.warlordsplusplus.detectors.DetectorManager
import com.ebicep.warlordsplusplus.modules.ModuleManager
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

const val MOD_ID = "warlordsplusplus"

object WarlordsPlusPlus {

    val LOGGER: Logger = LogManager.getLogger(MOD_ID)

    fun init() {
        LOGGER.info("CONFIG DIR: ${getConfigDirectory().toAbsolutePath().normalize()}")
        DetectorManager
        ModuleManager
    }

    fun isEnabled(): Boolean {
        return Config.values.enabled
    }
}