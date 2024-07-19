package com.example.pokedexandroid.domain.repository

import com.example.pokedexandroid.data.LocalDatabase.PokemonDatabase
import com.example.pokedexandroid.data.LocalDatabase.PokemonEntity.PokemonEntity
import com.example.pokedexandroid.data.remote.PokemonList.PokemonListApi
import com.example.pokedexandroid.data.remote.PokemonList.PokemonListResponse
import com.example.pokedexandroid.utils.Resource
import javax.inject.Inject

class PokemonListRepository @Inject constructor(
    private val pokemonListApi: PokemonListApi,
    private val pokemonDatabase: PokemonDatabase
) {

    suspend fun executeRequestToGetPokemonList(): Resource<MutableList<PokemonEntity>> {
        val pokemonListApiResponse: PokemonListResponse = pokemonListApi.getListOfPokemon()
        return Resource.Success(data = pokemonListApiResponse.results)
/*        if(pokemonEntityList)
        return try {
            val pokemonService =
                if (url == null) pokemonService.getPokemon() else pokemonService.getPokemon(url)
            val nextUrl = pokemonService.nextUrl
            val pokemonEntityList = pokemonService.results
            savePokemon(pokemonEntityList = pokemonEntityList)

            Resource.Success(data = myRoomDatabase.pokemonDao().getAllPokemon(), nextUrl = nextUrl)
        } catch (e: Exception) {
            Resource.Error(message = handlingError.handleErrorMessage(e))
        }*/
    }
}