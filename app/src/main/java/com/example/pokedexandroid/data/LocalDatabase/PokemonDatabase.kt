package com.example.pokedexandroid.data.LocalDatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pokedexandroid.data.LocalDatabase.PokemonEntity.PokemonDao
import com.example.pokedexandroid.data.LocalDatabase.PokemonEntity.PokemonEntity

@Database(
    entities = [PokemonEntity::class,],
    version = 1,
    exportSchema = false
)

abstract class PokemonDatabase : RoomDatabase() {
    abstract val pokemonDao: PokemonDao
    fun deleteAll() {
        clearAllTables()
    }
}