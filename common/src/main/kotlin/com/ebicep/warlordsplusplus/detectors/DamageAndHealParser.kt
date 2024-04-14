package com.ebicep.warlordsplusplus.detectors

import com.ebicep.chatplus.events.EventBus
import com.ebicep.warlordsplusplus.WarlordsPlusPlus
import com.ebicep.warlordsplusplus.event.WarlordsPlayerEvents

import com.ebicep.warlordsplusplus.game.GameStateManager
import dev.architectury.event.CompoundEventResult
import dev.architectury.event.events.client.ClientSystemMessageEvent
import net.minecraft.client.Minecraft
import net.minecraft.network.chat.Component
import java.util.regex.Pattern

const val SOMEBODY_DID = """«"""
const val YOU_DID = """»"""

private val numberPattern = Pattern.compile("\\s[0-9]+\\s")

object DamageAndHealParser : Detector {

    init {
        ClientSystemMessageEvent.RECEIVED.register { component: Component ->
            if (GameStateManager.notInGame) {
                return@register CompoundEventResult.pass()
            }
            try {
                val msg: String = component.string
                if (!(msg.contains(SOMEBODY_DID) || msg.contains(YOU_DID))) {
                    return@register CompoundEventResult.pass()
                }

                var otherPlayer: String
                var ability = ""
                val amount = getDamageOrHealthValue(msg)
                val isCrit = msg.contains("!")

                if (msg.contains(SOMEBODY_DID)) {
                    //Your Water Bolt healed you for
                    when {
                        msg.contains("Your") && msg.contains("you") -> {
                            otherPlayer = Minecraft.getInstance().player!!.name.string
                            //Water Bolt healed
                            val front: String = msg.substring(msg.indexOf("Your") + 5, msg.indexOf("you") - 1)
                            ability = front.substring(0, front.lastIndexOf(" ") - 1)
                        }
                        //PLAYER's ability hit you
                        msg.contains("'s") -> {
                            otherPlayer = msg.substring(2, msg.indexOf("'s"))
                            val hitIndex: Int = msg.indexOf(" hit")
                            val gaveIndex: Int = msg.indexOf(" gave")
                            val endIndex: Int = if (hitIndex != -1) hitIndex else gaveIndex
                            //TODO check others
                            ability = msg.substring(msg.indexOf("'s") + 3, endIndex)
                        }
                        //You took 500 dmg (revenant)
                        msg.contains("You took") -> {
                            otherPlayer = "EXTERNAL"
                        }
                        //PLAYER hit you for (melee)
                        msg.contains("hit") -> {
                            otherPlayer = msg.substring(2, msg.indexOf(" hit"))
                            ability = "melee"
                        }

                        else -> {
                            otherPlayer = "UNDEFINED"
                        }
                    }
                    when {
                        msg.contains("health") -> {
                            EventBus.post(
                                WarlordsPlayerEvents.HealingReceivedEvent(
                                    amount,
                                    otherPlayer,
                                    isCrit,
                                    ability = ability
                                )
                            )
                        }

                        msg.contains("damage") -> {
                            EventBus.post(
                                WarlordsPlayerEvents.DamageTakenEvent(
                                    amount,
                                    otherPlayer,
                                    isCrit,
                                    ability = ability
                                )
                            )
                            //Player lost Energy from otherPlayer's Avenger's Strike
                            if (msg.contains("Avenger's Strike"))
                                EventBus.post(
                                    WarlordsPlayerEvents.EnergyLostEvent(
                                        6,
                                        otherPlayer,
                                        ability = "Avenger's Strike"
                                    )
                                )
                        }

                        msg.contains("energy") -> {
                            otherPlayer = msg.substring(0, msg.indexOf("'s"))
                            EventBus.post(
                                WarlordsPlayerEvents.EnergyReceivedEvent(
                                    amount,
                                    otherPlayer,
                                    ability = ability
                                )
                            )
                        }
                    }
                } else if (msg.contains(YOU_DID)) {
                    when {
                        //Your ABILITY healed PLAYER for
                        msg.contains("health") && !msg.contains("you") -> {
                            otherPlayer = msg.substring(msg.indexOf("healed ") + 7, msg.indexOf("for") - 1)
                            ability = msg.substring(msg.indexOf("Your") + 5, msg.indexOf("healed") - 1)
                            EventBus.post(
                                WarlordsPlayerEvents.HealingGivenEvent(
                                    amount,
                                    otherPlayer,
                                    isCrit,
                                    ability = ability
                                )
                            )
                        }
                        //Your ABILITY healed you for
                        msg.contains("health") -> {
                            otherPlayer = Minecraft.getInstance().player!!.name.string
                            ability = msg.substring(msg.indexOf("Your") + 5, msg.indexOf("healed") - 1)
                            EventBus.post(
                                WarlordsPlayerEvents.HealingGivenEvent(
                                    amount,
                                    otherPlayer,
                                    isCrit,
                                    ability = ability
                                )
                            )
                        }
                        //Your ABILITY hit PLAYER for X damage
                        msg.contains("damage") -> {
                            otherPlayer = msg.substring(msg.indexOf("hit ") + 4, msg.indexOf("for") - 1)
                            ability = msg.substring(msg.indexOf("Your") + 5, msg.indexOf("hit") - 1)
                            EventBus.post(
                                WarlordsPlayerEvents.DamageDoneEvent(
                                    amount,
                                    otherPlayer,
                                    isCrit,
                                    ability = ability
                                )
                            )
                            //Player's Avenger's Strike stole energy from otherPlayer
                            if (msg.contains("Avengers Strike"))
                                EventBus.post(
                                    WarlordsPlayerEvents.EnergyStolenEvent(
                                        6,
                                        otherPlayer,
                                        ability = "Avenger's Strike"
                                    )
                                )
                        }
                        //Your ABILITY gave PLAYER X energy
                        msg.contains("energy") -> {
                            otherPlayer = msg.substring(msg.indexOf("gave") + 5, msg.indexOf("energy") - 4)
                            ability = msg.substring(msg.indexOf("Your") + 5, msg.indexOf("gave") - 1)
                            EventBus.post(
                                WarlordsPlayerEvents.EnergyGivenEvent(
                                    amount,
                                    otherPlayer,
                                    ability = ability
                                )
                            )
                        }
                        //Your ABILTIY was absorbed by PLAYER
                        msg.contains("absorbed") -> {
                            otherPlayer = msg.substring(msg.indexOf("by") + 3)
                            ability = msg.substring(msg.indexOf("Your") + 5, msg.indexOf("was") - 1)
                            EventBus.post(
                                WarlordsPlayerEvents.DamageAbsorbedEvent(
                                    amount,
                                    otherPlayer,
                                    isCrit,
                                    ability = ability
                                )
                            )
                        }
                    }
                }
            } catch (throwable: Throwable) {
                WarlordsPlusPlus.LOGGER.error(throwable)
            }
            CompoundEventResult.pass()
        }
    }

    private fun getDamageOrHealthValue(inputMessage: String): Int {
        try {
            var message = ""
            if (inputMessage.contains("for")) {
                message = inputMessage.substring(inputMessage.indexOf("for"))
            } else if (inputMessage.contains("Crusader")) {
                //giving energy
                message = inputMessage.substring(inputMessage.indexOf("energy") - 4)
            }
            val m = numberPattern.matcher(message.replace("!", ""))
            if (!m.find()) {
                return 0
            }
            return m.group().trim().toInt()
        } catch (e: Exception) {
            WarlordsPlusPlus.LOGGER.error("Failed to extract damage from this message: $inputMessage")
            e.printStackTrace()
        }
        return 0
    }
}



