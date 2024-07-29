package com.example.pokedexandroid.data.local_database.pojo

import androidx.room.Embedded
import androidx.room.Relation
import com.example.pokedexandroid.data.local_database.StatsEntity.StatsEntity
import com.example.pokedexandroid.data.local_database.pokemon_entity.PokemonEntity

data class PokemonWithStats(
    @Embedded val pokemon: PokemonEntity,
    @Relation(
        parentColumn = "name", // Column in PokemonEntity
        entityColumn = "pokemonEntityName" // Column in StatsEntity
    )
    val stats: List<StatsEntity>
)