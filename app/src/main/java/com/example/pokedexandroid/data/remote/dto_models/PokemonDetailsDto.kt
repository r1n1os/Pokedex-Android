package com.example.pokedexandroid.data.remote.dto_models

import com.google.gson.annotations.SerializedName

data class PokemonDetailsDto(
    val id: Int,
    @SerializedName("stats")
    val stats: List<StatsDto>
)
