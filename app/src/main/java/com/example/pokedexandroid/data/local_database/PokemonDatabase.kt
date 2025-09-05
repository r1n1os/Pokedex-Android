package com.example.pokedexandroid.data.local_database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pokedexandroid.data.local_database.entities.StatsEntity.StatsDao
import com.example.pokedexandroid.data.local_database.entities.StatsEntity.StatsEntity
import com.example.pokedexandroid.data.local_database.entities.pokemon_entity.PokemonDao
import com.example.pokedexandroid.data.local_database.entities.pokemon_entity.PokemonEntity
import com.example.pokedexandroid.data.local_database.relationships.PokemonAndStatsCrossRef
import com.example.pokedexandroid.data.local_database.relationships.PokemonAndTypesCrossRef
import com.example.pokedexandroid.data.local_database.type_converters.StatsTypeConverter
import com.example.pokedexandroid.data.local_database.type_converters.TypesTypeConverter
import com.example.pokedexandroid.data.local_database.entities.types_entity.TypesDao
import com.example.pokedexandroid.data.local_database.entities.types_entity.TypesEntity

@Database(
    entities = [PokemonEntity::class, StatsEntity::class, TypesEntity::class, PokemonAndTypesCrossRef::class, PokemonAndStatsCrossRef::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(StatsTypeConverter::class, TypesTypeConverter::class)
abstract class PokemonDatabase : RoomDatabase() {
    abstract val pokemonDao: PokemonDao
    abstract val statsDao: StatsDao
    abstract val typeDao: TypesDao

    fun deleteAll() {
        clearAllTables()
    }
}