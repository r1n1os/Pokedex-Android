package com.example.pokedexandroid.utils

object HelperMethods {
    fun fullPokemonStatNameToShorten(statName: String): String {
        return when(statName) {
            "hp" -> "Hp"
            "attack" -> "Att"
            "defense" -> "Def"
            "special-attack" -> "SAtt"
            "special-defense" -> "SDef"
            "speed" -> "Spd"
            else -> ""
        }
    }
}