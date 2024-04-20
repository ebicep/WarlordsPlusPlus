package com.ebicep.warlordsplusplus.game

import com.ebicep.warlordsplusplus.config.Config
import com.ebicep.warlordsplusplus.detectors.RespawnTimerDetector
import com.ebicep.warlordsplusplus.util.ScoreboardUtils

enum class GameModes {
    CTF {
        override fun isCurrent(): Boolean {
            return ScoreboardUtils.containsAt("RED Flag", 8)
        }

        override fun getTime(): Pair<Int, Int>? {
            return getTimePvP(10)
        }

        override fun getCurrentRespawn(): Int {
            return RespawnTimerDetector.respawnTimer + (if (RespawnTimerDetector.respawnTimer <= 4) 12 else 0)
        }

        override fun getScale(): Double {
            return Config.values.scoreboardScaleCTFTDM.value / 100.0
        }
    },
    TDM {
        override fun isCurrent(): Boolean {
            return ScoreboardUtils.containsAt("BLU", 10)
        }

        override fun getTime(): Pair<Int, Int>? {
            return getTimePvP(7)
        }

        override fun getCurrentRespawn(): Int {
            return 6
        }

        override fun getScale(): Double {
            return Config.values.scoreboardScaleCTFTDM.value / 100.0
        }
    },
    DOM {
        override fun isCurrent(): Boolean {
            return ScoreboardUtils.containsAt("/2000", 12)
        }

        override fun getTime(): Pair<Int, Int>? {
            return getTimePvP(10)
        }

        override fun getCurrentRespawn(): Int {
            return RespawnTimerDetector.respawnTimer + (if (RespawnTimerDetector.respawnTimer < 8) 8 else 0)
        }

        override fun getScale(): Double {
            return Config.values.scoreboardScaleDOM.value / 100.0
        }
    },

    //INTERCEPTION,
    WAVE_DEFENSE {
        override fun isCurrent(): Boolean {
            return ScoreboardUtils.containsAtAnywhere("Wave")
        }

        override fun getTime(): Pair<Int, Int>? {
            return getTimePvE(4, 6)
        }

    },
    ONSLAUGHT {
        override fun isCurrent(): Boolean {
            return ScoreboardUtils.containsAtAnywhere("Soul Energy")
        }

        override fun getTime(): Pair<Int, Int>? {
            return getTimePvE(4, 6)
        }
    },
    NONE {
        override fun isCurrent(): Boolean {
            return true // true in case no other gamemode is found
        }

        override fun getTime(): Pair<Int, Int>? {
            return null
        }
    },

    ;

    abstract fun isCurrent(): Boolean

    abstract fun getTime(): Pair<Int, Int>?

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

    fun getTimePvP(index: Int): Pair<Int, Int>? {
        return ScoreboardUtils.getAt(index)?.let {
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

    fun getTimePvE(index: Int, timeStringIndex: Int): Pair<Int, Int>? {
        return ScoreboardUtils.getAt(index)?.let {
            val timeString: String = it.substring(timeStringIndex)
            val colonPosition = timeString.indexOf(":")
            return Pair(
                timeString.substring(0, colonPosition).toInt(), //.coerceAtMost(14),
                timeString.substring(colonPosition + 1).toInt()
            )
        }
    }
}