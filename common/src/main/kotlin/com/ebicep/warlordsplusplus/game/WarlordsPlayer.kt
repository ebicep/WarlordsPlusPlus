package com.ebicep.warlordsplusplus.game

import com.ebicep.chatplus.events.EventBus
import com.ebicep.warlordsplusplus.event.WarlordsPlayerEvents
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
        get() = minuteStat.sumOf { it[0] }
    val hits: Int
        get() = minuteStat.sumOf { it[2] }

    var spec: Specialization = Specialization.NONE
    var superSpec: SpecType = SpecType.NONE
    var warlord: WarlordClass = WarlordClass.NONE
    var team: Team = Team.NONE

    init {
        EventBus.register<WarlordsPlayerEvents.KillEvent> {
            val playerName = Minecraft.getInstance().player!!.scoreboardName
            if (it.player == playerName) {
                minuteStat[0][0]++
            } else if (it.deathPlayer == playerName) {
                minuteStat[0][1]++
            }
        }
        EventBus.register<WarlordsPlayerEvents.HitEvent> {
            minuteStat[0][2]++
        }
        EventBus.register<WarlordsPlayerEvents.DamageDoneEvent> {
            damageDoneCounter += it.amount
            minuteStat[0][3] += it.amount
        }
        EventBus.register<WarlordsPlayerEvents.HealingGivenEvent> {
            healingGivenCounter += it.amount
            minuteStat[0][4] += it.amount
        }
        EventBus.register<WarlordsPlayerEvents.DamageTakenEvent> {
            damageTakenCounter += it.amount
            minuteStat[0][5] += it.amount
        }
        EventBus.register<WarlordsPlayerEvents.HealingReceivedEvent> {
            healingReceivedCounter += it.amount
            minuteStat[0][6] += it.amount
        }
        EventBus.register<WarlordsPlayerEvents.EnergyGivenEvent> {
            energyGivenCounter += it.amount
        }
        EventBus.register<WarlordsPlayerEvents.EnergyReceivedEvent> {
            energyReceivedCounter += it.amount
        }
        EventBus.register<WarlordsPlayerEvents.EnergyStolenEvent> {
            energyStoleCounter += it.amount
        }
    }

}