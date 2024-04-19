package com.ebicep.warlordsplusplus.features.hud

import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component

class HudElementScreen : Screen(Component.literal("TODO")) {

    private var moving: AbstractHudElement? = null
    private var movingXOffset: Int = 0
    private var movingYOffset: Int = 0

    override fun render(guiGraphics: GuiGraphics, i: Int, j: Int, f: Float) {
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, i: Int): Boolean {
        for (it in HudElementManager.hudElements) {
            if (
                it.x < mouseX && mouseX < it.x + it.width() &&
                it.y < mouseY && mouseY < it.y + it.height()
            ) {
                moving = it
                movingXOffset = mouseX.toInt() - it.x
                movingYOffset = mouseY.toInt() - it.y
                return true
            }
        }
        return super.mouseClicked(mouseX, mouseY, i)
    }

    override fun mouseReleased(d: Double, e: Double, i: Int): Boolean {
        moving = null
        return super.mouseReleased(d, e, i)
    }

    override fun mouseDragged(mouseX: Double, mouseY: Double, i: Int, f: Double, g: Double): Boolean {
        if (moving != null) {
            // check if the element is still in the screen
            var newX = mouseX.toInt() - movingXOffset
            var newY = mouseY.toInt() - movingYOffset
            if (newX < 0) {
                newX = 0
            } else if ((newX + moving!!.width()) > width) {
                newX = width - moving!!.width()
            }
            if (newY < 0) {
                newY = 0
            } else if ((newY + moving!!.height()) > height) {
                newY = height - moving!!.height()
            }
            moving!!.x = newX
            moving!!.y = newY
            return true
        }
        return super.mouseDragged(mouseX, mouseY, i, f, g)
    }

}