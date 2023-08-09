package com.ebicep.warlordsplusplus.forge

import com.ebicep.warlordsplusplus.MOD_ID
import com.ebicep.warlordsplusplus.WarlordsPlusPlus
import com.ebicep.warlordsplusplus.forge.config.Config
import com.ebicep.warlordsplusplus.forge.config.Example
import com.ebicep.warlordsplusplus.forge.events.ClientCommandRegistration
import dev.architectury.platform.forge.EventBuses
import me.shedaniel.autoconfig.AutoConfig
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import thedarkcolour.kotlinforforge.forge.MOD_BUS


@Mod(MOD_ID)
object WarlordsPlusPlusForge {

    init {
        EventBuses.registerModEventBus(MOD_ID, MOD_BUS)
        WarlordsPlusPlus.init()

        AutoConfig.register(Config::class.java, ::JanksonConfigSerializer)
        AutoConfig.register(Example::class.java, ::JanksonConfigSerializer)

        MinecraftForge.EVENT_BUS.addListener(ClientCommandRegistration::registerCommands)
    }

}