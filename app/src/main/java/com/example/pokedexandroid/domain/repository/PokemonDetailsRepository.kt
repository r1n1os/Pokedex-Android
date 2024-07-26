package com.example.pokedexandroid.domain.repository

import com.example.pokedexandroid.data.LocalDatabase.PokemonDatabase
import com.example.pokedexandroid.data.LocalDatabase.PokemonEntity.PokemonEntity
import com.example.pokedexandroid.data.remote.PokemonDetails.PokemonDetailsApi
import com.example.pokedexandroid.utils.Resource
import javax.inject.Inject

class PokemonDetailsRepository @Inject constructor(
    private val pokemonDetailsApi: PokemonDetailsApi,
    private val pokemonDatabase: PokemonDatabase
) {
    suspend fun executeRequestToGetPokemonDetails(pokemonDetailsUrl: String): Resource<PokemonEntity> {
        val test = pokemonDetailsApi.getPokemonDetails(pokemonDetailsUrl)
        return Resource.Success(
            data = test,
            nextUrl = null
        )
    }
}