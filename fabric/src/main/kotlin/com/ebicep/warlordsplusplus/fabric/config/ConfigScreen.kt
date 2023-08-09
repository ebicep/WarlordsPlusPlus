package com.ebicep.warlordsplusplus.fabric.config

import me.shedaniel.clothconfig2.api.ConfigBuilder
import net.minecraft.client.Minecraft
import net.minecraft.network.chat.Component

object ConfigScreen {

    var open = false

    fun handleOpenScreen() {
        if (open) {
            open = false
            openConfigScreen()
        }
    }

    private fun openConfigScreen() {
        val builder = ConfigBuilder.create()
            .setParentScreen(null)
            .setTitle(Component.literal("warlordsplusplus.title"))
            .transparentBackground()
        val entryBuilder = builder.entryBuilder()

        val general = builder.getOrCreateCategory(Component.translatable("warlordsplusplus.config.general.title"))
        general.addEntry(
            entryBuilder.startBooleanToggle(Component.translatable("warlordsplusplus.config.general.enabled"), true) //TODO config value
                .setDefaultValue(true)
                .setTooltip(Component.translatable("warlordsplusplus.config.general.enabled.tooltip"))
                .build()
        )

        val scoreboard = builder.getOrCreateCategory(Component.translatable("warlordsplusplus.config.scoreboard.title"))
        scoreboard.addEntry(
            entryBuilder.startBooleanToggle(Component.translatable("warlordsplusplus.config.scoreboard.enabled"), true) //TODO config value
                .setDefaultValue(true)
                .setTooltip(Component.translatable("warlordsplusplus.config.scoreboard.enabled.tooltip"))
                .build()
        )
        scoreboard.addEntry(
            entryBuilder.startIntSlider(
                Component.translatable("warlordsplusplus.config.scoreboard.scaleCTFTDM"),
                0,
                100,
                100
            ) //TODO config value
                .setDefaultValue(100)
                .setTooltip(Component.translatable("warlordsplusplus.config.scoreboard.scaleCTFTDM.tooltip"))
                .build()
        )
        scoreboard.addEntry(
            entryBuilder.startIntSlider(
                Component.translatable("warlordsplusplus.config.scoreboard.scaleDOM"),
                0,
                100,
                100
            ) //TODO config value
                .setDefaultValue(100)
                .setTooltip(Component.translatable("warlordsplusplus.config.scoreboard.scaleDOM.tooltip"))
                .build()
        )
        scoreboard.addEntry(
            entryBuilder.startBooleanToggle(
                Component.translatable("warlordsplusplus.config.scoreboard.showTopHeader.enabled"),
                true
            ) //TODO config value
                .setDefaultValue(true)
                .setTooltip(Component.translatable("warlordsplusplus.config.scoreboard.showTopHeader.enabled.tooltip"))
                .build()
        )
        scoreboard.addEntry(
            entryBuilder.startBooleanToggle(
                Component.translatable("warlordsplusplus.config.scoreboard.showOutline.enabled"),
                true
            ) //TODO config value
                .setDefaultValue(true)
                .setTooltip(Component.translatable("warlordsplusplus.config.scoreboard.showOutline.enabled.tooltip"))
                .build()
        )
        scoreboard.addEntry(
            entryBuilder.startBooleanToggle(
                Component.translatable("warlordsplusplus.config.scoreboard.showDiedToYouStoleKill.enabled"),
                true
            ) //TODO config value
                .setDefaultValue(true)
                .setTooltip(Component.translatable("warlordsplusplus.config.scoreboard.showDiedToYouStoleKill.enabled.tooltip"))
                .build()
        )
        scoreboard.addEntry(
            entryBuilder.startBooleanToggle(
                Component.translatable("warlordsplusplus.config.scoreboard.showDoneAndReceived.enabled"),
                true
            ) //TODO config value
                .setDefaultValue(true)
                .setTooltip(Component.translatable("warlordsplusplus.config.scoreboard.showDoneAndReceived.enabled.tooltip"))
                .build()
        )
        scoreboard.addEntry(
            entryBuilder.startBooleanToggle(
                Component.translatable("warlordsplusplus.config.scoreboard.splitScoreBoard.enabled"),
                true
            ) //TODO config value
                .setDefaultValue(true)
                .setTooltip(Component.translatable("warlordsplusplus.config.scoreboard.splitScoreBoard.enabled.tooltip"))
                .build()
        )

        val renderer = builder.getOrCreateCategory(Component.translatable("warlordsplusplus.config.renderer.title"))
        renderer.addEntry(
            entryBuilder.startBooleanToggle(
                Component.translatable("warlordsplusplus.config.renderer.renderPlayerInfo"),
                true
            ) //TODO config value
                .setDefaultValue(true)
                .setTooltip(Component.translatable("warlordsplusplus.config.renderer.renderPlayerInfo.tooltip"))
                .build()
        )
        val chat = builder.getOrCreateCategory(Component.translatable("warlordsplusplus.config.chat.title"))
        chat.addEntry(
            entryBuilder.startBooleanToggle(
                Component.translatable("warlordsplusplus.config.chat.printAbilityStatsAfterGame"),
                true
            ) //TODO config value
                .setDefaultValue(true)
                .setTooltip(Component.translatable("warlordsplusplus.config.chat.printAbilityStatsAfterGame.tooltip"))
                .build()
        )
        chat.addEntry(
            entryBuilder.startBooleanToggle(
                Component.translatable("warlordsplusplus.config.chat.printGeneralStatsAfterGame"),
                true
            ) //TODO config value
                .setDefaultValue(true)
                .setTooltip(Component.translatable("warlordsplusplus.config.chat.printGeneralStatsAfterGame.tooltip"))
                .build()
        )
        chat.addEntry(
            entryBuilder.startBooleanToggle(
                Component.translatable("warlordsplusplus.config.chat.printScoreboardStatsAfterGame"),
                true
            ) //TODO config value
                .setDefaultValue(true)
                .setTooltip(Component.translatable("warlordsplusplus.config.chat.printScoreboardStatsAfterGame.tooltip"))
                .build()
        )

        val screen = builder.build()
        Minecraft.getInstance().setScreen(screen)
    }

}