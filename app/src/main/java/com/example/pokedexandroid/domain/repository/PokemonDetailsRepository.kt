package com.example.pokedexandroid.domain.repository

import com.example.pokedexandroid.domain.model.PokemonDetails
import com.example.pokedexandroid.utils.Resource

interface PokemonDetailsRepository {
    suspend fun executeRequestToGetPokemonDetails(pokemonDetailsUrl: String): Resource<PokemonDetails>
}