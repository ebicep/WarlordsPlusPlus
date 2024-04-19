package com.ebicep.warlordsplusplus.detectors

object DetectorManager {

    val detectors = mutableListOf<Detector>()

    init {
        with(detectors) {
            add(DamageAndHealParser)
            add(KillAssistParser)
            add(GameEndDetector)
            add(HitDetector)
            add(RespawnTimerDetector)
            add(WinLossDetector)
        }

    }

}