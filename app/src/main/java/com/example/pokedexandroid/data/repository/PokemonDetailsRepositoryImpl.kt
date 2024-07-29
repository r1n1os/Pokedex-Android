package com.example.pokedexandroid.data.repository

import android.util.Log
import com.example.pokedexandroid.data.local_database.PokemonDatabase
import com.example.pokedexandroid.data.local_database.pokemon_entity.PokemonEntity
import com.example.pokedexandroid.data.remote.pokemon_details.PokemonDetailsApi
import com.example.pokedexandroid.data.remote.dto.PokemonDetailsDto
import com.example.pokedexandroid.domain.model.PokemonDetails
import com.example.pokedexandroid.domain.model.PokemonList
import com.example.pokedexandroid.domain.repository.PokemonDetailsRepository
import com.example.pokedexandroid.utils.Resource
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PokemonDetailsRepositoryImpl @Inject constructor(
    private val pokemonDetailsApi: PokemonDetailsApi,
    private val pokemonDatabase: PokemonDatabase
): PokemonDetailsRepository {

    override suspend fun executeRequestToGetPokemonDetails(pokemonDetailsUrl: String): Resource<PokemonDetails> {
        val pokemonDetailsResponse: PokemonDetailsDto = pokemonDetailsApi.getPokemonDetails(pokemonDetailsUrl)
        var pokemonDetails = PokemonDetails("", -1)

        savePokemonDetailsIntoLocalDatabase(pokemonDetailsResponse).collect { pokemonEntity ->
            pokemonDetails = pokemonEntity.toPokemonDetails()
            val test = pokemonDatabase.pokemonDao.loadPokemonEntityWithItsStats(pokemonDetailsResponse.name)
            Log.d("dfsdf", test.toString())
        }

        return Resource.Success(
            data = pokemonDetails,
            nextUrl = null
        )
    }

    private fun savePokemonDetailsIntoLocalDatabase(pokemonDetailsResponse: PokemonDetailsDto) =
        flow {
            val pokemonEntity: PokemonEntity = pokemonDetailsResponse.toPokemonEntity()
                pokemonDatabase.pokemonDao.updatePokemonEntityFromPokemonDetails(
                    pokemonName = pokemonEntity.name,
                    order = pokemonEntity.order
                )
            emit(pokemonDatabase.pokemonDao.getPokemonById(pokemonName = pokemonEntity.name))
        }

    private suspend fun getPokemonFromLocalDatabaseById(pokemonName: String) =
        flow {
            emit(pokemonDatabase.pokemonDao.getPokemonById(pokemonName = pokemonName))
        }
}