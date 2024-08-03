package com.example.pokedexandroid.data.local_database.pojo

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.pokedexandroid.data.local_database.pokemon_entity.PokemonEntity
import com.example.pokedexandroid.data.local_database.relationships.PokemonAndTypesCrossRef
import com.example.pokedexandroid.data.local_database.types_entity.TypesEntity

data class PokemonWithTypes(
    @Embedded val pokemonEntity: PokemonEntity,
    @Relation(
        parentColumn = "pokemonName",
        entityColumn = "typeName",
        associateBy = Junction(PokemonAndTypesCrossRef::class)
    )
    val types: List<TypesEntity>
)
