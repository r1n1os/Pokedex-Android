package com.example.pokedexandroid.data.remote.PokemonList

import retrofit2.http.GET
import retrofit2.http.Url

interface PokemonListApi {

    @GET("pokemon/")
    suspend fun getListOfPokemon(): PokemonListResponse

    @GET
    suspend fun getNextListOfPokemon(@Url url: String): PokemonListResponse
}