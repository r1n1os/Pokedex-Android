package com.example.pokedexandroid.data.repository

import com.example.pokedexandroid.data.local_database.PokemonDatabase
import com.example.pokedexandroid.data.local_database.pokemon_entity.PokemonEntity
import com.example.pokedexandroid.data.local_database.relationships.PokemonAndStatsCrossRef
import com.example.pokedexandroid.data.local_database.relationships.PokemonAndTypesCrossRef
import com.example.pokedexandroid.data.remote.pokemon_details.PokemonDetailsApi
import com.example.pokedexandroid.data.remote.dto.PokemonDetailsDto
import com.example.pokedexandroid.domain.model.PokemonDetails
import com.example.pokedexandroid.domain.repository.PokemonDetailsRepository
import com.example.pokedexandroid.utils.Colors
import com.example.pokedexandroid.utils.Constants
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
        val pokemonDetails = PokemonDetails("", "${Constants.POKEMON_GIF_BASE_URL}${pokemonDetailsResponse.id}.gif", Colors.getTypeColor(""), emptyList(), emptyList())
        savePokemonDetailsIntoLocalDatabase(pokemonDetailsResponse)
        savePokemonStatsIntoLocalDatabase(pokemonDetailsResponse)
        savePokemonTypesIntoLocalDatabase(pokemonDetailsResponse)
        getPokemonWithTypes(pokemonDetailsResponse).collect { pokemonWithTypes ->
            pokemonDetails.name = pokemonWithTypes.pokemonEntity.pokemonName
            pokemonDetails.types = pokemonWithTypes.types.map { it.toType() }

            getPokemonWithStatsByPokemonName(pokemonName = pokemonDetailsResponse.name).collect { pokemonWithStats ->
                pokemonDetails.stats = pokemonWithStats.stats.map { it.toStats() }
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
        pokemonDatabase.statsDao.deleteStats()
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