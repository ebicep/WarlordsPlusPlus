package com.ebicep.warlordsplusplus.detectors

object DetectorManager {

    val detectors = mutableListOf<Detector>()

    init {
        with(detectors) {
            add(DamageAndHealDetector)
            add(KillAssistDetector)
            add(GameEndDetector)
            add(HitDetector)
            add(RespawnTimerDetector)
            add(WinLossDetector)
        }

    }

}