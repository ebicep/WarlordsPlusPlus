package com.ebicep.warlordsplusplus.renderapi

import com.ebicep.warlordsplusplus.WarlordsPlusPlus

interface Render {

    fun render(): Boolean {
        if (!WarlordsPlusPlus.isEnabled()) {
            return false
        }
        if (!shouldRender()) {
            return false
        }
        setupRender()
        return true
    }

    fun setupRender()

    fun shouldRender(): Boolean

    fun render0()

}