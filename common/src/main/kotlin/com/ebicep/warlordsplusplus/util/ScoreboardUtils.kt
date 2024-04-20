package com.ebicep.warlordsplusplus.util

import com.ebicep.warlordsplusplus.game.GameStateManager
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
    fun containsAt(text: String, at: Int): Boolean {
        getAt(at)?.let {
            return it.contains(text)
        }
        return false
    }

    fun getAt(at: Int): String? {
        val relativeLine = at - 1
        if (relativeLine < 0) {
            return null
        }
        if (relativeLine >= GameStateManager.sortedTeams.size) {
            return null
        }
        return getUnformattedText(GameStateManager.sortedTeams[relativeLine])
    }

    fun getContaining(containing: String): String? {
        return GameStateManager.sortedTeams.firstOrNull { getUnformattedText(it).contains(containing) }?.let { getUnformattedText(it) }
    }

    fun containsAtAnywhere(text: String): Boolean {
        return GameStateManager.sortedTeams.any { getUnformattedText(it).contains(text) }
    }

}