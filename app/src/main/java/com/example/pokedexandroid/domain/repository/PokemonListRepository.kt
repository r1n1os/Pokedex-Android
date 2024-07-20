package com.example.pokedexandroid.domain.repository

import android.net.Uri
import com.example.pokedexandroid.data.LocalDatabase.PokemonDatabase
import com.example.pokedexandroid.data.LocalDatabase.PokemonEntity.PokemonEntity
import com.example.pokedexandroid.data.remote.PokemonList.PokemonListApi
import com.example.pokedexandroid.data.remote.PokemonList.PokemonListResponse
import com.example.pokedexandroid.utils.Constants
import com.example.pokedexandroid.utils.Resource
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PokemonListRepository @Inject constructor(
    private val pokemonListApi: PokemonListApi,
    private val pokemonDatabase: PokemonDatabase
) {

    suspend fun executeRequestToGetPokemonList(): Resource<MutableList<PokemonEntity>> {
        val pokemonListApiResponse: PokemonListResponse = pokemonListApi.getListOfPokemon()
        if (pokemonListApiResponse.results != null) {
            var pokemonEntityList:MutableList<PokemonEntity> = mutableListOf()
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
    }

    private suspend fun savePokemonIntoLocalDatabase(pokemonEntityList: MutableList<PokemonEntity>) =
        flow {
            pokemonEntityList.forEach { pokemonEntity ->
                pokemonEntity.photoUrl = getPokemonImageUrl(pokemonEntity.extraInfoUrl ?: "")
                pokemonDatabase.pokemonDao.insertPokemonEntity(pokemonEntity)
            }
            emit(pokemonDatabase.pokemonDao.getListOfPokemon())
        }

    private  fun getPokemonImageUrl(extraInfoUrl: String): String {
        val pokemonUri: Uri =  Uri.parse(extraInfoUrl ?: "")
        val pathSegments: List<String> = pokemonUri.pathSegments;
        val pokemonId: String = pathSegments[pathSegments.size - 1];
        return "${Constants.POKEMON_IMAGE_BASE_URL}$pokemonId.png"
    }
}

