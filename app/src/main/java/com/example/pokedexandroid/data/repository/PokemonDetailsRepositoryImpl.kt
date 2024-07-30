package com.example.pokedexandroid.data.repository

import android.util.Log
import com.example.pokedexandroid.data.local_database.PokemonDatabase
import com.example.pokedexandroid.data.local_database.pojo.PokemonWithStats
import com.example.pokedexandroid.data.local_database.pokemon_entity.PokemonEntity
import com.example.pokedexandroid.data.remote.pokemon_details.PokemonDetailsApi
import com.example.pokedexandroid.data.remote.dto.PokemonDetailsDto
import com.example.pokedexandroid.domain.model.PokemonDetails
import com.example.pokedexandroid.domain.repository.PokemonDetailsRepository
import com.example.pokedexandroid.utils.Resource
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PokemonDetailsRepositoryImpl @Inject constructor(
    private val pokemonDetailsApi: PokemonDetailsApi,
    private val pokemonDatabase: PokemonDatabase
) : PokemonDetailsRepository {

    override suspend fun executeRequestToGetPokemonDetails(pokemonDetailsUrl: String): Resource<PokemonDetails> {
        val pokemonDetailsResponse: PokemonDetailsDto =
            pokemonDetailsApi.getPokemonDetails(pokemonDetailsUrl)
        var pokemonDetails = PokemonDetails("", -1)
        var pokemonWithStats: List<PokemonWithStats>? = null

        savePokemonDetailsIntoLocalDatabase(pokemonDetailsResponse).collect { pokemonEntity ->
            pokemonDetails = pokemonEntity.toPokemonDetails()
        }
        Log.d("dfsdf","BEFORE")

        savePokemonStatsIntoLocalDatabase(pokemonDetailsResponse).collect {

        }
        getPokemonWithStatsByPokemonName(pokemonName = pokemonDetails.name).collect {
            pokemonWithStats = it
        }
        Log.d("dfsdf", pokemonWithStats.toString())
        return Resource.Success(
            data = pokemonDetails,
            nextUrl = null
        )
    }

    private suspend fun savePokemonDetailsIntoLocalDatabase(pokemonDetailsResponse: PokemonDetailsDto) =
        flow {
            val pokemonEntity: PokemonEntity = pokemonDetailsResponse.toPokemonEntity()
            pokemonDatabase.pokemonDao.updatePokemonEntityFromPokemonDetails(
                pokemonName = pokemonEntity.name,
                order = pokemonEntity.order,
            )
            emit(pokemonDatabase.pokemonDao.getPokemonById(pokemonName = pokemonEntity.name))
        }

    private suspend fun savePokemonStatsIntoLocalDatabase(pokemonDetailsResponse: PokemonDetailsDto) =
        flow {
            val pokemonEntity: PokemonEntity = pokemonDetailsResponse.toPokemonEntity()
            pokemonDetailsResponse.stats.forEach { statEntity ->
                pokemonDatabase.statsDao.insertStatsEntity(statsEntity = statEntity.toStatsEntity(pokemonName = pokemonEntity.name))
            }
            Log.d("dfsdf","WHILE")
            emit(pokemonDatabase.statsDao.getListOfStats())
        }

    private suspend fun getPokemonWithStatsByPokemonName(pokemonName: String) =
        flow {
            emit(pokemonDatabase.pokemonDao.getPokemonEntityWithItsStats(pokemonName = pokemonName))
        }
}