package com.ebicep.warlordsplusplus

import dev.architectury.registry.CreativeTabRegistry
import dev.architectury.registry.registries.DeferredRegister
import dev.architectury.registry.registries.RegistrySupplier
import com.ebicep.warlordsplusplus.ExpectPlatform.getConfigDirectory
import com.ebicep.warlordsplusplus.channel.CooldownRenderer
import com.ebicep.warlordsplusplus.channel.WarlordsPvEPacketHandler
import com.ebicep.warlordsplusplus.detectors.DetectorEventHandler
import com.ebicep.warlordsplusplus.event.PlayerRenderEvent
import dev.architectury.event.CompoundEventResult
import dev.architectury.event.EventFactory
import dev.architectury.event.events.client.ClientChatEvent
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.ChatType
import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

const val MOD_ID = "warlordsplusplus"

object WarlordsPlusPlus {

    val LOGGER: Logger = LogManager.getLogger(MOD_ID)

    fun init() {
        LOGGER.info("CONFIG DIR: ${getConfigDirectory().toAbsolutePath().normalize()}")
        WarlordsPvEPacketHandler
        DetectorEventHandler

        ClientChatEvent.RECEIVED.register { bound: ChatType.Bound, component: Component ->
            LOGGER.info("TEST")
            CompoundEventResult.pass()
        }
    }

    fun isEnabled(): Boolean {
        return true
    }
}