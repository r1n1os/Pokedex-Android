package com.example.pokedexandroid.data.remote.pokemon_list

import com.example.pokedexandroid.data.local_database.pokemon_entity.PokemonEntity
import com.example.pokedexandroid.data.remote.dto_models.PokemonDto
import com.google.gson.annotations.SerializedName

data class PokemonListResponse(
    @SerializedName("next") val nextUrl: String?,
    @SerializedName("results") val results: MutableList<PokemonDto>?
)
