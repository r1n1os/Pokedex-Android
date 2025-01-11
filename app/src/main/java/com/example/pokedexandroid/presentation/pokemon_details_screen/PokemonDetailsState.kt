package com.example.pokedexandroid.presentation.pokemon_details_screen

import com.example.pokedexandroid.domain.model.PokemonDetails

data class PokemonDetailsState(
    val isLoading: Boolean = false,
    val pokemonDetails: PokemonDetails? = null,
    val error: String = "",)