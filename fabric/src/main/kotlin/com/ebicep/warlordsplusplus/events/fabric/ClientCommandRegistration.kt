package com.ebicep.warlordsplusplus.events.fabric

import com.ebicep.chatplus.events.EventBus
import com.ebicep.warlordsplusplus.config.Config
import com.ebicep.warlordsplusplus.config.ConfigScreen
import com.ebicep.warlordsplusplus.events.WarlordsGameEvents
import com.ebicep.warlordsplusplus.features.hud.HudElementManager
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
                            EventBus.post(WarlordsGameEvents.ResetEvent())
                            Command.SINGLE_SUCCESS
                        }
                    )
                    .then(ClientCommandManager.literal("fakeend")
                        .executes {
                            EventBus.post(WarlordsGameEvents.GameEndEvent())
                            Command.SINGLE_SUCCESS
                        }
                    )
                    .then(ClientCommandManager.literal("edit")
                        .executes {
                            HudElementManager.openEditScreen()
                            Command.SINGLE_SUCCESS
                        }
                    )
                    .then(ClientCommandManager.literal("save")
                        .executes {
                            Config.save()
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