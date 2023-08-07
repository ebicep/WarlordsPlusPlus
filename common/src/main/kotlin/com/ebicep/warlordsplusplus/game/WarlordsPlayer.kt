package com.ebicep.warlordsplusplus.game

import com.ebicep.warlordsplusplus.event.WarlordsPlayerEvents
import com.ebicep.warlordsplusplus.event.WarlordsPlayerEventsImpl
import com.ebicep.warlordsplusplus.util.SpecType
import com.ebicep.warlordsplusplus.util.Specialization
import com.ebicep.warlordsplusplus.util.Team
import com.ebicep.warlordsplusplus.util.WarlordClass
import net.minecraft.client.Minecraft

object WarlordsPlayer {

    var healingGivenCounter = 0
        private set
    var damageDoneCounter = 0
        private set
    var energyReceivedCounter = 0
        private set
    var healingReceivedCounter = 0
        private set
    var damageTakenCounter = 0
        private set
    var energyGivenCounter = 0
        private set
    var energyStoleCounter = 0
        private set
    var energyLostCounter = 0
        private set
    var killParticipation = 0
        private set

    //minute
    //kill,death,hit,dmg,heal,dmg taken,heal received
    var minuteStat = Array(1) { IntArray(7) }
        private set
    val kills: Int
        get() = minuteStat[0].sum()
    val hits: Int
        get() = minuteStat[2].sum()

    var spec: Specialization = Specialization.NONE
    var superSpec: SpecType = SpecType.NONE
    var warlord: WarlordClass = WarlordClass.NONE
    var team: Team = Team.NONE

    init {
        WarlordsPlayerEventsImpl.KILL_EVENT.register { event: WarlordsPlayerEvents.KillEvent ->
            val playerName = Minecraft.getInstance().player!!.scoreboardName
            if (event.player == playerName) {
                minuteStat[0][0]++
            } else if (event.deathPlayer == playerName) {
                minuteStat[0][1]++
            }
        }
        WarlordsPlayerEventsImpl.HIT_EVENT.register { event: WarlordsPlayerEvents.HitEvent ->
            minuteStat[0][2]++
        }
        WarlordsPlayerEventsImpl.DAMAGE_DONE_EVENT.register { event: WarlordsPlayerEvents.DamageDoneEvent ->
            damageDoneCounter += event.amount
            minuteStat[0][3] += event.amount
        }
        WarlordsPlayerEventsImpl.HEALING_GIVEN_EVENT.register { event: WarlordsPlayerEvents.HealingGivenEvent ->
            healingGivenCounter += event.amount
            minuteStat[0][4] += event.amount
        }
        WarlordsPlayerEventsImpl.DAMAGE_TAKEN_EVENT.register { event: WarlordsPlayerEvents.DamageTakenEvent ->
            damageTakenCounter += event.amount
            minuteStat[0][5] += event.amount
        }
        WarlordsPlayerEventsImpl.HEALING_RECEIVED_EVENT.register { event: WarlordsPlayerEvents.HealingReceivedEvent ->
            healingReceivedCounter += event.amount
            minuteStat[0][6] += event.amount
        }
        WarlordsPlayerEventsImpl.ENERGY_GIVEN_EVENT.register { event: WarlordsPlayerEvents.EnergyGivenEvent ->
            energyGivenCounter += event.amount
        }
        WarlordsPlayerEventsImpl.ENERGY_RECEIVED_EVENT.register { event: WarlordsPlayerEvents.EnergyReceivedEvent ->
            energyReceivedCounter += event.amount
        }
        WarlordsPlayerEventsImpl.ENERGY_STOLEN_EVENT.register { event: WarlordsPlayerEvents.EnergyStolenEvent ->
            energyStoleCounter += event.amount
        }
    }

}