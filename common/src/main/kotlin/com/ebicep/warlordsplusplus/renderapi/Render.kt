package com.ebicep.warlordsplusplus.renderapi

interface Render {

    fun render(): Boolean {
//        if (!WarlordsPlusPlus.isEnabled()) {
//            return
//        }
        if (shouldRender()) {
            setupRender()
            return true
        }
        return false
    }

    fun setupRender()

    fun shouldRender(): Boolean

    fun render0()

}