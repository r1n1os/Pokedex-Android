package com.example.pokedexandroid.data.local_database.type_converters

import androidx.room.TypeConverter
import com.example.pokedexandroid.data.local_database.StatsEntity.StatsEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class StatsTypeConverter {
    @TypeConverter
    fun fromStatsList(stats: List<StatsEntity>): String {
        val gson = Gson()
        val type = object : TypeToken<List<StatsEntity>>() {}.type
        return gson.toJson(stats, type)
    }

    @TypeConverter
    fun toStatsList(statsString: String): List<StatsEntity> {
        val gson = Gson()
        val type = object : TypeToken<List<StatsEntity>>() {}.type
        return gson.fromJson(statsString, type)
    }
}