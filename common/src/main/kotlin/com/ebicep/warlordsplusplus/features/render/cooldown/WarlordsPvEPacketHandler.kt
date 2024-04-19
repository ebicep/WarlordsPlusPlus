package com.ebicep.warlordsplusplus.features.render.cooldown

import com.ebicep.warlordsplusplus.WarlordsPlusPlus
import com.ebicep.warlordsplusplus.features.Feature
import dev.architectury.networking.NetworkChannel
import net.minecraft.resources.ResourceLocation
import org.apache.logging.log4j.Level


object WarlordsPvEPacketHandler : Feature {

    val INSTANCE: NetworkChannel = NetworkChannel.create(ResourceLocation("warlords", "warlords"))

    init {
        WarlordsPlusPlus.LOGGER.log(Level.INFO, "Starting packet handler...")
        INSTANCE.register(
            CooldownPacket::class.java,
            CooldownPacket::encoder,
            ::CooldownPacket,
            CooldownPacket::apply
        )
        /*
                WarlordsPlusPlus.LOGGER.log(Level.INFO, "Starting other packet handler...")
                NetworkManager.registerReceiver(
                    NetworkManager.Side.S2C,
                    ResourceLocation("warlords", "warlords"),
                    NetworkReceiver { buffer, context ->
                        buffer.readUtf()
                        val playerName = buffer.readUtf() ?: return@NetworkReceiver
                        if (GameStateManager.inWarlords2) {
                            val otherWarlordsPlayer = OtherWarlordsPlayers.getPlayerByName(playerName) ?: return@NetworkReceiver
                            otherWarlordsPlayer.lastUpdated = Instant.now()
                            otherWarlordsPlayer.currentEnergy = buffer.readInt()
                            otherWarlordsPlayer.maxEnergy = buffer.readInt()
                            otherWarlordsPlayer.redCooldown = buffer.readInt()
                            otherWarlordsPlayer.purpleCooldown = buffer.readInt()
                            otherWarlordsPlayer.blueCooldown = buffer.readInt()
                            otherWarlordsPlayer.orangeCooldown = buffer.readInt()
                        }
                    }
                )

         */
        CooldownRenderer

    }

}