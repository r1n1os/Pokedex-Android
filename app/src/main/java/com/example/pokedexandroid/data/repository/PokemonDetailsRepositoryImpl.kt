package com.example.pokedexandroid.data.repository

import android.util.Log
import com.example.pokedexandroid.data.local_database.PokemonDatabase
import com.example.pokedexandroid.data.local_database.pokemon_entity.PokemonEntity
import com.example.pokedexandroid.data.remote.pokemon_details.PokemonDetailsApi
import com.example.pokedexandroid.data.remote.dto.PokemonDetailsDto
import com.example.pokedexandroid.domain.model.PokemonDetails
import com.example.pokedexandroid.domain.repository.PokemonDetailsRepository
import com.example.pokedexandroid.utils.Colors
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
        val pokemonDetails = PokemonDetails("", Colors.getTypeColor(""), emptyList())
        savePokemonDetailsIntoLocalDatabase(pokemonDetailsResponse)
        savePokemonStatsIntoLocalDatabase(pokemonDetailsResponse)
        savePokemonTypesIntoLocalDatabase(pokemonDetailsResponse)
        getPokemonWithStatsByPokemonName(pokemonName = pokemonDetailsResponse.name).collect { pokemonWithStats ->
            if (pokemonWithStats.pokemon != null) {
                pokemonDetails.apply {
                    name = pokemonWithStats.pokemon.name
                    stats = pokemonWithStats.stats.map { it.toStats() }
                }
            }
        }

        return Resource.Success(
            data = pokemonDetails,
            nextUrl = null
        )
    }

    private suspend fun savePokemonDetailsIntoLocalDatabase(pokemonDetailsResponse: PokemonDetailsDto) {
        val pokemonEntity: PokemonEntity = pokemonDetailsResponse.toPokemonEntity()
        pokemonDatabase.pokemonDao.updatePokemonEntityFromPokemonDetails(
            pokemonName = pokemonEntity.name,
            order = pokemonEntity.order,
        )
    }


    private suspend fun savePokemonStatsIntoLocalDatabase(pokemonDetailsResponse: PokemonDetailsDto) {
        val pokemonEntity: PokemonEntity = pokemonDetailsResponse.toPokemonEntity()
        pokemonDetailsResponse.stats.forEach { statEntity ->
            pokemonDatabase.statsDao.insertStatsEntity(
                statsEntity = statEntity.toStatsEntity(
                    pokemonName = pokemonEntity.name
                )
            )
        }
    }

    private suspend fun savePokemonTypesIntoLocalDatabase(pokemonDetailsResponse: PokemonDetailsDto) {
        pokemonDetailsResponse.types.forEach { types ->
            pokemonDatabase.typeDao.insertTypeEntity(
                typeEntity = types.typeDetails.toTypeEntity()
            )
        }
    }

    private suspend fun getPokemonWithStatsByPokemonName(pokemonName: String) =
        flow {
            val pokemonWithStats =
                pokemonDatabase.pokemonDao.getPokemonEntityWithItsStats(pokemonName = pokemonName)
            emit(pokemonWithStats)
        }
}