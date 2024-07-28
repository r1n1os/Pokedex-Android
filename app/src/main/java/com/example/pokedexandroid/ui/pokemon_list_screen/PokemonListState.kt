package com.example.pokedexandroid.ui.pokemon_list_screen

import com.example.pokedexandroid.domain.model.PokemonList

data class PokemonListState(
    val isLoading: Boolean = false,
    val pokemonList: List<PokemonList> = emptyList(),
    val error: String = "",
    val nextUrl: String? = null
)