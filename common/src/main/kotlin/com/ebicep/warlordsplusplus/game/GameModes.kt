package com.ebicep.warlordsplusplus.game

import com.ebicep.warlordsplusplus.detectors.RespawnTimerDetector
import com.ebicep.warlordsplusplus.util.ScoreboardUtils
import net.minecraft.world.scores.PlayerTeam

enum class GameModes {
    CTF {
        override fun isCurrent(sidebar: List<PlayerTeam>): Boolean {
            return ScoreboardUtils.containsAt(sidebar, "RED Flag", 8)
        }

        override fun getTime(sidebar: List<PlayerTeam>): Pair<Int, Int>? {
            return getTimePvP(sidebar, 10)
        }

        override fun getCurrentRespawn(): Int {
            return RespawnTimerDetector.respawnTimer + (if (RespawnTimerDetector.respawnTimer <= 4) 12 else 0)
        }

        override fun getScale(): Double? {
            return 1.0//ConfigScoreboardGui.scaleCTFTDM.get()
        }
    },
    TDM {
        override fun isCurrent(sidebar: List<PlayerTeam>): Boolean {
            return ScoreboardUtils.containsAt(sidebar, "BLU", 10)
        }

        override fun getTime(sidebar: List<PlayerTeam>): Pair<Int, Int>? {
            return getTimePvP(sidebar, 7)
        }

        override fun getCurrentRespawn(): Int {
            return 6
        }

        override fun getScale(): Double? {
            return 1.0//ConfigScoreboardGui.scaleCTFTDM.get()
        }
    },
    DOM {
        override fun isCurrent(sidebar: List<PlayerTeam>): Boolean {
            return ScoreboardUtils.containsAt(sidebar, "/2000", 12)
        }

        override fun getTime(sidebar: List<PlayerTeam>): Pair<Int, Int>? {
            return getTimePvP(sidebar, 10)
        }

        override fun getCurrentRespawn(): Int {
            return RespawnTimerDetector.respawnTimer + (if (RespawnTimerDetector.respawnTimer < 8) 8 else 0)
        }

        override fun getScale(): Double? {
            return 1.0//detectorsConfigScoreboardGui.scaleDOM.get()
        }
    },

    //INTERCEPTION,
    WAVE_DEFENSE {
        override fun isCurrent(sidebar: List<PlayerTeam>): Boolean {
            return ScoreboardUtils.containsAtAnywhere(sidebar, "Wave")
        }

        override fun getTime(sidebar: List<PlayerTeam>): Pair<Int, Int>? {
            return getTimePvE(sidebar, 4, 6)
        }

    },
    ONSLAUGHT {
        override fun isCurrent(sidebar: List<PlayerTeam>): Boolean {
            return ScoreboardUtils.containsAtAnywhere(sidebar, "Soul Energy")
        }

        override fun getTime(sidebar: List<PlayerTeam>): Pair<Int, Int>? {
            return getTimePvE(sidebar, 4, 6)
        }
    },
    NONE {
        override fun isCurrent(sidebar: List<PlayerTeam>): Boolean {
            return true // true in case no other gamemode is found
        }

        override fun getTime(sidebar: List<PlayerTeam>): Pair<Int, Int>? {
            return null
        }
    },

    ;

    abstract fun isCurrent(sidebar: List<PlayerTeam>): Boolean

    abstract fun getTime(sidebar: List<PlayerTeam>): Pair<Int, Int>?

    open fun getCurrentRespawn(): Int {
        return -1
    }

    open fun getScale(): Double? {
        return null
    }

    fun isPvP(): Boolean {
        return when (this) {
            CTF, TDM, DOM -> true
            else -> false
        }
    }

    fun getTimePvP(sidebar: List<PlayerTeam>, index: Int): Pair<Int, Int>? {
        return ScoreboardUtils.getAt(sidebar, index)?.let {
            val timeString: String =
                if (it.contains("Wins")) it.substring(13)
                else it.substring(11)
            val colonPosition = timeString.indexOf(":")
            return Pair(
                timeString.substring(0, colonPosition).toInt(), //.coerceAtMost(14),
                timeString.substring(colonPosition + 1).toInt()
            )
        }
    }

    fun getTimePvE(sidebar: List<PlayerTeam>, index: Int, timeStringIndex: Int): Pair<Int, Int>? {
        return ScoreboardUtils.getAt(sidebar, index)?.let {
            val timeString: String = it.substring(timeStringIndex)
            val colonPosition = timeString.indexOf(":")
            return Pair(
                timeString.substring(0, colonPosition).toInt(), //.coerceAtMost(14),
                timeString.substring(colonPosition + 1).toInt()
            )
        }
    }
}