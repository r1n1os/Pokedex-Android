package com.example.pokedexandroid.data.local_database.pojo

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.pokedexandroid.data.local_database.entities.StatsEntity.StatsEntity
import com.example.pokedexandroid.data.local_database.entities.pokemon_entity.PokemonEntity
import com.example.pokedexandroid.data.local_database.entities.types_entity.TypesEntity
import com.example.pokedexandroid.data.local_database.relationships.PokemonAndStatsCrossRef
import com.example.pokedexandroid.data.local_database.relationships.PokemonAndTypesCrossRef

data class PokemonWithStatsAndTypes(
    @Embedded val pokemon: PokemonEntity,
    @Relation(
        parentColumn = "pokemonName",
        entityColumn = "statName",
        associateBy = Junction(PokemonAndStatsCrossRef::class)
    )
    val stats: List<StatsEntity>,
    @Relation(
        parentColumn = "pokemonName",
        entityColumn = "typeName",
        associateBy = Junction(PokemonAndTypesCrossRef::class)
    )
    val types: List<TypesEntity>

)
