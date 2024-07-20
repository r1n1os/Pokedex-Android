package com.example.pokedexandroid.data.remote.PokemonList

import com.example.pokedexandroid.data.LocalDatabase.PokemonEntity.PokemonEntity
import com.google.gson.annotations.SerializedName

data class PokemonListResponse(
    @SerializedName("next") val nextUrl: String?,
    @SerializedName("results") val results: MutableList<PokemonEntity>?
)
