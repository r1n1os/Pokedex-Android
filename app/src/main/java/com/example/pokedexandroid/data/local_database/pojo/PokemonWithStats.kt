package com.example.pokedexandroid.data.local_database.pojo

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.pokedexandroid.data.local_database.StatsEntity.StatsEntity
import com.example.pokedexandroid.data.local_database.pokemon_entity.PokemonEntity
import com.example.pokedexandroid.data.local_database.relationships.PokemonAndStatsCrossRef

data class PokemonWithStats(
    @Embedded val pokemon: PokemonEntity,
    @Relation(
        parentColumn = "pokemonName",
        entityColumn = "statName",
        associateBy = Junction(PokemonAndStatsCrossRef::class)
    )
    val stats: List<StatsEntity>
)