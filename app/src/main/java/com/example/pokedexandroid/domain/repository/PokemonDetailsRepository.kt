package com.example.pokedexandroid.domain.repository

import com.example.pokedexandroid.data.local_database.PokemonDatabase
import com.example.pokedexandroid.data.remote.pokemon_details.PokemonDetailsApi
import com.example.pokedexandroid.data.remote.dto_models.PokemonDetailsDto
import com.example.pokedexandroid.utils.Resource
import javax.inject.Inject

class PokemonDetailsRepository @Inject constructor(
    private val pokemonDetailsApi: PokemonDetailsApi,
    private val pokemonDatabase: PokemonDatabase
) {
    suspend fun executeRequestToGetPokemonDetails(pokemonDetailsUrl: String): Resource<PokemonDetailsDto> {
        val test: PokemonDetailsDto = pokemonDetailsApi.getPokemonDetails(pokemonDetailsUrl)
        return Resource.Success(
            data = test,
            nextUrl = null
        )
    }
}