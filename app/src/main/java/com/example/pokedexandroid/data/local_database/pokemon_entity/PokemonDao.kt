package com.example.pokedexandroid.data.local_database.pokemon_entity

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.pokedexandroid.data.local_database.pojo.PokemonWithStats

@Dao
interface PokemonDao {
    @Upsert
    suspend fun insertPokemonEntity(pokemonEntity: PokemonEntity)

    @Transaction
    @Query("SELECT * From pokemon")
    suspend fun getListOfPokemon(): List<PokemonEntity>

    @Transaction
    @Query("SELECT * From pokemon WHERE name=:pokemonName")
    suspend fun getPokemonById(pokemonName: String): PokemonEntity

    /**
     * Updating only price
     * By order id
     */
    @Query("UPDATE pokemon SET `order`=:order WHERE name = :pokemonName")
    fun updatePokemonEntityFromPokemonDetails(pokemonName: String, order: Int)

    @Transaction
    @Query(
        "SELECT * FROM pokemon WHERE pokemon.name=:pokemonName" /*+
        "JOIN stats ON pokemon.name = stats.pokemonEntityName " +
        "WHERE pokemon.name=:pokemonName"*/
    )
    fun getPokemonEntityWithItsStats(pokemonName: String): PokemonWithStats
}