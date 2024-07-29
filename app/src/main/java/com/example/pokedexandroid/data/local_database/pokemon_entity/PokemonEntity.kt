package com.example.pokedexandroid.data.local_database.pokemon_entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pokedexandroid.domain.model.PokemonDetails
import com.example.pokedexandroid.domain.model.PokemonList
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Pokemon")
data class PokemonEntity(
    @PrimaryKey
    val name: String,
    @SerializedName("url") val extraInfoUrl: String?,
    val order: Int,
    var photoUrl: String?
) {
    fun toPokemonList(): PokemonList {
        return PokemonList(
            name = name,
            extraInfoUrl = extraInfoUrl ?: "",
            photoUrl = photoUrl ?: ""
        )
    }

    fun toPokemonDetails(): PokemonDetails {
        return PokemonDetails(
            name = name,
            order = order,
        )
    }
}
