package com.example.pokedexandroid.data.remote.PokemonList

import retrofit2.http.GET

interface PokemonListApi {

    @GET("pokemon/")
    suspend fun getListOfPokemon(): PokemonListResponse
}