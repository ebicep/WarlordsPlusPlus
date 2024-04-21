package com.ebicep.warlordsplusplus.config

import com.ebicep.warlordsplusplus.MOD_ID
import com.ebicep.warlordsplusplus.WarlordsPlusPlus
import com.ebicep.warlordsplusplus.features.hud.elements.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.File

private val json = Json {
    encodeDefaults = true
    ignoreUnknownKeys = true
    prettyPrint = true
}
val configDirectoryPath: String
    get() = ConfigDirectory.getConfigDirectory().toString() + "\\warlordsplusplus"

object Config {

    var values = ConfigVariables()

    fun save() {
        val configDirectory = File(configDirectoryPath)
        if (!configDirectory.exists()) {
            configDirectory.mkdir()
        }
        val configFile = File(configDirectory, "$MOD_ID.json")
        configFile.writeText(json.encodeToString(ConfigVariables.serializer(), values))
    }

    fun load() {
        WarlordsPlusPlus.LOGGER.info("Config Directory: ${ConfigDirectory.getConfigDirectory().toAbsolutePath().normalize()}")
        val configDirectory = File(configDirectoryPath)
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
    // general
    var enabled: Boolean = true,
    // hud rendering
    var hudElements: HudElementVariables = HudElementVariables(),
    // world rendering
    var renderPlayerInfo: Boolean = true,
    // scoreboard
    var scoreboardEnabled: Boolean = true,
    var scoreboardScaleCTFTDM: Int = 100,
    var scoreboardScaleDOM: Int = 100,
    var scoreboardShowTopHeader: Boolean = true,
    var scoreboardShowOutline: Boolean = true,
    var scoreboardShowDiedToYouStoleKill: Boolean = false,
    var scoreboardShowDoneAndReceived: Boolean = true,
    var scoreboardSplitScoreBoard: Boolean = true,
    // chat
    var printAbilityStatsAfterGame: Boolean = true,
    var printGeneralStatsAfterGame: Boolean = true,
    var printScoreboardStatsAfterGame: Boolean = false,
)

@Serializable
data class HudElementVariables(
    var damageDone: HudElementValues = HudElementValues(10, 10),
    var healingGiven: HudElementValues = HudElementValues(10, 21),
    var damageTaken: HudElementValues = HudElementValues(10, 32),
    var healingReceived: HudElementValues = HudElementValues(10, 43),
    var regenTimer: HudElementValues = HudElementValues(0, 44),

    var bluePoints: HudElementValues = HudElementValues(0, 0),
    var redPoints: HudElementValues = HudElementValues(0, 0),
    var blueKills: HudElementValues = HudElementValues(0, 0),
    var redKills: HudElementValues = HudElementValues(0, 0),

    var kda: HudElementValues = HudElementValues(10, 10),
    var wl: HudElementValues = HudElementValues(10, 21),
    var streak: HudElementValues = HudElementValues(10, 32),

    var respawnTimer: HudElementValues = HudElementValues(0, 0)
)

@Serializable
data class HudElementValues(
    var x: Int = 0,
    var y: Int = 0,
    var enabled: Boolean = true,
)