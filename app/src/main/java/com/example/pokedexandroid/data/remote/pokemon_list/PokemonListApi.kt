package com.example.pokedexandroid.data.remote.pokemon_list

import retrofit2.http.GET
import retrofit2.http.Url

interface PokemonListApi {

    @GET("pokemon/")
    suspend fun getListOfPokemon(): PokemonListResponse

    @GET
    suspend fun getNextListOfPokemon(@Url url: String): PokemonListResponse
}