package com.ebicep.warlordsplusplus.features.world

import com.ebicep.chatplus.events.EventBus
import com.ebicep.warlordsplusplus.events.WarlordsGameEvents
import com.ebicep.warlordsplusplus.game.GameModes
import com.ebicep.warlordsplusplus.game.GameStateManager
import com.ebicep.warlordsplusplus.render.RenderHelperWorld
import com.ebicep.warlordsplusplus.util.Colors
import dev.architectury.event.EventResult
import dev.architectury.event.events.client.ClientLifecycleEvent
import dev.architectury.event.events.client.ClientTickEvent
import dev.architectury.event.events.common.EntityEvent
import net.minecraft.world.entity.decoration.ArmorStand
import java.util.*

object PowerUpTimer : RenderHelperWorld() {

    private val powerUpColors = mapOf(
        "DAMAGE" to Colors.RED,
        "ENERGY" to Colors.ORANGE,
        "HEALING" to Colors.GREEN,
        "SPEED" to Colors.YELLOW,
        "COOLDOWN" to Colors.AQUA,
    )
    private var powerUps: MutableMap<UUID, PowerUp> = mutableMapOf()
    private var worldArmorStands = mutableSetOf<ArmorStand>()
    private var justJoinedWorld = false

    data class PowerUp(
        val id: UUID,
        val x: Double,
        val y: Double,
        val z: Double,
        val color: Colors,
        var respawnTimer: Int = -1,
        var visible: Boolean = true
    )

    init {
        EventBus.register<WarlordsGameEvents.ResetEvent> {
            powerUps.clear()
            worldArmorStands.clear()
        }
        ClientLifecycleEvent.CLIENT_LEVEL_LOAD.register {
            worldArmorStands.clear()
            justJoinedWorld = true
        }
        EntityEvent.ADD.register { entity, world ->
            if (GameStateManager.notInGame) {
                return@register EventResult.pass()
            }
            if (entity is ArmorStand) {
                worldArmorStands.add(entity)
            }
            EventResult.pass()
        }
        ClientTickEvent.CLIENT_POST.register {
            if (GameStateManager.notInGame) {
                return@register
            }
            if (worldArmorStands.isEmpty()) {
                return@register
            }
            if (justJoinedWorld) {
                justJoinedWorld = false
                worldArmorStands.addAll(
                    mc.level!!.getEntitiesOfClass(
                        ArmorStand::class.java,
                        mc.player!!.boundingBox.inflate(200.0, 200.0, 200.0)
                    )
                )
            }
            // checking for new powerups
            worldArmorStands.filter {
                !powerUps.containsKey(it.uuid)
            }.forEach {
                powerUpColors
                    .filter { (key, _) -> it.name.string.contains(key) }
                    .values
                    .firstOrNull()
                    ?.let { c ->
                        removePreviousPowerUp(it)
                        powerUps[it.uuid] = PowerUp(it.uuid, it.x, it.y, it.z, c)
                    }
            }
        }

        EventBus.register<WarlordsGameEvents.SecondEvent> {
            worldArmorStands.removeIf {
                val isRemoved = it.isRemoved
                if (powerUps.containsKey(it.uuid)) {
                    val powerUp = powerUps[it.uuid]!!
                    powerUp.visible = false
                    if (isRemoved) {
                        powerUp.respawnTimer = 45
                    } else {
                        powerUp.respawnTimer = -1
                        powerUp.visible = true
                    }
                }
                isRemoved
            }
            powerUps.forEach {
                it.value.respawnTimer--
            }
        }
    }

    private fun removePreviousPowerUp(armorStand: ArmorStand) {
        powerUps.values.filter { powerUp ->
            powerUp.id != armorStand.uuid &&
                    powerUp.x == armorStand.x &&
                    powerUp.y == armorStand.y &&
                    powerUp.z == armorStand.z
        }.forEach {
            powerUps.remove(it.id)
        }
    }

    override fun shouldRender(): Boolean {
        return GameStateManager.inGame
    }

    override fun render0() {
        powerUps.values.forEach {
            createPose {
                translateToPos(
                    it.x,
                    it.y + 3,
                    it.z
                )
                autoRotate()
                scaleForWorldRendering(7f)
                if (it.respawnTimer <= -1) {
                    if (it.visible) {
                        "\u2714"
                    } else {
                        "?"
                    }
                } else {
                    "${it.respawnTimer}"
                }.drawCentered(
                    seeThruBlocks = true,
                    color = when (GameStateManager.currentGameMode) {
                        GameModes.DOM, GameModes.TDM -> Colors.WHITE
                        else -> it.color
                    }
                )
            }
        }
    }

}