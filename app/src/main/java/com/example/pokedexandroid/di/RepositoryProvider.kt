package com.example.pokedexandroid.di

import com.example.pokedexandroid.data.repository.PokemonDetailsRepositoryImpl
import com.example.pokedexandroid.data.repository.PokemonListRepositoryImpl
import com.example.pokedexandroid.domain.repository.PokemonDetailsRepository
import com.example.pokedexandroid.domain.repository.PokemonListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryProvider {

    @Binds
    @Singleton
    abstract fun providePokemonListRepository(
        pokemonListRepositoryImpl: PokemonListRepositoryImpl
    ): PokemonListRepository

    @Binds
    @Singleton
    abstract fun providePokemonDetailsRepository(
        pokemonDetailsRepositoryImpl: PokemonDetailsRepositoryImpl
    ): PokemonDetailsRepository
}