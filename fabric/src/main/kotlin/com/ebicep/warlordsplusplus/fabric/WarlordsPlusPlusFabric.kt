package com.ebicep.warlordsplusplus.fabric

import com.ebicep.warlordsplusplus.WarlordsPlusPlus
import com.ebicep.warlordsplusplus.fabric.config.ConfigScreen
import com.ebicep.warlordsplusplus.fabric.events.ClientCommandRegistration
import dev.architectury.event.events.client.ClientTickEvent
import net.fabricmc.api.ModInitializer


object WarlordsPlusPlusFabric : ModInitializer {

    override fun onInitialize() {
        WarlordsPlusPlus.init()
        ClientCommandRegistration.registerCommands()

        ClientTickEvent.CLIENT_POST.register {
            ConfigScreen.handleOpenScreen()
        }
    }

}
