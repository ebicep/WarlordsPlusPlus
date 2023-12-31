package com.ebicep.warlordsplusplus.events.fabric

import com.ebicep.warlordsplusplus.config.ConfigScreen
import com.ebicep.warlordsplusplus.game.OtherWarlordsPlayers
import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.commands.CommandBuildContext

object ClientCommandRegistration {

    fun registerCommands() {
        ClientCommandRegistrationCallback.EVENT.register(ClientCommandRegistrationCallback { dispatcher: CommandDispatcher<FabricClientCommandSource?>, _: CommandBuildContext? ->
            val command = dispatcher.register(
                ClientCommandManager.literal("warlordsplusplus")
                    .then(ClientCommandManager.literal("reset")
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
            dispatcher.register(ClientCommandManager.literal("wpp").redirect(command))
            dispatcher.register(ClientCommandManager.literal("warlordsplus").redirect(command))
        })
    }

}