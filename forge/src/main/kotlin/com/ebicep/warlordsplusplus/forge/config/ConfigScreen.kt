package com.ebicep.warlordsplusplus.forge.config

import com.ebicep.warlordsplusplus.config.Config
import com.ebicep.warlordsplusplus.config.mutable.MutableBoolean
import com.ebicep.warlordsplusplus.config.mutable.MutableInt
import me.shedaniel.clothconfig2.api.ConfigBuilder
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component

object ConfigScreen {

    private fun ConfigEntryBuilder.booleanToggle(translatable: String, variable: MutableBoolean) =
        startBooleanToggle(Component.translatable(translatable), variable.value)
            .setDefaultValue(variable.value)
            .setTooltip(Component.translatable("$translatable.tooltip"))
            .setSaveConsumer { variable.value = it }
            .build()

    private fun ConfigEntryBuilder.percentSlider(translatable: String, variable: MutableInt) =
        startIntSlider(Component.translatable(translatable), 0, 100, variable.value)
            .setDefaultValue(variable.value)
            .setTooltip(Component.translatable("$translatable.tooltip"))
            .setSaveConsumer { variable.value = it }
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
        general.addEntry(entryBuilder.booleanToggle("warlordsplusplus.config.general.enabled", Config.values.enabled))
    }

    private fun addScoreboardOptions(builder: ConfigBuilder, entryBuilder: ConfigEntryBuilder) {
        val scoreboard = builder.getOrCreateCategory(Component.translatable("warlordsplusplus.config.scoreboard.title"))
        scoreboard.addEntry(
            entryBuilder.booleanToggle(
                "warlordsplusplus.config.scoreboard.enabled",
                Config.values.scoreboardEnabled
            )
        )
        scoreboard.addEntry(
            entryBuilder.percentSlider(
                "warlordsplusplus.config.scoreboard.scaleCTFTDM",
                Config.values.scoreboardScaleCTFTDM
            )
        )
        scoreboard.addEntry(
            entryBuilder.percentSlider(
                "warlordsplusplus.config.scoreboard.scaleDOM",
                Config.values.scoreboardScaleDOM
            )
        )
        scoreboard.addEntry(
            entryBuilder.booleanToggle(
                "warlordsplusplus.config.scoreboard.showTopHeader.enabled",
                Config.values.scoreboardShowTopHeader
            )
        )
        scoreboard.addEntry(
            entryBuilder.booleanToggle(
                "warlordsplusplus.config.scoreboard.showOutline.enabled",
                Config.values.scoreboardShowOutline
            )
        )
        scoreboard.addEntry(
            entryBuilder.booleanToggle(
                "warlordsplusplus.config.scoreboard.showDiedToYouStoleKill.enabled", Config.values
                    .scoreboardShowDiedToYouStoleKill
            )
        )
        scoreboard.addEntry(
            entryBuilder.booleanToggle(
                "warlordsplusplus.config.scoreboard.showDoneAndReceived.enabled",
                Config.values.scoreboardShowDoneAndReceived
            )
        )
        scoreboard.addEntry(
            entryBuilder.booleanToggle(
                "warlordsplusplus.config.scoreboard.splitScoreBoard.enabled",
                Config.values.scoreboardSplitScoreBoard
            )
        )
    }

    private fun addRendererOptions(builder: ConfigBuilder, entryBuilder: ConfigEntryBuilder) {
        val renderer = builder.getOrCreateCategory(Component.translatable("warlordsplusplus.config.renderer.title"))
        renderer.addEntry(
            entryBuilder.booleanToggle(
                "warlordsplusplus.config.renderer.renderPlayerInfo",
                Config.values.renderPlayerInfo
            )
        )
    }

    private fun addChatOptions(builder: ConfigBuilder, entryBuilder: ConfigEntryBuilder) {
        val chat = builder.getOrCreateCategory(Component.translatable("warlordsplusplus.config.chat.title"))
        chat.addEntry(
            entryBuilder.booleanToggle(
                "warlordsplusplus.config.chat.printAbilityStatsAfterGame",
                Config.values.printAbilityStatsAfterGame
            )
        )
        chat.addEntry(
            entryBuilder.booleanToggle(
                "warlordsplusplus.config.chat.printGeneralStatsAfterGame",
                Config.values.printGeneralStatsAfterGame
            )
        )
        chat.addEntry(
            entryBuilder.booleanToggle(
                "warlordsplusplus.config.chat.printScoreboardStatsAfterGame",
                Config.values.printAbilityStatsAfterGame
            )
        )
    }

}