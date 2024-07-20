package com.example.pokedexandroid.data.LocalDatabase.PokemonEntity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Pokemon")
data class PokemonEntity(
    @PrimaryKey
    val name: String,
    @SerializedName("url") val extraInfoUrl: String?,
    val order: Int,
    var photoUrl: String?
)
