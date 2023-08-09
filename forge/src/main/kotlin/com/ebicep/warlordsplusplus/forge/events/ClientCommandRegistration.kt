package com.ebicep.warlordsplusplus.forge.events

import com.mojang.brigadier.Command
import com.mojang.brigadier.context.CommandContext
import me.shedaniel.clothconfig2.api.ConfigBuilder
import net.minecraft.client.Minecraft
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.network.chat.Component
import net.minecraftforge.client.event.RegisterClientCommandsEvent

object ClientCommandRegistration {

    fun registerCommands(event: RegisterClientCommandsEvent) {
        event.dispatcher.register(
            Commands.literal("wpp")
                .executes { context: CommandContext<CommandSourceStack?>? ->
                    val builder = ConfigBuilder.create()
                        .setParentScreen(null)
                        .setTitle(Component.literal("warlordsplusplus.title"))
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
                    Command.SINGLE_SUCCESS
                }
        )
    }

}