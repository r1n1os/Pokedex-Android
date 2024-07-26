package com.example.pokedexandroid.di

import com.example.pokedexandroid.data.LocalDatabase.PokemonDatabase
import com.example.pokedexandroid.data.remote.PokemonDetails.PokemonDetailsApi
import com.example.pokedexandroid.data.remote.PokemonList.PokemonListApi
import com.example.pokedexandroid.domain.repository.PokemonDetailsRepository
import com.example.pokedexandroid.domain.repository.PokemonListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryProvider {
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
    fun providePokemonDetailsRepository(
        pokemonDetailsApi: PokemonDetailsApi,
        pokemonDatabase: PokemonDatabase
    ): PokemonDetailsRepository {
        return PokemonDetailsRepository(pokemonDetailsApi, pokemonDatabase)
    }
}