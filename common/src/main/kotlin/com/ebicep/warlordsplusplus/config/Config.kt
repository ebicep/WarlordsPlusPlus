package com.ebicep.warlordsplusplus.config

import com.ebicep.warlordsplusplus.ExpectPlatform
import com.ebicep.warlordsplusplus.MOD_ID
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.File

private val json = Json {
    prettyPrint = true
    ignoreUnknownKeys = true
    encodeDefaults = true
}

object Config {

    var values = ConfigVariables()

    fun save() {
        val configDirectory = File(ExpectPlatform.getConfigDirectory().toString(), "config")
        if (!configDirectory.exists()) {
            configDirectory.mkdir()
        }
        val configFile = File(configDirectory, "$MOD_ID.json")
        configFile.writeText(json.encodeToString(ConfigVariables.serializer(), values))
    }

    fun load() {
        val configDirectory = File(ExpectPlatform.getConfigDirectory().toString(), "config")
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
    var enabled: Boolean = true,
    var scoreboardEnabled: Boolean = true,
    var scoreboardScaleCTFTDM: Int = 100,
    var scoreboardScaleDOM: Int = 100,
    var scoreboardShowTopHeader: Boolean = true,
    var scoreboardShowOutline: Boolean = true,
    var scoreboardShowDiedToYouStoleKill: Boolean = false,
    var scoreboardShowDoneAndReceived: Boolean = true,
    var scoreboardSplitScoreBoard: Boolean = true,
    var renderPlayerInfo: Boolean = true,
    var printAbilityStatsAfterGame: Boolean = true,
    var printGeneralStatsAfterGame: Boolean = true,
    var printScoreboardStatsAfterGame: Boolean = true
)
