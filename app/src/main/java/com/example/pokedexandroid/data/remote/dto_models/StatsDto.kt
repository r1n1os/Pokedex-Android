package com.example.pokedexandroid.data.remote.dto_models

import com.google.gson.annotations.SerializedName

data class StatsDto(
    @SerializedName("base_stat")
    val statValue: Int,
    val effort: Int,
    @SerializedName("stat")
    val stat: StatDto
)
