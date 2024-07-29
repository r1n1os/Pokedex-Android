package com.example.pokedexandroid.data.remote.dto

import android.net.Uri
import com.example.pokedexandroid.data.local_database.pokemon_entity.PokemonEntity
import com.example.pokedexandroid.utils.Constants
import com.google.gson.annotations.SerializedName

data class PokemonDto(
    val name: String,
    @SerializedName("url") val extraInfoUrl: String?,
    val pokemonDetailsDto: PokemonDetailsDto
) {
    fun toPokemonEntity(): PokemonEntity {
        return PokemonEntity(
            name = name,
            extraInfoUrl = extraInfoUrl,
            order = -1,
            photoUrl =  getPokemonImageUrl(extraInfoUrl ?: "")
        )
    }

    private fun getPokemonImageUrl(extraInfoUrl: String): String {
        val pokemonUri: Uri = Uri.parse(extraInfoUrl ?: "")
        val pathSegments: List<String> = pokemonUri.pathSegments;
        val pokemonId: String = pathSegments[pathSegments.size - 1];
        return "${Constants.POKEMON_IMAGE_BASE_URL}$pokemonId.png"
    }
}
