package com.example.pokedexandroid.data.local_database.StatsEntity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pokedexandroid.domain.model.Stats

@Entity(tableName = "Stats")
data class StatsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val statName: String,
    val value: Int,
    val pokemonEntityName: String //This is the primary key of pokemonEntity
) {
    fun toStats(): Stats {
        return  Stats(
            name = statName,
            value = value
        )
    }
}
