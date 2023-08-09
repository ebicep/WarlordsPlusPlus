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
            .setTitle(Component.literal("test.title"))
            .transparentBackground()
        val entryBuilder = builder.entryBuilder()
        val general = builder.getOrCreateCategory(Component.translatable("general"))
        general.addEntry(
            entryBuilder.startStrField(Component.translatable("test"), "test")
                .setDefaultValue("This is the default value") // Recommended: Used when user click "Reset"
                .setTooltip(Component.translatable("This option is awesome!")) // Optional: Shown when the user hover over this option
                .build()
        ) // Builds the option entry for cloth config
        val screen = builder.build()
        Minecraft.getInstance().setScreen(screen)
    }

}