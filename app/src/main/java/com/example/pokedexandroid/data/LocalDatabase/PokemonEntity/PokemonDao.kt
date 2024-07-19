package com.example.pokedexandroid.data.LocalDatabase.PokemonEntity

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListOfPokemon(pokemonEntityList: List<PokemonEntity>)



    @Transaction
    @Query("SELECT * From pokemon")
    suspend fun getListOfPokemon(): List<PokemonEntity>

    @Transaction
    @Query("SELECT * From pokemon WHERE id=:pokemonId")
    suspend fun getPokemonById(pokemonId: Int): PokemonEntity

}