package com.example.pokedexandroid.data.remote.PokemonDetails

import com.example.pokedexandroid.data.LocalDatabase.PokemonEntity.PokemonEntity
import retrofit2.http.GET
import retrofit2.http.Url

interface PokemonDetailsApi {
    @GET
    suspend fun getPokemonDetails(@Url url: String): PokemonEntity
}