package com.example.pokedexandroid.ui.pokemon_details_screen

import com.example.pokedexandroid.data.local_database.pokemon_entity.PokemonEntity

data class PokemonDetailsState(
    val isLoading: Boolean = false,
    val pokemonEntity: PokemonEntity? = null,
    val error: String = "",)