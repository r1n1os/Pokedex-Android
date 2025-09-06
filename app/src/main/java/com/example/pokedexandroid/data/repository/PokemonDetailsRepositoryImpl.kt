package com.example.pokedexandroid.data.repository

import com.example.pokedexandroid.data.local_database.PokemonDatabase
import com.example.pokedexandroid.data.local_database.entities.pokemon_entity.PokemonEntity
import com.example.pokedexandroid.data.local_database.relationships.PokemonAndStatsCrossRef
import com.example.pokedexandroid.data.local_database.relationships.PokemonAndTypesCrossRef
import com.example.pokedexandroid.data.mappers.toPokemonDetailsModel
import com.example.pokedexandroid.data.remote.dto.PokemonDetailsDto
import com.example.pokedexandroid.data.remote.pokemon_details.PokemonDetailsApi
import com.example.pokedexandroid.domain.model.PokemonDetailsModel
import com.example.pokedexandroid.domain.repository.PokemonDetailsRepository
import com.example.pokedexandroid.utils.Resource
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PokemonDetailsRepositoryImpl @Inject constructor(
    private val pokemonDetailsApi: PokemonDetailsApi,
    private val pokemonDatabase: PokemonDatabase
) : PokemonDetailsRepository {

    override suspend fun executeRequestToGetPokemonDetails(pokemonDetailsUrl: String): Resource<PokemonDetailsModel> {
        val pokemonDetailsResponse: PokemonDetailsDto =
            pokemonDetailsApi.getPokemonDetails(pokemonDetailsUrl)
        var pokemonDetails = PokemonDetailsModel()

        savePokemonDetailsIntoLocalDatabase(pokemonDetailsResponse)
        savePokemonStatsIntoLocalDatabase(pokemonDetailsResponse)
        savePokemonTypesIntoLocalDatabase(pokemonDetailsResponse)

        getAllPokemonDetails(pokemonDetailsResponse).collect { pokemonWithStatsAndTypes ->
           pokemonDetails = pokemonWithStatsAndTypes.toPokemonDetailsModel()
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

    private fun getAllPokemonDetails(pokemonDetailsResponse: PokemonDetailsDto) =
        flow {
            emit(pokemonDatabase.pokemonDao.getAllPokemonDetails(pokemonName = pokemonDetailsResponse.name))
        }
}