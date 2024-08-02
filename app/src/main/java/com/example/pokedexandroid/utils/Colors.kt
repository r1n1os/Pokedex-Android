package com.example.pokedexandroid.utils

import androidx.compose.ui.graphics.Color

object Colors {
    fun getTypeColor(type: String): Color {
        return when (type) {
            "fighting" ->  Color(0xff9f422a)
            "flying" -> Color(0xff642785)
            "poison"-> Color(0xff642785)
            "ground" -> Color(0xffAD7235)
            "rock" -> Color(0xff4B190E)
            "bug" -> Color(0xff179A55)
            "ghost" -> Color(0xff363069)
            "steel" -> Color(0xff5C756D)
            "fire" -> Color(0xffB22328)
            "water" -> Color(0xff2648DC)
            "grass" -> Color(0xff007c42)
            "electric" -> Color(0xffE0E64B)
            "psychic" -> Color(0xffAC296B)
            "ice" -> Color(0xff7ECFF2)
            "dragon" -> Color(0xff378A94)
            "fairy" -> Color(0xff9E1A44)
            "dark" -> Color(0xff040706)
            else -> Color.White
        }
    }
}