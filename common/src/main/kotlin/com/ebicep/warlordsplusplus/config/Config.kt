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
import com.ebicep.warlordsplusplus.features.hud.elements.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
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
    var enabled: MutableBoolean = MutableBoolean(),
    // hud rendering
    var hudElements: HudElementVariables = HudElementVariables(),
    // world rendering
    var renderPlayerInfo: MutableBoolean = MutableBoolean(),
    // scoreboard
    var scoreboardEnabled: MutableBoolean = MutableBoolean(),
    var scoreboardScaleCTFTDM: MutableInt = MutableInt(),
    var scoreboardScaleDOM: MutableInt = MutableInt(),
    var scoreboardShowTopHeader: MutableBoolean = MutableBoolean(),
    var scoreboardShowOutline: MutableBoolean = MutableBoolean(),
    var scoreboardShowDiedToYouStoleKill: MutableBoolean = MutableBoolean(false),
    var scoreboardShowDoneAndReceived: MutableBoolean = MutableBoolean(),
    var scoreboardSplitScoreBoard: MutableBoolean = MutableBoolean(),
    // chat
    var printAbilityStatsAfterGame: MutableBoolean = MutableBoolean(),
    var printGeneralStatsAfterGame: MutableBoolean = MutableBoolean(),
    var printScoreboardStatsAfterGame: MutableBoolean = MutableBoolean(false),
)

@Serializable
data class HudElementVariables(
    var damageDone: HudElementValues = HudElementValues(10, 10),
    var healingGiven: HudElementValues = HudElementValues(10, 21),
    var damageTaken: HudElementValues = HudElementValues(10, 32),
    var healingReceived: HudElementValues = HudElementValues(10, 43),
    var regenTimer: HudElementValues = HudElementValues(0, 44),

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