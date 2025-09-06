package com.example.pokedexandroid.data.mappers

import androidx.compose.ui.graphics.Color
import com.example.pokedexandroid.data.local_database.entities.StatsEntity.StatsEntity
import com.example.pokedexandroid.data.local_database.entities.pokemon_entity.PokemonEntity
import com.example.pokedexandroid.data.local_database.entities.types_entity.TypesEntity
import com.example.pokedexandroid.data.local_database.pojo.PokemonWithStatsAndTypes
import com.example.pokedexandroid.domain.model.PokemonDetailsModel
import com.example.pokedexandroid.domain.model.PokemonListModel
import com.example.pokedexandroid.domain.model.StatsModel
import com.example.pokedexandroid.domain.model.TypeModel
import com.example.pokedexandroid.utils.Colors

fun PokemonEntity.toPokemonListModel(): PokemonListModel {
    return PokemonListModel(
        name = this.pokemonName,
        photoUrl = this.photoUrl ?: "",
        extraInfoUrl = this.extraInfoUrl ?: ""
    )
}

fun PokemonWithStatsAndTypes.toPokemonDetailsModel(): PokemonDetailsModel {
    val pokemonColor: Color =  Colors.getTypeColor(this.types.first().toTypeModel().name)

    return PokemonDetailsModel(
        name = this.pokemon.pokemonName,
        photoUrl = this.pokemon.photoUrl ?: "",
        color = pokemonColor,
        stats = this.stats.map { it.toStatsModel() },
        types = this.types.map { it.toTypeModel() },
    )
}

fun TypesEntity.toTypeModel(): TypeModel {
    return TypeModel(
        name = this.typeName,
        url = this.url
    )
}

fun StatsEntity.toStatsModel(): StatsModel {
    return StatsModel(
        name = this.statName,
        value = this.value
    )
}