package com.example.pokedexandroid.data.local_database.types_entity

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.pokedexandroid.data.local_database.relationships.PokemonAndTypesCrossRef

@Dao
interface TypesDao {
    @Upsert
    suspend fun insertTypeEntity(typeEntity: TypesEntity)

    @Transaction
    @Query("SELECT * From types")
    suspend fun getListOfTypes(): List<TypesEntity>
}