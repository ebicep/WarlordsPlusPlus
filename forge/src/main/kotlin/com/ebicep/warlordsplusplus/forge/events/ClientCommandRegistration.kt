package com.ebicep.warlordsplusplus.forge.events

import com.ebicep.warlordsplusplus.forge.config.Config
import com.ebicep.warlordsplusplus.forge.config.Example
import com.mojang.brigadier.Command
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import me.shedaniel.autoconfig.AutoConfig
import net.minecraft.client.Minecraft
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
                            val screen = AutoConfig.getConfigScreen(Example::class.java, Minecraft.getInstance().screen).get()
                            Minecraft.getInstance().setScreen(screen)
                            Command.SINGLE_SUCCESS
                        }
                )
                .executes { context: CommandContext<CommandSourceStack?>? ->
                    val screen = AutoConfig.getConfigScreen(Config::class.java, Minecraft.getInstance().screen).get()
                    Minecraft.getInstance().setScreen(screen)
                    Command.SINGLE_SUCCESS
                }
        )
    }

}