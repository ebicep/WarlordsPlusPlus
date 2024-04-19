package com.ebicep.warlordsplusplus.features.hud

import com.ebicep.warlordsplusplus.renderapi.api.RenderHelperGui

abstract class AbstractHudElement(var renderHelper: RenderHelperGui, var x: Int = 0, var y: Int = 0) {

    abstract fun width(): Int

    abstract fun height(): Int

}