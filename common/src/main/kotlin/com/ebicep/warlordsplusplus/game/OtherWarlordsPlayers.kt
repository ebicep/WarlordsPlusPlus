package com.ebicep.warlordsplusplus.game

import com.ebicep.warlordsplusplus.event.WarlordsGameEventsImpl
import com.ebicep.warlordsplusplus.event.WarlordsPlayerEvents
import com.ebicep.warlordsplusplus.event.WarlordsPlayerEventsImpl
import com.ebicep.warlordsplusplus.util.Specialization
import com.ebicep.warlordsplusplus.util.Team
import com.ebicep.warlordsplusplus.util.WarlordClass
import net.minecraft.ChatFormatting
import net.minecraft.client.Minecraft
import net.minecraft.client.multiplayer.PlayerInfo
import net.minecraft.client.player.RemotePlayer
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.ListTag
import net.minecraft.nbt.Tag
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.network.chat.TextColor
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.scores.PlayerTeam
import java.time.Instant
import java.util.*
import java.util.regex.Pattern


private val numberPattern = Pattern.compile("[0-9]{2}")

open class OtherWarlordsPlayer(val name: String, val uuid: UUID) {

    var kills: Int = 0
    var deaths: Int = 0
    var damageDone: Int = 0
    var damageReceived: Int = 0
    var healingDone: Int = 0
    var healingReceived: Int = 0
    var warlordClass = WarlordClass.NONE
    var spec = Specialization.NONE
    var team = Team.NONE
    var level = 0
    var levelColor: TextColor? = TextColor.fromLegacyFormat(ChatFormatting.GOLD)
    var prestiged: Boolean = false
    var left: Boolean = false

    //TODO
    var died: Int = 0
    var stoleKill: Int = 0
    var picks: Int = 0
    var returns: Int = 0
    var caps: Int = 0
    var isDead: Boolean = false
    var respawn: Int = -1
    var hasFlag: Boolean = false

    var lastUpdated: Instant = Instant.now()
    var currentEnergy: Int = 0
    var maxEnergy: Int = 0
    var redCooldown: Int = 0
    var purpleCooldown: Int = 0
    var blueCooldown: Int = 0
    var orangeCooldown: Int = 0

    // [MAG][80][+]
    fun getInfoPrefix(): MutableComponent {
        return Component.literal("")
            .withStyle {
                it.withColor(ChatFormatting.DARK_GRAY)
            }
            .append(Component.literal("["))
            .append(Component.literal(warlordClass.shortName)
                .withStyle {
                    it.withColor(ChatFormatting.WHITE)
                })
            .append(Component.literal("]["))
            .append(Component.literal(level.toString().padStart(2, '0'))
                .withStyle {
                    it.withColor(if (prestiged) ChatFormatting.GOLD else ChatFormatting.WHITE)
                })
            .append(Component.literal("]["))
            .append(spec.iconComponent)
            .append(Component.literal("]"))
    }
}

object OtherWarlordsPlayers {

    val playersMap: MutableMap<String, OtherWarlordsPlayer> = mutableMapOf()
    //val tempPlayersMap: MutableMap<String, WarlordsPlayer> = mutableMapOf()

    fun getOtherWarlordsPlayers(): Collection<OtherWarlordsPlayer> {
        return getOtherWarlordsPlayers(Minecraft.getInstance().player!!.connection.onlinePlayers)
    }

