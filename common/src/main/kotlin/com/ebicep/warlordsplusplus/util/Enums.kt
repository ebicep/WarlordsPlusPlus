package com.ebicep.warlordsplusplus.util

import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component

enum class Team(val teamName: String, val color: ChatFormatting?) {
    BLUE("Blue", ChatFormatting.BLUE),
    RED("Red", ChatFormatting.RED),
    NONE("NONE", null)
}

enum class WarlordClass(val classname: String, val shortName: String) {
    WARRIOR("Warrior", "WAR"),
    SHAMAN("Shaman", "SHA"),
    MAGE("Mage", "MAG"),
    PALADIN("Paladin", "PAL"),
    ROGUE("Rogue", "ROG"),
    ARCANIST("Arcanist", "ARC"),
    NONE("NONE", "NONE")
}

enum class Specialization(
    val classname: String,
    val red: String,
    val purple: String,
    val blue: String,
    val orange: String,
    val weapon: String,
    val type: SpecType
) {
    AVENGER(
        "Avenger",
        "Consecrate",
        "Light Infusion",
        "Holy Radiance",
        "Avenger's Wrath",
        "Avenger",
        SpecType.DAMAGE

    ),
    CRUSADER(
        "Crusader",
        "Consecrate",
        "Light Infusion",
        "Holy Radiance",
        "Inspiring Presence",
        "Crusader",
        SpecType.TANK
    ),
    PROTECTOR(
        "Protector",
        "Consecrate",
        "Light Infusion",
        "Holy Radiance",
        "Hammer of Light",
        "Protector",
        SpecType.HEALER
    ),
    BERSERKER(
        "Berserker",
        "Seismic Wave",
        "Ground Slam",
        "Blood Lust",
        "Berserk",
        "receives 35 less",
        SpecType.DAMAGE
    ),
    DEFENDER(
        "Defender",
        "Seismic Wave",
        "Ground Slam",
        "Intervene",
        "Last Stand",
        "receives 25 less",
        SpecType.TANK
    ),
    REVENANT(
        "Revenant",
        "Reckless Charge",
        "Ground Slam",
        "Orbs of Life",
        "Undying Army",
        "crippling",
        SpecType.HEALER
    ),
    PYROMANCER(
        "Pyromancer",
        "Flame Burst",
        "Time Warp",
        "Arcane Shield",
        "Inferno",
        "Fireball",
        SpecType.DAMAGE
    ),
    CRYOMANCER(
        "Cryomancer",
        "Freezing Breath",
        "Time Warp",
        "Arcane Shield",
        "Ice Barrier",
        "Frostbolt",
        SpecType.TANK
    ),
    AQUAMANCER(
        "Aquamancer",
        "Water Breath",
        "Time Warp",
        "Arcane Shield",
        "Healing Rain",
        "Water",
        SpecType.HEALER
    ),
    THUNDERLORD(
        "Thunderlord",
        "Chain Lightning",
        "Windfury Weapon",
        "Lightning Rod",
        "Capacitor Totem",
        "Lightning",
        SpecType.DAMAGE
    ),
    SPIRITGUARD(
        "Spiritguard",
        "Spirit Link",
        "Soulbinding Weapon",
        "Repentance",
        "Death's Debt",
        "Fallen",
        SpecType.TANK
    ),
    EARTHWARDEN(
        "Earthwarden",
        "Boulder",
        "Earthliving Weapon",
        "Chain Heal",
        "Healing Totem",
        "Earthen",
        SpecType.HEALER
    ),
    ASSASSIN(
        "Assassin",
        "Incendiary Curse",
        "Blinding Assault",
        "Soul Switch",
        "Order Of Eviscerate",
        "Judgement",
        SpecType.DAMAGE
    ),
    VINDICATOR(
        "Vindicator",
        "Soul Shackle",
        "Heart To Heart",
        "Prism Guard",
        "Vindicate",
        "Righteous",
        SpecType.TANK
    ),
    APOTHECARY(
        "Apothecary",
        "Soothing Puddle",
        "Vitality Liquor",
        "Remedic Chains",
        "Draining Miasma",
        "Impaling",
        SpecType.HEALER
    ),
    CONJURER(
        "Conjurer",
        "Soulfire Beam",
        "Energy Seer",
        "Contagious Facade",
        "Astral Plague",
        "Poisonous",
        SpecType.DAMAGE
    ),
    SENTINEL(
        "Sentinel",
        "Guardian Beam",
        "Energy Seer",
        "Mystical Barrier",
        "Sanctuary",
        "Fortifying",
        SpecType.TANK
    ),
    LUMINARY(
        "Luminary",
        "Ray of Light",
        "Energy Seer",
        "Sanctified Beacon",
        "Divine Blessing",
        "Merciful",
        SpecType.HEALER
    ),

    NONE("NONE", "", "", "", "", "NONE", SpecType.NONE);


    ;

    val icon
        get() = type.coloredSymbol
    val iconComponent
        get() = type.coloredSymbolComponent
}

enum class SpecType(val specName: String, val symbol: String, val color: ChatFormatting) {
    DAMAGE("damage", "銌", ChatFormatting.RED),
    TANK("defense", "鉰", ChatFormatting.YELLOW),
    HEALER("healer", "銀", ChatFormatting.GREEN),
    NONE("NONE", "", ChatFormatting.OBFUSCATED);

    val coloredSymbol = "$color$symbol" //TODO
    val coloredSymbolComponent = Component.literal(symbol)
        .withStyle {
            it.withColor(color)
        }
}
