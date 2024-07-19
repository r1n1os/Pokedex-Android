package com.example.pokedexandroid.data.LocalDatabase.PokemonEntity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Pokemon")
data class PokemonEntity(
    @PrimaryKey
    val id: Int,
    val name: String?,
    val extraInfoUrl: String?,
    val order: Int,
    val photoUrl: String?
)
