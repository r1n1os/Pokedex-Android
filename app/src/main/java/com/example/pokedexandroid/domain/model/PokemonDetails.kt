package com.example.pokedexandroid.domain.model

import androidx.compose.ui.graphics.Color

data class PokemonDetails(
    var name: String,
    val color: Color,
    var stats: List<Stats>
)