    fun getOtherWarlordsPlayers(playersInfo: MutableCollection<PlayerInfo>): Collection<OtherWarlordsPlayer> {
        playersInfo.filter {
            !playersMap.contains(it.profile.name)
        }.filter { playerInfo ->
            val playerTeam: PlayerTeam? = playerInfo.team
            if (playerTeam == null) {
                false
            } else {
                WarlordClass.values().any {
                    playerTeam.playerPrefix.string.contains(it.shortName)
                }
            }
        }.map { playerInfo ->
            val playerTeam: PlayerTeam = playerInfo.team!!
            val otherWarlordsPlayer = OtherWarlordsPlayer(playerInfo.profile.name, playerInfo.profile.id)
            otherWarlordsPlayer.warlordClass = WarlordClass.values().first {
                playerTeam.playerPrefix.string.contains(it.shortName)
            }
            playerInfo.team?.playerSuffix?.let { suffix ->
                val m = numberPattern.matcher(suffix.string)
                otherWarlordsPlayer.level =
                    if (!m.find()) {
                        0
                    } else {
                        m.group().toInt()
                    }
                //TODO
//                otherWarlordsPlayer.levelColor =
//                    if (!m.find()) {
//                    null
//                } else {
//                    m.group().
//                }
                otherWarlordsPlayer.prestiged = suffix.siblings.any {
                    it.style.color == TextColor.fromLegacyFormat(ChatFormatting.GOLD)
                }
            }

            otherWarlordsPlayer.team = Team.values().first {
                playerTeam.color == it.color
            }

            return@map otherWarlordsPlayer
        }.forEach {
            playersMap[it.name] = it
        }

        val players = Minecraft.getInstance().level!!.players()
        playersMap.filter {
            it.value.spec == Specialization.NONE || GameStateManager.inWarlords2
        }.forEach { player ->
            players.filter {
                it is RemotePlayer && it.gameProfile.name == player.key
            }.map { p ->
                val selectedItem = p.inventory.getSelected()
                if (selectedItem == ItemStack.EMPTY || selectedItem.count != 1) {
                    return@map
                }
                val tag: CompoundTag = selectedItem.tag ?: return@map
                val display: CompoundTag = tag.getCompound("display") ?: return@map
                val displayString = display.toString()
                when {
                    displayString.contains("RIGHT-CLICK") -> {
                        val lore: ListTag = display.getList("Lore", Tag.TAG_STRING.toInt()) ?: return@map
                        val skillBoostStart = lore.getString(4)
                        if (skillBoostStart.contains("green")) {
                            player.value.spec = Specialization.values().firstOrNull {
                                skillBoostStart.contains(it.classname)
                            } ?: Specialization.NONE
                        } else {
                            val afterRightClick = displayString.substring(displayString.indexOf("RIGHT-CLICK"))
                            player.value.spec = Specialization.values().firstOrNull {
                                afterRightClick.contains(it.weapon)
                            } ?: Specialization.NONE
                        }
                    }

                    displayString.contains("LEFT-CLICK") -> {
                        val name = display.getString("Name") ?: return@map
                        player.value.spec = Specialization.values().firstOrNull {
                            name.contains(it.weapon)
                        } ?: Specialization.NONE
                    }

                    displayString.contains("Cooldown") && !display.contains("Mount") -> {
                        val name = display.getString("Name") ?: return@map
                        val ability: ((Specialization) -> String) = when (selectedItem.item) {
                            Items.RED_DYE -> { it: Specialization -> it.red }
                            Items.GLOWSTONE_DUST -> { it: Specialization -> it.purple }
                            Items.LIME_DYE -> { it: Specialization -> it.blue }
                            Items.ORANGE_DYE -> { it: Specialization -> it.orange }
                            else -> return@map
                        }
                        player.value.spec = Specialization.values().firstOrNull {
                            name.contains(ability(it))
                        } ?: Specialization.NONE
                    }
                }

            }
        }

        //TODO reupdate player

        return playersMap.values
    }

    fun getPlayerByName(name: String): OtherWarlordsPlayer? {
        return playersMap.values.firstOrNull { it.name == name }
    }

    init {
        WarlordsGameEventsImpl.RESET_EVENT.register {
            playersMap.clear()
            getOtherWarlordsPlayers()
        }
        WarlordsPlayerEventsImpl.KILL_EVENT.register {
            if (it.deathPlayer in playersMap) {
                val otherWarlordsPlayer = playersMap[it.deathPlayer]!!
                otherWarlordsPlayer.deaths++
                otherWarlordsPlayer.isDead = true
                otherWarlordsPlayer.respawn = it.respawn
            }
            if (it.player in playersMap) {
                playersMap[it.player]!!.kills++
            }
        }
        WarlordsPlayerEventsImpl.ABSTRACT_DAMAGE_HEAL_ENERGY_EVENT.register {
            val player = it.player
            val otherWarlordsPlayer = if (player in playersMap) playersMap[player]!! else return@register
            when (it::class) {
                WarlordsPlayerEvents.DamageDoneEvent::class -> otherWarlordsPlayer.damageDone += it.amount

                WarlordsPlayerEvents.DamageTakenEvent::class -> otherWarlordsPlayer.damageReceived += it.amount

                WarlordsPlayerEvents.HealingGivenEvent::class -> otherWarlordsPlayer.healingDone += it.amount

                WarlordsPlayerEvents.HealingReceivedEvent::class -> otherWarlordsPlayer.healingReceived += it.amount
            }
        }
    }

}