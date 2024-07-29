package com.example.pokedexandroid.data.local_database.StatsEntity

import androidx.room.Entity
import androidx.room.MapColumn
import androidx.room.PrimaryKey

@Entity(tableName = "Stats")
data class StatsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val value: Int,
    val pokemonEntityName: String //This is the primary key of pokemonEntity
)
