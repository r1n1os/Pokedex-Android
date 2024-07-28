package com.example.pokedexandroid.data.remote.pokemon_details

import com.example.pokedexandroid.data.remote.dto_models.PokemonDetailsDto
import retrofit2.http.GET
import retrofit2.http.Url

interface PokemonDetailsApi {
    @GET
    suspend fun getPokemonDetails(@Url url: String): PokemonDetailsDto
}