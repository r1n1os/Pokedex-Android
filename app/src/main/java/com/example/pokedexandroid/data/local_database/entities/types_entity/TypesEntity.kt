package com.example.pokedexandroid.data.local_database.entities.types_entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pokedexandroid.domain.model.TypeModel

@Entity(tableName = "Types")
data class TypesEntity(
    @PrimaryKey
    val typeName: String,
    val url: String,
    val pokemonEntityName: String //This is the primary key of pokemonEntity
)
