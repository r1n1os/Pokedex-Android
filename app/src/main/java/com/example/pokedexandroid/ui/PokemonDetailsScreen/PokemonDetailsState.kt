package com.example.pokedexandroid.ui.PokemonDetailsScreen

import com.example.pokedexandroid.data.LocalDatabase.PokemonEntity.PokemonEntity

data class PokemonDetailsState(
    val isLoading: Boolean = false,
    val pokemonEntity: PokemonEntity? = null,
    val error: String = "",)