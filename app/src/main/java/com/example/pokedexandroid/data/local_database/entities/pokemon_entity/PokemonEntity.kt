package com.example.pokedexandroid.data.local_database.entities.pokemon_entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pokedexandroid.domain.model.PokemonList
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Pokemon")
data class PokemonEntity(
    @PrimaryKey
    val pokemonName: String,
    @SerializedName("url") val extraInfoUrl: String?,
    val order: Int,
    var photoUrl: String?,
) {
    fun toPokemonList(): PokemonList {
        return PokemonList(
            name = pokemonName,
            extraInfoUrl = extraInfoUrl ?: "",
            photoUrl = photoUrl ?: ""
        )
    }
}
