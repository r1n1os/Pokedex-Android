package com.example.pokedexandroid.data.remote.dto

import com.example.pokedexandroid.data.local_database.types_entity.TypesEntity

data class TypeDetailsDto(
    val name: String,
    val url: String
) {
    fun toTypeEntity(): TypesEntity {
        return TypesEntity(
            name = name,
            url = url,
        )
    }
}
