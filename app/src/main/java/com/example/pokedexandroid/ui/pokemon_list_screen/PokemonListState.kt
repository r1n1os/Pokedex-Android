package com.example.pokedexandroid.ui.pokemon_list_screen

import com.example.pokedexandroid.data.local_database.pokemon_entity.PokemonEntity

data class PokemonListState(
    val isLoading: Boolean = false,
    val pokemonList: List<PokemonEntity> = emptyList(),
    val error: String = "",
    val nextUrl: String? = null
)