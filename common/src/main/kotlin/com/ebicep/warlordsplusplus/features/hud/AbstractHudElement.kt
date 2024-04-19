package com.ebicep.warlordsplusplus.features.hud

import com.ebicep.warlordsplusplus.renderapi.api.RenderHelperGui
import com.ebicep.warlordsplusplus.util.Colors
import net.minecraft.client.Minecraft
import net.minecraft.network.chat.MutableComponent

abstract class AbstractHudElement(var x: Int = 0, var y: Int = 0) {

    var renderHelper: RenderHelperGui

    init {
        this.renderHelper = object : RenderHelperGui() {

            override fun shouldRender(): Boolean {
                return this@AbstractHudElement.shouldRender()
            }

            override fun render0() {
                val text = getText()
                createPose {
                    translateX(-2)
                    guiGraphics?.fill(0, 0, Minecraft.getInstance().font.width(text) + 3, 11, Colors.DEF.convertToArgb(100))
                }
                createPose {
                    translateY(-2)
                    guiGraphics?.drawString(Minecraft.getInstance().font, text, 0, 0, 0, false)
                }
            }
        }
    }

    abstract fun shouldRender(): Boolean

    abstract fun getText(): MutableComponent

    fun width(): Int {
        return Minecraft.getInstance().font.width(getText())
    }

    fun height(): Int {
        return 10
    }

}