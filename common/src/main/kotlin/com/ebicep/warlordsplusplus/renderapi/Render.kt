package com.ebicep.warlordsplusplus.renderapi

interface Render {

    fun render() {
//        if (!WarlordsPlusPlus.isEnabled()) {
//            return
//        }
        if (shouldRender()) {
            setupRender()
        }
    }

    fun setupRender()

    fun shouldRender(): Boolean

    fun render0()

}