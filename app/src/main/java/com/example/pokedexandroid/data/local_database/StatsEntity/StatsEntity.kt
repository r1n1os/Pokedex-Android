package com.example.pokedexandroid.data.local_database.StatsEntity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StatsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val value: Int
)
