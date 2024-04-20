package com.ebicep.warlordsplusplus.features.world

import com.ebicep.chatplus.events.EventBus
import com.ebicep.warlordsplusplus.events.LevelRenderEvent
import com.ebicep.warlordsplusplus.features.Feature
import com.ebicep.warlordsplusplus.render.RenderHelperWorld

object WorldRenderManager : Feature {

    private val worldRenderers = mutableListOf<RenderHelperWorld>()

    init {
        with(worldRenderers) {
            add(PowerUpTimer)
            add(TestWorldRenderer)
        }
        EventBus.register<LevelRenderEvent> {
            worldRenderers.forEach { renderer ->
                renderer.levelRenderEvent = it
                renderer.render()
            }
        }

    }

}