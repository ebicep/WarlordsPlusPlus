@file:UseSerializers(
    MutableBooleanSerializer::class,
    MutableIntSerializer::class
)

package com.ebicep.warlordsplusplus.config

import com.ebicep.warlordsplusplus.MOD_ID
import com.ebicep.warlordsplusplus.WarlordsPlusPlus
import com.ebicep.warlordsplusplus.config.mutable.MutableBoolean
import com.ebicep.warlordsplusplus.config.mutable.MutableBooleanSerializer
import com.ebicep.warlordsplusplus.config.mutable.MutableInt
import com.ebicep.warlordsplusplus.config.mutable.MutableIntSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import kotlinx.serialization.json.Json
import java.io.File

private val json = Json {
    encodeDefaults = true
    ignoreUnknownKeys = true
    prettyPrint = true
}

object Config {

    var values = ConfigVariables()

    fun save() {
        val configDirectory = File(ConfigDirectory.getConfigDirectory().toString())
        if (!configDirectory.exists()) {
            configDirectory.mkdir()
        }
        val configFile = File(configDirectory, "$MOD_ID.json")
        configFile.writeText(json.encodeToString(ConfigVariables.serializer(), values))
    }

    fun load() {
        WarlordsPlusPlus.LOGGER.info("Config Directory: ${ConfigDirectory.getConfigDirectory().toAbsolutePath().normalize()}")
        val configDirectory = File(ConfigDirectory.getConfigDirectory().toString())
        if (!configDirectory.exists()) {
            return
        }
        val configFile = File(configDirectory, "$MOD_ID.json")
        if (configFile.exists()) {
            val json = Json {
                prettyPrint = true
                ignoreUnknownKeys = true
                encodeDefaults = true
            }
            values = json.decodeFromString(ConfigVariables.serializer(), configFile.readText())
        }
    }

}

@Serializable
data class ConfigVariables(
    var enabled: MutableBoolean = MutableBoolean(),
    var scoreboardEnabled: MutableBoolean = MutableBoolean(),
    var scoreboardScaleCTFTDM: MutableInt = MutableInt(),
    var scoreboardScaleDOM: MutableInt = MutableInt(),
    var scoreboardShowTopHeader: MutableBoolean = MutableBoolean(),
    var scoreboardShowOutline: MutableBoolean = MutableBoolean(),
    var scoreboardShowDiedToYouStoleKill: MutableBoolean = MutableBoolean(false),
    var scoreboardShowDoneAndReceived: MutableBoolean = MutableBoolean(),
    var scoreboardSplitScoreBoard: MutableBoolean = MutableBoolean(),
    var renderPlayerInfo: MutableBoolean = MutableBoolean(),
    var printAbilityStatsAfterGame: MutableBoolean = MutableBoolean(),
    var printGeneralStatsAfterGame: MutableBoolean = MutableBoolean(),
    var printScoreboardStatsAfterGame: MutableBoolean = MutableBoolean(false),
)
