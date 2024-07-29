package com.example.pokedexandroid.data.remote.pokemon_list

import com.example.pokedexandroid.data.remote.dto.PokemonDto
import com.google.gson.annotations.SerializedName

data class PokemonListResponse(
    @SerializedName("next") val nextUrl: String?,
    @SerializedName("results") val results: MutableList<PokemonDto>?
)
