package com.ebicep.warlordsplusplus.forge.config

import me.shedaniel.autoconfig.ConfigData
import me.shedaniel.autoconfig.annotation.Config
import me.shedaniel.autoconfig.annotation.ConfigEntry
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment
import java.util.*

@Config(name = "TEST")
class Example : ConfigData {

    @ConfigEntry.Category("a")
    @ConfigEntry.Gui.TransitiveObject
    var moduleA = ModuleA()

    @ConfigEntry.Category("a")
    @ConfigEntry.Gui.TransitiveObject
    var empty = Empty()

    @ConfigEntry.Category("b")
    @ConfigEntry.Gui.TransitiveObject
    var moduleB = ModuleB()

    fun ExampleConfig() {}

    @Config(name = "module_a")
    class ModuleA : ConfigData {
        @ConfigEntry.Gui.PrefixText
        var aBoolean = true

        @ConfigEntry.Gui.Tooltip(count = 2)
        var anEnum: ExampleEnum

        @ConfigEntry.Gui.Tooltip(count = 2)
        @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
        var anEnumWithButton: ExampleEnum

        @Comment("This tooltip was automatically applied from a Jankson @Comment")
        var aString = "hello"

        @ConfigEntry.Gui.CollapsibleObject(startExpanded = true)
        var anObject: PairOfIntPairs
        var list: List<Int>
        var array: IntArray
        var complexList: List<PairOfInts>
        var complexArray: Array<PairOfInts>

        init {
            anEnum = ExampleEnum.FOO
            anEnumWithButton = ExampleEnum.FOO
            anObject = PairOfIntPairs(PairOfInts(), PairOfInts(3, 4))
            list = mutableListOf(1, 2, 3)
            array = intArrayOf(1, 2, 3)
            complexList = Arrays.asList(PairOfInts(0, 1), PairOfInts(3, 7))
            complexArray = arrayOf(PairOfInts(0, 1), PairOfInts(3, 7))
        }
    }

    @Config(name = "empty")
    class Empty : ConfigData

    @Config(name = "module_b")
    class ModuleB : ConfigData {
        @ConfigEntry.BoundedDiscrete(min = -1000L, max = 2000L)
        var intSlider = 500

        @ConfigEntry.BoundedDiscrete(min = -1000L, max = 2000L)
        var longSlider = 500L

        @ConfigEntry.Gui.TransitiveObject
        var anObject = PairOfIntPairs(PairOfInts(), PairOfInts(3, 4))

        @ConfigEntry.Gui.Excluded
        var aList = Arrays.asList(PairOfInts(), PairOfInts(3, 4))

        @ConfigEntry.ColorPicker
        var color = 16777215
    }

    class PairOfIntPairs @JvmOverloads constructor(
        @field:ConfigEntry.Gui.CollapsibleObject var first: PairOfInts = PairOfInts(),
        @field:ConfigEntry.Gui.CollapsibleObject var second: PairOfInts = PairOfInts()
    )

    class PairOfInts @JvmOverloads constructor(var foo: Int = 1, var bar: Int = 2)

    enum class ExampleEnum {
        FOO, BAR, BAZ
    }

}