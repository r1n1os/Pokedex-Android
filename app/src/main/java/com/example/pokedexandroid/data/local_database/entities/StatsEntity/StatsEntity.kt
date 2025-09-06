package com.example.pokedexandroid.data.local_database.entities.StatsEntity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Stats")
data class StatsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val statName: String,
    val value: Int,
    val pokemonEntityName: String //This is the primary key of pokemonEntity
)
