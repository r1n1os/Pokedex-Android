package com.example.pokedexandroid.domain.model

import androidx.compose.ui.graphics.Color

data class PokemonDetailsModel(
    var name: String,
    var photoUrl: String,
    var color: Color,
    var types: List<TypeModel>,
    var stats: List<StatsModel>
) {
    constructor(): this("", "", Color.Transparent, emptyList(), emptyList())
}
