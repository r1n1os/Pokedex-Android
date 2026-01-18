package com.example.pokedexandroid.navigations.routes

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data class PokemonDetailsRoute(
    val pokemonDetailsUrl: String?,
): NavKey