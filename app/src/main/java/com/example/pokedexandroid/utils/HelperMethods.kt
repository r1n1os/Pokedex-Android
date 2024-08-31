package com.example.pokedexandroid.utils

object HelperMethods {
    fun fullPokemonStatNameToShorten(statName: String): String {
        return when(statName) {
            "hp" -> "Hp"
            "attack" -> "Att"
            "defense" -> "Def"
            "special-attack" -> "Special Att"
            "special-defense" -> "Special Def"
            "speed" -> "Speed"
            else -> ""
        }
    }
}