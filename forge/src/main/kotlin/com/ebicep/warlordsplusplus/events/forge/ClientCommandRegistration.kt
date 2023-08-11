package com.ebicep.warlordsplusplus.events.forge

import com.ebicep.warlordsplusplus.config.ConfigScreen
import com.ebicep.warlordsplusplus.game.OtherWarlordsPlayers
import com.mojang.brigadier.Command
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraftforge.client.event.RegisterClientCommandsEvent


object ClientCommandRegistration {

    fun registerCommands(event: RegisterClientCommandsEvent) {
        val commandNode = event.dispatcher.register(
            Commands.literal("warlordsplusplus")
                .then(LiteralArgumentBuilder.literal<CommandSourceStack?>("reset")
                    .executes {
                        OtherWarlordsPlayers.playersMap.clear()
                        Command.SINGLE_SUCCESS
                    }
                )
                .executes {
                    ConfigScreen.open = true
                    Command.SINGLE_SUCCESS
                }
        )
        event.dispatcher.register(Commands.literal("wpp").redirect(commandNode))
        event.dispatcher.register(Commands.literal("warlordsplus").redirect(commandNode))
    }

}