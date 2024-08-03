package com.example.pokedexandroid.data.local_database.relationships

import androidx.room.Entity

@Entity(primaryKeys = ["pokemonName", "typeName"])
data class PokemonAndTypesCrossRef(
    val pokemonName: String,
    val typeName: String
)
