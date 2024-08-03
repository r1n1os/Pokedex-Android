package com.example.pokedexandroid.data.local_database.pokemon_entity

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.pokedexandroid.data.local_database.pojo.PokemonWithStats
import com.example.pokedexandroid.data.local_database.pojo.PokemonWithTypes
import com.example.pokedexandroid.data.local_database.relationships.PokemonAndTypesCrossRef

@Dao
interface PokemonDao {
    @Upsert
    suspend fun insertPokemonEntity(pokemonEntity: PokemonEntity)

    @Transaction
    @Query("SELECT * From pokemon")
    suspend fun getListOfPokemon(): List<PokemonEntity>

    @Transaction
    @Query("SELECT * From pokemon WHERE pokemonName=:pokemonName")
    suspend fun getPokemonById(pokemonName: String): PokemonEntity

    /**
     * Updating only price
     * By order id
     */
    @Query("UPDATE pokemon SET `order`=:order WHERE pokemonName = :pokemonName")
    fun updatePokemonEntityFromPokemonDetails(pokemonName: String, order: Int)

    @Transaction
    @Query(
        "SELECT * FROM pokemon WHERE pokemon.pokemonName=:pokemonName" /*+
        "JOIN stats ON pokemon.name = stats.pokemonEntityName " +
        "WHERE pokemon.name=:pokemonName"*/
    )
    fun getPokemonEntityWithItsStats(pokemonName: String): PokemonWithStats

    @Transaction
    @Query("SELECT * FROM pokemon WHERE pokemon.pokemonName=:pokemonName")
    fun getPokemonWithTypes(pokemonName: String): List<PokemonWithTypes>

    @Upsert
    suspend fun insertPokemonAndTypesCrossRef(test: PokemonAndTypesCrossRef)

}