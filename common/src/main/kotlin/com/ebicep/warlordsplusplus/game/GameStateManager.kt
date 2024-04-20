package com.ebicep.warlordsplusplus.game

import com.ebicep.chatplus.events.EventBus
import com.ebicep.warlordsplusplus.WarlordsPlusPlus
import com.ebicep.warlordsplusplus.events.WarlordsGameEvents
import dev.architectury.event.CompoundEventResult
import dev.architectury.event.events.client.ClientSystemMessageEvent
import dev.architectury.event.events.client.ClientTickEvent
import net.minecraft.client.Minecraft
import net.minecraft.network.chat.Component
import net.minecraft.world.scores.DisplaySlot
import net.minecraft.world.scores.PlayerTeam

object GameStateManager {

    private const val SCOREBOARD_TEAM_CHECK = "team_" // for checking valid sidebar teams

    var inWarlords = false
    var inWarlords2 = false
    val inPvP: Boolean
        get() = currentGameMode != GameModes.NONE && currentGameMode.isPvP()
    val inPvE: Boolean
        get() = currentGameMode != GameModes.NONE && !inPvP
    var currentGameMode: GameModes = GameModes.NONE
    val inGame: Boolean
        get() = currentGameMode != GameModes.NONE
    val notInGame: Boolean
        get() = !inGame
    private var time: Pair<Int, Int>? = null
    val minute: Int
        get() = time?.first ?: 0
    val second: Int
        get() = time?.second ?: 0

    var sortedTeams: List<PlayerTeam> = emptyList()

    init {
        EventBus.register<WarlordsGameEvents.ResetEvent> {

        }
        ClientSystemMessageEvent.RECEIVED.register { component: Component ->
            val unformattedText = component.string
            if (
                unformattedText == "The gates will fall in 1 second!" ||
                (inWarlords2 && unformattedText == "The game starts in 1 second!")
            ) {
                EventBus.post(WarlordsGameEvents.ResetEvent())
                WarlordsPlusPlus.LOGGER.debug("Posted ResetEvent")
            }
            CompoundEventResult.pass()
        }
        ClientTickEvent.CLIENT_POST.register {
            if (Minecraft.getInstance().isPaused || Minecraft.getInstance().player == null) {
                return@register
            }
            val scoreboard = Minecraft.getInstance().player?.scoreboard ?: return@register
            val sidebarObjective = scoreboard.getDisplayObjective(DisplaySlot.SIDEBAR) ?: return@register
            val unformattedDisplayName = sidebarObjective.displayName.string
            inWarlords = unformattedDisplayName.contains("WARLORDS", ignoreCase = true)
            inWarlords2 = unformattedDisplayName.contains("WARLORDS 2.0", ignoreCase = true)
            if (!inWarlords) {
                currentGameMode = GameModes.NONE
                return@register
            }
            sortedTeams = scoreboard.playerTeams
                .filter { it.name.contains(SCOREBOARD_TEAM_CHECK) }
                .toList()
                .sortedBy {
                    val name = it.name
                    name.substring(name.indexOf("_") + 1).toInt()
                }
            currentGameMode = GameModes.values().first { it.isCurrent() }
            try {
                val oldTime = time
                time = currentGameMode.getTime()
                if (time == null) {
                    return@register
                }
                if (oldTime?.first != time?.first) {
                    EventBus.post(WarlordsGameEvents.MinuteEvent(time!!.first))
                }
                if (oldTime?.second != time?.second) {
                    EventBus.post(WarlordsGameEvents.SecondEvent(time!!.second))
                }
            } catch (e: Exception) {
                WarlordsPlusPlus.LOGGER.error("Error getting time", e)
            }
        }
    }

}