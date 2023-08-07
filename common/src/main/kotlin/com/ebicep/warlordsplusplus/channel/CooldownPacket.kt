package com.ebicep.warlordsplusplus.channel

import com.ebicep.warlordsplusplus.game.GameStateManager
import com.ebicep.warlordsplusplus.game.OtherWarlordsPlayers
import dev.architectury.networking.NetworkChannel
import dev.architectury.networking.NetworkManager
import net.minecraft.network.FriendlyByteBuf
import java.util.function.Supplier

class CooldownPacket {

    var playerName: String? = null
    var currentEnergy = 0
    var maxEnergy = 0
    var redCooldown = 0
    var purpleCooldown = 0
    var blueCooldown = 0
    var orangeCooldown = 0

    fun encoder(buffer: FriendlyByteBuf?) {
        // Write to buffer
    }

    fun messageConsumer(ctx: Supplier<NetworkManager.PacketContext>) {
        if (this.playerName == null) {
            return
        }
        if (GameStateManager.inWarlords2) {
            val otherWarlordsPlayer = OtherWarlordsPlayers.getPlayerByName(this.playerName!!) ?: return
            otherWarlordsPlayer.currentEnergy = this.currentEnergy
            otherWarlordsPlayer.maxEnergy = this.maxEnergy
            otherWarlordsPlayer.redCooldown = this.redCooldown
            otherWarlordsPlayer.purpleCooldown = this.purpleCooldown
            otherWarlordsPlayer.blueCooldown = this.blueCooldown
            otherWarlordsPlayer.orangeCooldown = this.orangeCooldown
        }
    }

    companion object {

        fun decoder(buffer: FriendlyByteBuf?): CooldownPacket {
            val packet = CooldownPacket()
            if (buffer == null) {
                return packet
            }
            packet.playerName = buffer.readUtf()
            packet.currentEnergy = buffer.readInt()
            packet.maxEnergy = buffer.readInt()
            packet.redCooldown = buffer.readInt()
            packet.purpleCooldown = buffer.readInt()
            packet.blueCooldown = buffer.readInt()
            packet.orangeCooldown = buffer.readInt()
            return packet
        }

    }

}