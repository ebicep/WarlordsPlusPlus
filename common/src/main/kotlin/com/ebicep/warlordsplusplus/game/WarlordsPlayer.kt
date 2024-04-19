package com.ebicep.warlordsplusplus.game

import com.ebicep.chatplus.events.EventBus
import com.ebicep.warlordsplusplus.events.WarlordsGameEvents
import com.ebicep.warlordsplusplus.events.WarlordsPlayerEvents
import com.ebicep.warlordsplusplus.util.SpecType
import com.ebicep.warlordsplusplus.util.Specialization
import com.ebicep.warlordsplusplus.util.Team
import com.ebicep.warlordsplusplus.util.WarlordClass
import net.minecraft.client.Minecraft

object WarlordsPlayer {

    var healingGivenCounter = 0
    var damageDoneCounter = 0
    var energyReceivedCounter = 0
    var healingReceivedCounter = 0
    var damageTakenCounter = 0
    var energyGivenCounter = 0
    var energyStoleCounter = 0
    var energyLostCounter = 0
    var killParticipation = 0

    //minute
    //kill,death,hit,dmg,heal,dmg taken,heal received
    var minuteStat = Array(1) { IntArray(7) }
    val kills: Int
        get() = minuteStat.sumOf { it[0] }
    val hits: Int
        get() = minuteStat.sumOf { it[2] }

    var spec: Specialization = Specialization.NONE
    var superSpec: SpecType = SpecType.NONE
    var warlord: WarlordClass = WarlordClass.NONE
    var team: Team = Team.NONE

    init {
//        setTestValues()
        EventBus.register<WarlordsGameEvents.ResetEvent> {
            healingGivenCounter = 0
            damageDoneCounter = 0
            energyReceivedCounter = 0
            healingReceivedCounter = 0
            damageTakenCounter = 0
            energyGivenCounter = 0
            energyStoleCounter = 0
            energyLostCounter = 0
            killParticipation = 0

            minuteStat = Array(1) { IntArray(7) }
            spec = Specialization.NONE
            superSpec = SpecType.NONE
            warlord = WarlordClass.NONE
            team = Team.NONE
        }
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

    fun setTestValues() {
        healingGivenCounter = 100
        damageDoneCounter = 200
        energyReceivedCounter = 300
        healingReceivedCounter = 400
        damageTakenCounter = 500
        energyGivenCounter = 600
        energyStoleCounter = 700
        energyLostCounter = 800
        killParticipation = 900

        minuteStat = arrayOf(intArrayOf(1, 2, 3, 4, 5, 6, 7))
        spec = Specialization.PYROMANCER
        superSpec = SpecType.DAMAGE
        warlord = WarlordClass.PALADIN
        team = Team.BLUE
    }

}