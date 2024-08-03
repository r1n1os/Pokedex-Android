package com.example.pokedexandroid.data.remote.dto

import com.example.pokedexandroid.data.local_database.pokemon_entity.PokemonEntity
import com.google.gson.annotations.SerializedName

data class PokemonDetailsDto(
    val id: Int,
    val name: String,
    val order: Int,
    @SerializedName("stats")
    val stats: List<StatsDto>,
    @SerializedName("types")
    val types: List<TypesDto>
) {
    fun toPokemonEntity(): PokemonEntity {
        return PokemonEntity(
            pokemonName = name,
            order = order,
            extraInfoUrl = "",
            photoUrl = "",
        )
    }
}
