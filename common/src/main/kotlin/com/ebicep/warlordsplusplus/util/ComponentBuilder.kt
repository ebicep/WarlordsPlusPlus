package com.ebicep.warlordsplusplus.util

import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent

class ComponentBuilder(private val component: MutableComponent) {

    constructor(text: String, chatFormatting: ChatFormatting = ChatFormatting.RESET) : this(
        Component.literal(text).withStyle(chatFormatting)
    )

    fun append(text: String, chatFormatting: ChatFormatting = ChatFormatting.RESET): ComponentBuilder {
        component.append(Component.literal(text).withStyle(chatFormatting))
        return this
    }

    fun create(): MutableComponent {
        return component
    }

}