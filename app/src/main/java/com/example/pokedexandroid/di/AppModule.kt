package com.example.pokedexandroid.di

import android.content.Context
import androidx.room.Room
import com.example.pokedexandroid.data.LocalDatabase.PokemonDatabase
import com.example.pokedexandroid.data.remote.PokemonList.PokemonListApi
import com.example.pokedexandroid.domain.repository.PokemonListRepository
import com.example.pokedexandroid.utils.Urls
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePokemonApi(): PokemonListApi {
        return Retrofit.Builder()
            .baseUrl(Urls.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokemonListApi::class.java)
    }

    @Provides
    @Singleton
    fun providePokemonListRepository(
        pokemonListApi: PokemonListApi,
        pokemonDatabase: PokemonDatabase
    ): PokemonListRepository {
        return PokemonListRepository(pokemonListApi, pokemonDatabase)
    }

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
