package com.example.pokedexandroid.data.repository

import android.util.Log
import com.example.pokedexandroid.data.local_database.PokemonDatabase
import com.example.pokedexandroid.data.local_database.pokemon_entity.PokemonEntity
import com.example.pokedexandroid.data.local_database.relationships.PokemonAndStatsCrossRef
import com.example.pokedexandroid.data.local_database.relationships.PokemonAndTypesCrossRef
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
        getPokemonWithTypes(pokemonDetailsResponse).collect {
            it.forEach { t ->
                Log.d("Test", "Pokemon: ${t.pokemonEntity.pokemonName} ")

                t.types.forEach { type ->
                    Log.d("Test", "Type: ${type.typeName}")
                }
            }
        }
        getPokemonWithStatsByPokemonName(pokemonName = pokemonDetailsResponse.name).collect { pokemonWithStats ->
            pokemonWithStats.forEach { t ->
                Log.d("Test", "Pokemon: ${t.pokemon.pokemonName} ")

                t.stats.forEach { stat ->
                    Log.d("Test", "Type: ${stat.statName}")
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
            pokemonName = pokemonEntity.pokemonName,
            order = pokemonEntity.order,
        )
    }


    private suspend fun savePokemonStatsIntoLocalDatabase(pokemonDetailsResponse: PokemonDetailsDto) {
        val pokemonEntity: PokemonEntity = pokemonDetailsResponse.toPokemonEntity()
        pokemonDetailsResponse.stats.forEach { statEntity ->
            pokemonDatabase.statsDao.insertStatsEntity(
                statsEntity = statEntity.toStatsEntity(
                    pokemonName = pokemonEntity.pokemonName
                )
            )
            pokemonDatabase.pokemonDao.insertPokemonAndStatsCrossRef(
                test = PokemonAndStatsCrossRef(
                    pokemonName = pokemonEntity.pokemonName,
                    statName = statEntity.stat.name
                )
            )
        }
    }

    private suspend fun savePokemonTypesIntoLocalDatabase(pokemonDetailsResponse: PokemonDetailsDto) {
        pokemonDetailsResponse.types.forEach { types ->
            pokemonDatabase.typeDao.insertTypeEntity(
                typeEntity = types.typeDetails.toTypeEntity(pokemonName = pokemonDetailsResponse.name)
            )
            pokemonDatabase.pokemonDao.insertPokemonAndTypesCrossRef(PokemonAndTypesCrossRef(pokemonName = pokemonDetailsResponse.name, typeName = types.typeDetails.name))
        }
    }

    private suspend fun getPokemonWithStatsByPokemonName(pokemonName: String) =
        flow {
            val pokemonWithStats =
                pokemonDatabase.pokemonDao.getPokemonEntityWithItsStats(pokemonName = pokemonName)
            emit(pokemonWithStats)
        }

    private suspend fun getPokemonWithTypes(pokemonDetailsResponse: PokemonDetailsDto) =
        flow {
            emit(pokemonDatabase.pokemonDao.getPokemonWithTypes(pokemonName = pokemonDetailsResponse.name))
        }
}