package com.ebicep.warlordsplusplus.forge.events

import com.ebicep.warlordsplusplus.config.ConfigScreen
import com.mojang.brigadier.Command
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraftforge.client.event.RegisterClientCommandsEvent


object ClientCommandRegistration {

    fun registerCommands(event: RegisterClientCommandsEvent) {
        event.dispatcher.register(
            Commands.literal("wpp")
                .then(
                    LiteralArgumentBuilder.literal<CommandSourceStack?>("test")
                        .executes {
                            // Minecraft.getInstance().setScreen(screen)
                            Command.SINGLE_SUCCESS
                        }
                )
                .executes { context: CommandContext<CommandSourceStack?>? ->
                    ConfigScreen.open = true
                    Command.SINGLE_SUCCESS
                }
        )
    }

}