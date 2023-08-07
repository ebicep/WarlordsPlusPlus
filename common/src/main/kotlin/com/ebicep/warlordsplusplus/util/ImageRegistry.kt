package com.ebicep.warlordsplusplus.util

import com.ebicep.warlordsplusplus.MOD_ID
import net.minecraft.resources.ResourceLocation

enum class ImageRegistry(path: String) {

    WEIRDCHAMP("/images/weirdchamp.png"),
    RED_ABILITY("/images/abilityicons/redability.png"),
    PURPLE_ABILITY("/images/abilityicons/purpleability.png"),
    BLUE_ABILITY("/images/abilityicons/blueability.png"),
    ORANGE_ABILITY("/images/abilityicons/orangeability.png"),
    COOLDOWN("/images/abilityicons/cooldown.png"),

    ;

    private var resourceLocation: ResourceLocation? = null

    init {
//        val resourceOptional = Minecraft.getInstance().resourceManager.getResource(ResourceLocation(MODID, path))
//        resourceOptional.ifPresentOrElse({
//            val inputStream = it.open()
//            val img = ImageIO.read(inputStream)
//            val n = NativeImage(img.width, img.height, true)
//            WarlordsPlusPlus.LOGGER.log(Level.INFO, "Registering image $path - ${img.width}x${img.height}")
//            for (i in 0 until img.width) {
//                for (j in 0 until img.height) {
//                    val c: IntArray = img.raster.getPixel(i, j, IntArray(4))
//                    val c1 = Color(c[3], c[2], c[1], c[0])
//                    n.setPixelRGBA(i, j, (c1.rgb shl 8) + c1.alpha)
//                }
//            }
//            val dynamicTexture = DynamicTexture(n)
//            this.resourceLocation = Minecraft.getInstance().textureManager.register(path, dynamicTexture)
//            WarlordsPlusPlus.LOGGER.log(Level.INFO, "Registered image $path - $resourceLocation")
//        }, {
//            WarlordsPlusPlus.LOGGER.log(Level.ERROR, "Failed to register image $path")
        this.resourceLocation = ResourceLocation(MOD_ID, path)
//        })
    }


    fun getResourceLocation(): ResourceLocation? {
        return resourceLocation
    }

}