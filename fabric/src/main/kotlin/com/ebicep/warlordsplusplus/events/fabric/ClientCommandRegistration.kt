package com.ebicep.warlordsplusplus.events.fabric

import com.ebicep.warlordsplusplus.config.ConfigScreen
import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.commands.CommandBuildContext

object ClientCommandRegistration {

    fun registerCommands() {
        ClientCommandRegistrationCallback.EVENT.register(ClientCommandRegistrationCallback { dispatcher: CommandDispatcher<FabricClientCommandSource?>, _: CommandBuildContext? ->
            dispatcher.register(
                ClientCommandManager.literal("wpp")
                    .executes { _: CommandContext<FabricClientCommandSource?>? ->
                        ConfigScreen.open = true
                        Command.SINGLE_SUCCESS
                    }
            )
        })
    }

}