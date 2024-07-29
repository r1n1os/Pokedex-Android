package com.example.pokedexandroid.data.local_database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pokedexandroid.data.local_database.StatsEntity.StatsDao
import com.example.pokedexandroid.data.local_database.StatsEntity.StatsEntity
import com.example.pokedexandroid.data.local_database.pokemon_entity.PokemonDao
import com.example.pokedexandroid.data.local_database.pokemon_entity.PokemonEntity
import com.example.pokedexandroid.data.local_database.type_converters.StatsTypeConverter

@Database(
    entities = [PokemonEntity::class, StatsEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(StatsTypeConverter::class)
abstract class PokemonDatabase : RoomDatabase() {
    abstract val pokemonDao: PokemonDao
    abstract val statsDao: StatsDao

    fun deleteAll() {
        clearAllTables()
    }
}