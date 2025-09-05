package com.example.pokedexandroid.data.local_database.entities.pokemon_entity

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.pokedexandroid.data.local_database.pojo.PokemonWithStatsAndTypes
import com.example.pokedexandroid.data.local_database.relationships.PokemonAndStatsCrossRef
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

    @Transaction
    @Query("DELETE From pokemon WHERE pokemonName=:pokemonName")
    suspend fun deletePokemonById(pokemonName: String)

    /**
     * Updating only price
     * By order id
     */
    @Query("UPDATE pokemon SET `order`=:order WHERE pokemonName = :pokemonName")
    suspend fun updatePokemonEntityFromPokemonDetails(pokemonName: String, order: Int)

    @Transaction
    @Query("SELECT * FROM pokemon WHERE pokemon.pokemonName=:pokemonName")
    suspend fun getAllPokemonDetails(pokemonName: String): PokemonWithStatsAndTypes

    @Upsert
    suspend fun insertPokemonAndStatsCrossRef(test: PokemonAndStatsCrossRef)

    @Upsert
    suspend fun insertPokemonAndTypesCrossRef(test: PokemonAndTypesCrossRef)

}