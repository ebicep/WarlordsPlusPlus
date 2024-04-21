package com.ebicep.warlordsplusplus.config.fabric

import com.ebicep.warlordsplusplus.config.Config
import me.shedaniel.clothconfig2.api.ConfigBuilder
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component
import java.util.function.Consumer

object ConfigScreenImpl {

    private fun ConfigEntryBuilder.booleanToggle(translatable: String, variable: Boolean, saveConsumer: Consumer<Boolean>) =
        startBooleanToggle(Component.translatable(translatable), variable)
            .setDefaultValue(variable)
            .setTooltip(Component.translatable("$translatable.tooltip"))
            .setSaveConsumer(saveConsumer)
            .build()

    private fun ConfigEntryBuilder.percentSlider(translatable: String, variable: Int, saveConsumer: Consumer<Int>) =
        startIntSlider(Component.translatable(translatable), variable, 0, 100)
            .setDefaultValue(variable)
            .setTooltip(Component.translatable("$translatable.tooltip"))
            .setSaveConsumer(saveConsumer)
            .build()

    @JvmStatic
    fun getConfigScreen(previousScreen: Screen? = null): Screen {
        val builder: ConfigBuilder = ConfigBuilder.create()
            .setParentScreen(previousScreen)
            .setTitle(Component.literal("warlordsplusplus.title"))
            .setSavingRunnable(Config::save)
            .transparentBackground()
        val entryBuilder: ConfigEntryBuilder = builder.entryBuilder()
        addGeneralOptions(builder, entryBuilder)
        addScoreboardOptions(builder, entryBuilder)
        addRendererOptions(builder, entryBuilder)
        addChatOptions(builder, entryBuilder)
        return builder.build()
    }

    private fun addGeneralOptions(builder: ConfigBuilder, entryBuilder: ConfigEntryBuilder) {
        val general = builder.getOrCreateCategory(Component.translatable("warlordsplusplus.config.general.title"))
        general.addEntry(
            entryBuilder.booleanToggle(
                "warlordsplusplus.config.general.enabled",
                Config.values.enabled
            ) { Config.values.enabled = it }
        )
    }

    private fun addScoreboardOptions(builder: ConfigBuilder, entryBuilder: ConfigEntryBuilder) {
        val scoreboard = builder.getOrCreateCategory(Component.translatable("warlordsplusplus.config.scoreboard.title"))
        scoreboard.addEntry(
            entryBuilder.booleanToggle(
                "warlordsplusplus.config.scoreboard.enabled",
                Config.values.scoreboardEnabled
            ) { Config.values.scoreboardEnabled = it }
        )
        scoreboard.addEntry(
            entryBuilder.percentSlider(
                "warlordsplusplus.config.scoreboard.scaleCTFTDM",
                Config.values.scoreboardScaleCTFTDM
            ) { Config.values.scoreboardScaleCTFTDM = it }
        )
        scoreboard.addEntry(
            entryBuilder.percentSlider(
                "warlordsplusplus.config.scoreboard.scaleDOM",
                Config.values.scoreboardScaleDOM
            ) { Config.values.scoreboardScaleDOM = it }
        )
        scoreboard.addEntry(
            entryBuilder.booleanToggle(
                "warlordsplusplus.config.scoreboard.showTopHeader.enabled",
                Config.values.scoreboardShowTopHeader
            ) { Config.values.scoreboardShowTopHeader = it }
        )
        scoreboard.addEntry(
            entryBuilder.booleanToggle(
                "warlordsplusplus.config.scoreboard.showOutline.enabled",
                Config.values.scoreboardShowOutline
            ) { Config.values.scoreboardShowOutline = it }
        )
        scoreboard.addEntry(
            entryBuilder.booleanToggle(
                "warlordsplusplus.config.scoreboard.showDiedToYouStoleKill.enabled", Config.values
                    .scoreboardShowDiedToYouStoleKill
            ) { Config.values.scoreboardShowDiedToYouStoleKill = it }
        )
        scoreboard.addEntry(
            entryBuilder.booleanToggle(
                "warlordsplusplus.config.scoreboard.showDoneAndReceived.enabled",
                Config.values.scoreboardShowDoneAndReceived
            ) { Config.values.scoreboardShowDoneAndReceived = it }
        )
        scoreboard.addEntry(
            entryBuilder.booleanToggle(
                "warlordsplusplus.config.scoreboard.splitScoreBoard.enabled",
                Config.values.scoreboardSplitScoreBoard
            ) { Config.values.scoreboardSplitScoreBoard = it }
        )
    }

    private fun addRendererOptions(builder: ConfigBuilder, entryBuilder: ConfigEntryBuilder) {
        val renderer = builder.getOrCreateCategory(Component.translatable("warlordsplusplus.config.renderer.title"))
        renderer.addEntry(
            entryBuilder.booleanToggle(
                "warlordsplusplus.config.renderer.renderPlayerInfo",
                Config.values.renderPlayerInfo
            ) { Config.values.renderPlayerInfo = it }
        )
    }

    private fun addChatOptions(builder: ConfigBuilder, entryBuilder: ConfigEntryBuilder) {
        val chat = builder.getOrCreateCategory(Component.translatable("warlordsplusplus.config.chat.title"))
        chat.addEntry(
            entryBuilder.booleanToggle(
                "warlordsplusplus.config.chat.printAbilityStatsAfterGame",
                Config.values.printAbilityStatsAfterGame
            ) { Config.values.printAbilityStatsAfterGame = it }
        )
        chat.addEntry(
            entryBuilder.booleanToggle(
                "warlordsplusplus.config.chat.printGeneralStatsAfterGame",
                Config.values.printGeneralStatsAfterGame
            ) { Config.values.printGeneralStatsAfterGame = it }
        )
        chat.addEntry(
            entryBuilder.booleanToggle(
                "warlordsplusplus.config.chat.printScoreboardStatsAfterGame",
                Config.values.printScoreboardStatsAfterGame
            ) { Config.values.printScoreboardStatsAfterGame = it }
        )
    }

}