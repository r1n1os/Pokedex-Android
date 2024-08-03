package com.example.pokedexandroid.data.local_database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pokedexandroid.data.local_database.StatsEntity.StatsDao
import com.example.pokedexandroid.data.local_database.StatsEntity.StatsEntity
import com.example.pokedexandroid.data.local_database.pokemon_entity.PokemonDao
import com.example.pokedexandroid.data.local_database.pokemon_entity.PokemonEntity
import com.example.pokedexandroid.data.local_database.type_converters.StatsTypeConverter
import com.example.pokedexandroid.data.local_database.types_entity.TypesDao
import com.example.pokedexandroid.data.local_database.types_entity.TypesEntity

@Database(
    entities = [PokemonEntity::class, StatsEntity::class, TypesEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(StatsTypeConverter::class)
abstract class PokemonDatabase : RoomDatabase() {
    abstract val pokemonDao: PokemonDao
    abstract val statsDao: StatsDao
    abstract val typeDao: TypesDao

    fun deleteAll() {
        clearAllTables()
    }
}