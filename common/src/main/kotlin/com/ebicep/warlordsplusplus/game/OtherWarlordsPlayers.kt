package com.ebicep.warlordsplusplus.game


import com.ebicep.chatplus.events.EventBus
import com.ebicep.warlordsplusplus.events.WarlordsGameEvents
import com.ebicep.warlordsplusplus.events.WarlordsPlayerEvents
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

    var scoreboardName: String? = null

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
    val hasFlag: Boolean
        get() = scoreboardName?.contains("⚑") ?: false

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
        // updating player names
        playersInfo.filter {
            playersMap.contains(it.profile.name) && playersMap[it.profile.name] != null
        }.filter { playerInfo ->
            val playerTeam: PlayerTeam? = playerInfo.team
            if (playerTeam == null) {
                false
            } else {
                WarlordClass.values().any {
                    playerTeam.playerPrefix.string.contains(it.shortName)
                }
            }
        }.forEach { playerInfo ->
            val playerTeam: PlayerTeam = playerInfo.team!!
            val otherWarlordsPlayer = playersMap[playerInfo.profile.name] ?: return@forEach
            otherWarlordsPlayer.scoreboardName = playerTeam.playerPrefix.string + otherWarlordsPlayer.name + playerTeam.playerSuffix.string
        }

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
            otherWarlordsPlayer.scoreboardName = playerInfo.tabListDisplayName?.string
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

        // spec detection
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

        return playersMap.values
    }

    fun testPlayers(): Collection<OtherWarlordsPlayer> {
        return listOf(
            OtherWarlordsPlayer("Heatran", UUID.randomUUID()).apply {
                kills = 1
                deaths = 2
                damageDone = 300
                damageReceived = 40
                healingDone = 0
                healingReceived = 0
                warlordClass = WarlordClass.MAGE
                spec = Specialization.CRYOMANCER
                team = Team.BLUE
                level = 7
                left = false
            },
            OtherWarlordsPlayer("John_Br", UUID.randomUUID()).apply {
                kills = 10
                deaths = 0
                damageDone = 0
                damageReceived = 0
                healingDone = 100
                healingReceived = 1000
                warlordClass = WarlordClass.WARRIOR
                spec = Specialization.NONE
                team = Team.RED
                level = 90
                left = false
            },
            OtherWarlordsPlayer("_RealDeal_", UUID.randomUUID()).apply {
                kills = 0
                deaths = 204
                damageDone = 0
                damageReceived = 0
                healingDone = 3234
                healingReceived = 0
                warlordClass = WarlordClass.PALADIN
                spec = Specialization.AVENGER
                team = Team.RED
                level = 56
                left = false
            },
            OtherWarlordsPlayer("JohnSmith", UUID.randomUUID()).apply {
                kills = 100
                deaths = 25
                damageDone = 30
                damageReceived = 406
                healingDone = 0
                healingReceived = 0
                warlordClass = WarlordClass.MAGE
                spec = Specialization.PYROMANCER
                team = Team.BLUE
                level = 70
                left = false
            },
        )
    }

    fun getPlayerByName(name: String): OtherWarlordsPlayer? {
        return playersMap.values.firstOrNull { it.name == name }
    }

    init {
        EventBus.register<WarlordsGameEvents.ResetEvent> {
            playersMap.clear()
            getOtherWarlordsPlayers()
        }
        EventBus.register<WarlordsPlayerEvents.KillEvent> {
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
        EventBus.register<WarlordsPlayerEvents.DamageDoneEvent> {
            playersMap[it.player]?.damageDone = it.amount
        }
        EventBus.register<WarlordsPlayerEvents.DamageTakenEvent> {
            playersMap[it.player]?.damageReceived = it.amount
        }
        EventBus.register<WarlordsPlayerEvents.HealingGivenEvent> {
            playersMap[it.player]?.healingDone = it.amount
        }
        EventBus.register<WarlordsPlayerEvents.HealingReceivedEvent> {
            playersMap[it.player]?.healingReceived = it.amount
        }
    }

}