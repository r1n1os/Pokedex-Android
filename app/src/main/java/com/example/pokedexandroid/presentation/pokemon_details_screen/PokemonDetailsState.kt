package com.example.pokedexandroid.presentation.pokemon_details_screen

import com.example.pokedexandroid.domain.model.PokemonDetailsModel

data class PokemonDetailsState(
    val isLoading: Boolean = false,
    val pokemonDetails: PokemonDetailsModel? = null,
    val error: String = "",)