package com.example.pokedexandroid.data.local_database.types_entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pokedexandroid.domain.model.Type

@Entity(tableName = "Types")
data class TypesEntity(
    @PrimaryKey
    val typeName: String,
    val url: String
) {
    fun toType(): Type {
        return  Type(
            name = typeName,
            url = url
        )
    }
}
