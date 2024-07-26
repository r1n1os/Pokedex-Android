package com.example.pokedexandroid.di

import android.content.Context
import androidx.room.Room
import com.example.pokedexandroid.data.LocalDatabase.PokemonDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDatabaseProvider {

    @Provides
    @Singleton
    fun providePokemonDatabase(@ApplicationContext appContext: Context): PokemonDatabase {
        return Room.databaseBuilder(
            appContext.applicationContext,
            PokemonDatabase::class.java,
            "pokemon_database"
        ).build()
    }
}