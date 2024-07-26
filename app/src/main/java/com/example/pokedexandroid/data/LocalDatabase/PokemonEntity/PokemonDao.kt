package com.example.pokedexandroid.data.LocalDatabase.PokemonEntity

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert

@Dao
interface PokemonDao {
    @Upsert
    suspend fun insertPokemonEntity(pokemonEntity: PokemonEntity)

    @Transaction
    @Query("SELECT * From pokemon")
    suspend fun getListOfPokemon(): List<PokemonEntity>

    @Transaction
    @Query("SELECT * From pokemon WHERE name=:pokemonName")
    suspend fun getPokemonById(pokemonName: Int): PokemonEntity

}