package com.example.pokedexandroid.data.repository

import com.example.pokedexandroid.data.local_database.PokemonDatabase
import com.example.pokedexandroid.data.mappers.toPokemonListModel
import com.example.pokedexandroid.data.remote.dto.PokemonDto
import com.example.pokedexandroid.data.remote.pokemon_list.PokemonListApi
import com.example.pokedexandroid.data.remote.pokemon_list.PokemonListResponse
import com.example.pokedexandroid.domain.model.PokemonListModel
import com.example.pokedexandroid.domain.repository.PokemonListRepository
import com.example.pokedexandroid.utils.Resource
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PokemonListRepositoryImpl @Inject constructor(
    private val pokemonListApi: PokemonListApi,
    private val pokemonDatabase: PokemonDatabase
) : PokemonListRepository {


    override suspend fun executeRequestToGetPokemonList(nextUrl: String?): Resource<MutableList<PokemonListModel>> {
        return try {
            var pokemonList: MutableList<PokemonListModel> = mutableListOf()
            val requestResponse: PokemonListResponse =
                if (nextUrl == null) pokemonListApi.getListOfPokemon() else pokemonListApi.getNextListOfPokemon(
                    nextUrl
                )
            savePokemonIntoLocalDatabase(requestResponse.results ?: mutableListOf()).collect {
                getPokemonFromLocalDatabase().collect { pokemonEntityList ->
                    pokemonList = pokemonEntityList.map { it.toPokemonListModel() }.toMutableList()
                }
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

    private fun savePokemonIntoLocalDatabase(pokemonEntityList: MutableList<PokemonDto>) =
        flow {
            pokemonEntityList.forEach { pokemonEntity ->
                pokemonDatabase.pokemonDao.insertPokemonEntity(pokemonEntity.toPokemonEntity())
            }
            emit(pokemonDatabase.pokemonDao.getListOfPokemon())
        }

    private fun getPokemonFromLocalDatabase() =
        flow {
            emit(pokemonDatabase.pokemonDao.getListOfPokemon())
        }
}

