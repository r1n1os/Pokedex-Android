package com.example.pokedexandroid.ui.PokemonListScreen

import com.example.pokedexandroid.data.LocalDatabase.PokemonEntity.PokemonEntity

data class PokemonListState(
    val isLoading: Boolean = false,
    val pokemonList: List<PokemonEntity> = emptyList(),
    val error: String = "",
    val nextUrl: String? = null
)