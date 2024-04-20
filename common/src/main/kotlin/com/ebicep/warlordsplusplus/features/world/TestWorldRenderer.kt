package com.ebicep.warlordsplusplus.features.world

import com.ebicep.warlordsplusplus.render.RenderHelperWorld
import com.ebicep.warlordsplusplus.util.Colors

object TestWorldRenderer : RenderHelperWorld() {

    override fun shouldRender(): Boolean {
        return false
    }

    override fun render0() {
        translateToPos(
            levelRenderEvent!!.camera.position.x,
            levelRenderEvent!!.camera.position.y,
            levelRenderEvent!!.camera.position.z + 10
        )
        autoRotate()
        scaleForWorldRendering(10f)
        "Hello".drawCentered(seeThruBlocks = true, color = Colors.GREEN)
    }

}