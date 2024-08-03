package com.example.pokedexandroid.data.local_database.relationships

import androidx.room.Entity

@Entity(primaryKeys = ["pokemonName", "statName"])
data class PokemonAndStatsCrossRef(
    val pokemonName: String,
    val statName: String
)
