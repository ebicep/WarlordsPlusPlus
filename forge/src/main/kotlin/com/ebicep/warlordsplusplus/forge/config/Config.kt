package com.ebicep.warlordsplusplus.forge.config

import com.ebicep.warlordsplusplus.MOD_ID
import me.shedaniel.autoconfig.ConfigData
import me.shedaniel.autoconfig.annotation.Config
import me.shedaniel.autoconfig.annotation.ConfigEntry
import java.util.*

@Config(name = MOD_ID)
class Config : ConfigData {

    @ConfigEntry.Category("general")
    @ConfigEntry.Gui.TransitiveObject
    var general = ConfigGeneral()

    @ConfigEntry.Category("scoreboard")
    @ConfigEntry.Gui.TransitiveObject
    var scoreboard = ConfigScoreboard()

    @ConfigEntry.Category("renderer")
    @ConfigEntry.Gui.TransitiveObject
    var renderer = ConfigRenderer()

    @ConfigEntry.Category("chat")
    @ConfigEntry.Gui.TransitiveObject
    var chat = ConfigChat()


    @Config(name = "general")
    class ConfigGeneral : ConfigData {

        var enabled = true

    }

    @Config(name = "scoreboard")
    class ConfigScoreboard : ConfigData {

        var enabled = true

        @ConfigEntry.BoundedDiscrete(min = 0, max = 100L)
        var scaleCTFTDM = 100

        @ConfigEntry.BoundedDiscrete(min = 0, max = 100L)
        var scaleDOM = 100

        var showTopHeader = true
        var showOutline = true
        val showDiedToYouStoleKill = false
        val showDoneAndReceived = true
        val splitScoreBoard = true

    }

    @Config(name = "renderer")
    class ConfigRenderer : ConfigData {

        var renderPlayerInfo = true

    }

    @Config(name = "chat")
    class ConfigChat : ConfigData {

        var printAbilityStats = true
        var printGeneralStats = true
        var printScoreboardStats = true

    }

}