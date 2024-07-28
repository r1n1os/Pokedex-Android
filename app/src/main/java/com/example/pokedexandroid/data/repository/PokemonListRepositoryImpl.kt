package com.example.pokedexandroid.data.repository

import android.net.Uri
import com.example.pokedexandroid.data.local_database.PokemonDatabase
import com.example.pokedexandroid.data.local_database.pokemon_entity.PokemonEntity
import com.example.pokedexandroid.data.remote.dto_models.PokemonDto
import com.example.pokedexandroid.data.remote.pokemon_list.PokemonListApi
import com.example.pokedexandroid.data.remote.pokemon_list.PokemonListResponse
import com.example.pokedexandroid.domain.model.PokemonList
import com.example.pokedexandroid.domain.repository.PokemonListRepository
import com.example.pokedexandroid.utils.Constants
import com.example.pokedexandroid.utils.Resource
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PokemonListRepositoryImpl @Inject constructor(
    private val pokemonListApi: PokemonListApi,
    private val pokemonDatabase: PokemonDatabase
): PokemonListRepository {


    override suspend fun executeRequestToGetPokemonList(nextUrl: String?): Resource<MutableList<PokemonList>> {
        return try {
            var pokemonList: MutableList<PokemonList> = mutableListOf()
            val requestResponse: PokemonListResponse =
                if (nextUrl == null) pokemonListApi.getListOfPokemon() else pokemonListApi.getNextListOfPokemon(
                    nextUrl
                )
            savePokemonIntoLocalDatabase(requestResponse.results ?: mutableListOf())
            getPokemonFromLocalDatabase().collect { pokemonEntityList ->
                pokemonList = pokemonEntityList.map { it.toPokemonList() }.toMutableList()
            }
            Resource.Success(
                data = pokemonList,
                nextUrl = requestResponse.nextUrl
            )

        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "Could not complete request. Something went wrong!")
        }
    }

    /*    suspend fun executeRequestToGetPokemonList(nextUrl: String?): Resource<MutableList<PokemonDto>> {
        val pokemonListApiResponse: PokemonListResponse =
            if (nextUrl == null) pokemonListApi.getListOfPokemon() else pokemonListApi.getNextListOfPokemon(
                nextUrl
            )
        if (pokemonListApiResponse.results != null) {
            var pokemonEntityList: MutableList<PokemonEntity> = mutableListOf()
            savePokemonIntoLocalDatabase(pokemonListApiResponse.results).collect { retrievedPokemonEntityList ->
                pokemonEntityList = retrievedPokemonEntityList.toMutableList()
            }
            return Resource.Success(
                data = pokemonEntityList,
                nextUrl = pokemonListApiResponse.nextUrl
            )
        } else {
            return Resource.Error(message = "Something went wrong")
        }
    }*/

    private suspend fun savePokemonIntoLocalDatabase(pokemonEntityList: MutableList<PokemonDto>) =
        flow {
            pokemonEntityList.forEach { pokemonEntity ->
                pokemonDatabase.pokemonDao.insertPokemonEntity(pokemonEntity.toPokemonEntity())
            }
            emit(pokemonDatabase.pokemonDao.getListOfPokemon())
        }

    private suspend fun getPokemonFromLocalDatabase() =
        flow {
            emit(pokemonDatabase.pokemonDao.getListOfPokemon())
        }
}

