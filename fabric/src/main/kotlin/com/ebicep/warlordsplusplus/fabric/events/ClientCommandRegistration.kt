package com.ebicep.warlordsplusplus.fabric.events

import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import me.shedaniel.clothconfig2.api.ConfigBuilder
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.client.Minecraft
import net.minecraft.commands.CommandBuildContext
import net.minecraft.network.chat.Component

object ClientCommandRegistration {

    fun registerCommands() {
        ClientCommandRegistrationCallback.EVENT.register(ClientCommandRegistrationCallback { dispatcher: CommandDispatcher<FabricClientCommandSource?>, _: CommandBuildContext? ->
            dispatcher.register(
                ClientCommandManager.literal("wpp")
                    .executes { _: CommandContext<FabricClientCommandSource?>? ->
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
                        Minecraft.getInstance().execute {
                            Minecraft.getInstance().setScreen(screen)
                        }
                        Command.SINGLE_SUCCESS
                    }
            )
        })
    }

}