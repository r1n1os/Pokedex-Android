package com.example.pokedexandroid.data.remote.dto_models

import com.google.gson.annotations.SerializedName

data class PokemonDto(
    val name: String,
    @SerializedName("url") val extraInfoUrl: String?,
    val pokemonDetailsDto: PokemonDetailsDto
)
