package com.example.pokedexandroid.presentation.pokemon_list_screen

import com.example.pokedexandroid.domain.model.PokemonListModel

data class PokemonListState(
    val isLoading: Boolean = false,
    val pokemonList: List<PokemonListModel> = emptyList(),
    val error: String = "",
    val nextUrl: String? = null
)