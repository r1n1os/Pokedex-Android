package com.example.pokedexandroid.domain.repository

import com.example.pokedexandroid.domain.model.PokemonList
import com.example.pokedexandroid.utils.Resource

interface PokemonListRepository {
    suspend fun executeRequestToGetPokemonList(nextUrl: String?): Resource<MutableList<PokemonList>>
}