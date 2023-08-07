package com.ebicep.warlordsplusplus.channel

import com.ebicep.warlordsplusplus.WarlordsPlusPlus
import dev.architectury.networking.NetworkChannel
import net.minecraft.resources.ResourceLocation
import org.apache.logging.log4j.Level


object WarlordsPvEPacketHandler {

    private val INSTANCE: NetworkChannel = NetworkChannel.create(ResourceLocation("warlords", "warlords"))

    init {
        WarlordsPlusPlus.LOGGER.log(Level.INFO, "Starting packet handler...")
        INSTANCE.register(
            CooldownPacket::class.java,
            CooldownPacket::encoder,
            CooldownPacket::decoder,
            CooldownPacket::messageConsumer
        )
        CooldownRenderer
    }

}