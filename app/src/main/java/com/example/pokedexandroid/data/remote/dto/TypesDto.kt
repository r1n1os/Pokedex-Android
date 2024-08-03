package com.example.pokedexandroid.data.remote.dto

import com.google.gson.annotations.SerializedName

data class TypesDto(
    val slot: Int,
    @SerializedName("type")
    val typeDetails: TypeDetailsDto
)
