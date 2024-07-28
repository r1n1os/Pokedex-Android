package com.example.pokedexandroid.di

import com.example.pokedexandroid.data.local_database.PokemonDatabase
import com.example.pokedexandroid.data.remote.pokemon_details.PokemonDetailsApi
import com.example.pokedexandroid.data.remote.pokemon_list.PokemonListApi
import com.example.pokedexandroid.domain.repository.PokemonDetailsRepository
import com.example.pokedexandroid.data.repository.PokemonListRepositoryImpl
import com.example.pokedexandroid.domain.repository.PokemonListRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
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

  /*  @Provides
    @Singleton
    fun providePokemonDetailsRepository(
        pokemonDetailsApi: PokemonDetailsApi,
        pokemonDatabase: PokemonDatabase
    ): PokemonDetailsRepository {
        return PokemonDetailsRepository(pokemonDetailsApi, pokemonDatabase)
    }*/
}