package com.example.pokedexandroid.domain.model

import androidx.compose.ui.graphics.Color

data class PokemonDetails(
    var name: String,
    var photoUrl: String,
    var color: Color,
    var types: List<Type>,
    var stats: List<Stats>
)
