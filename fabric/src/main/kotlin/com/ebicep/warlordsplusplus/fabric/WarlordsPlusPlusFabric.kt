package com.ebicep.warlordsplusplus.fabric

import com.ebicep.warlordsplusplus.WarlordsPlusPlus
import com.ebicep.warlordsplusplus.fabric.events.ClientCommandRegistration
import net.fabricmc.api.ModInitializer


object WarlordsPlusPlusFabric: ModInitializer {

    override fun onInitialize() {
        WarlordsPlusPlus.init()
        ClientCommandRegistration.registerCommands()
    }

}
