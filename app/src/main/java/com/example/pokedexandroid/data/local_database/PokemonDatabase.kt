package com.example.pokedexandroid.data.local_database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pokedexandroid.data.local_database.pokemon_entity.PokemonDao
import com.example.pokedexandroid.data.local_database.pokemon_entity.PokemonEntity

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