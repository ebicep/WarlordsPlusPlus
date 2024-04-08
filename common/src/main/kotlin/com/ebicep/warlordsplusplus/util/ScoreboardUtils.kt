package com.ebicep.warlordsplusplus.util

import net.minecraft.client.Minecraft
import net.minecraft.world.scores.PlayerTeam

object ScoreboardUtils {

    fun getPlayerTeamFromName(name: String): PlayerTeam? {
        return Minecraft.getInstance().player?.scoreboard?.playerTeams?.firstOrNull { it.name.equals(name) }
    }

    private fun getUnformattedText(playerTeam: PlayerTeam): String {
        return playerTeam.playerPrefix.string + playerTeam.playerSuffix.string
    }

    /**
     * @param at represents sidebar number starting from bottom = 1 (basically read off sidebar)
     */
    fun containsAt(teams: List<PlayerTeam>, text: String, at: Int): Boolean {
        getAt(teams, at)?.let {
            return it.contains(text)
        }
        return false
    }

    fun getAt(teams: List<PlayerTeam>, at: Int): String? {
        val relativeLine = at - 1
        if (relativeLine < 0) {
            return null
        }
        if (relativeLine >= teams.size) {
            return null
        }
        return getUnformattedText(teams[relativeLine])
    }

    fun getContaining(teams: List<PlayerTeam>, containing: String): String? {
        return teams.firstOrNull { getUnformattedText(it).contains(containing) }?.let { getUnformattedText(it) }
    }

    fun containsAtAnywhere(teams: List<PlayerTeam>, text: String): Boolean {
        return teams.any { getUnformattedText(it).contains(text) }
    }

}