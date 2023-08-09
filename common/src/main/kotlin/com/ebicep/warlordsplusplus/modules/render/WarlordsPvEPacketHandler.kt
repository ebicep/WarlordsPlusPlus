package com.ebicep.warlordsplusplus.modules.render

import com.ebicep.warlordsplusplus.WarlordsPlusPlus
import com.ebicep.warlordsplusplus.modules.Module
import dev.architectury.networking.NetworkChannel
import net.minecraft.resources.ResourceLocation
import org.apache.logging.log4j.Level


object WarlordsPvEPacketHandler : Module {

    private val INSTANCE: NetworkChannel = NetworkChannel.create(ResourceLocation("warlords", "warlords"))

    init {
        WarlordsPlusPlus.LOGGER.log(Level.INFO, "Starting packet handler...")
        INSTANCE.register(
            CooldownPacket::class.java,
            CooldownPacket::encoder,
            CooldownPacket.Companion::decoder,
            CooldownPacket::messageConsumer
        )
        CooldownRenderer
    }

}