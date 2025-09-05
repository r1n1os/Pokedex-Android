package com.example.pokedexandroid.data.local_database.entities.StatsEntity

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert

@Dao
interface StatsDao {
    @Upsert
    suspend fun insertStatsEntity(statsEntity: StatsEntity)

    @Transaction
    @Query("SELECT * From stats")
    suspend fun getListOfStats(): List<StatsEntity>

    @Transaction
    @Query("DELETE From stats")
    suspend fun deleteStats()
}