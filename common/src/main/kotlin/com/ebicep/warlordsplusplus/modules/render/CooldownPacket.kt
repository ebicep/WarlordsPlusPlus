package com.ebicep.warlordsplusplus.modules.render

import com.ebicep.warlordsplusplus.game.GameStateManager
import com.ebicep.warlordsplusplus.game.OtherWarlordsPlayers
import dev.architectury.networking.NetworkManager
import net.minecraft.network.FriendlyByteBuf
import java.time.Instant
import java.util.function.Supplier

class CooldownPacket(buffer: FriendlyByteBuf?) {

    var playerName: String? = null
    var currentEnergy = 0
    var maxEnergy = 0
    var redCooldown = 0
    var purpleCooldown = 0
    var blueCooldown = 0
    var orangeCooldown = 0


    init {
        if (buffer != null) {
            playerName = buffer.readUtf()
            currentEnergy = buffer.readInt()
            maxEnergy = buffer.readInt()
            redCooldown = buffer.readInt()
            purpleCooldown = buffer.readInt()
            blueCooldown = buffer.readInt()
            orangeCooldown = buffer.readInt()
        }
    }

    fun encoder(buffer: FriendlyByteBuf?) {
        // Write to buffer
    }

    fun apply(contextSupplier: Supplier<NetworkManager.PacketContext?>?) {
        if (this.playerName == null) {
            return
        }
        if (GameStateManager.inWarlords2) {
            val otherWarlordsPlayer = OtherWarlordsPlayers.getPlayerByName(this.playerName!!) ?: return
            otherWarlordsPlayer.lastUpdated = Instant.now()
            otherWarlordsPlayer.currentEnergy = this.currentEnergy
            otherWarlordsPlayer.maxEnergy = this.maxEnergy
            otherWarlordsPlayer.redCooldown = this.redCooldown
            otherWarlordsPlayer.purpleCooldown = this.purpleCooldown
            otherWarlordsPlayer.blueCooldown = this.blueCooldown
            otherWarlordsPlayer.orangeCooldown = this.orangeCooldown
        }
    }

}