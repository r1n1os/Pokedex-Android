package com.example.pokedexandroid.data.local_database.type_converters

import androidx.room.TypeConverter
import com.example.pokedexandroid.data.local_database.entities.types_entity.TypesEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TypesTypeConverter {
    @TypeConverter
    fun fromTypesList(stats: List<TypesEntity>): String {
        val gson = Gson()
        val type = object : TypeToken<List<TypesEntity>>() {}.type
        return gson.toJson(stats, type)
    }

    @TypeConverter
    fun toTypesList(statsString: String): List<TypesEntity> {
        val gson = Gson()
        val type = object : TypeToken<List<TypesEntity>>() {}.type
        return gson.fromJson(statsString, type)
    }
}