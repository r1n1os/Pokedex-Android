package com.example.pokedexandroid.data.remote.dto

import com.example.pokedexandroid.data.local_database.StatsEntity.StatsEntity
import com.google.gson.annotations.SerializedName

data class StatsDto(
    @SerializedName("base_stat")
    val statValue: Int,
    val effort: Int,
    @SerializedName("stat")
    val stat: StatDto
) {
    fun toStatsEntity(): StatsEntity {
        return StatsEntity(
            name = stat.name,
            value = statValue,
            id = -1 // Here we are specifying id to be -1 as the StatsEntity Id is autoIncrement
        )
    }
}
