package com.example.pokedexandroid.presentation.pokemon_list_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexandroid.di.IoDispatcher
import com.example.pokedexandroid.domain.repository.PokemonListRepository
import com.example.pokedexandroid.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val pokemonListRepository: PokemonListRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _pokemonListState = MutableStateFlow(PokemonListState())
    val pokemonListState: StateFlow<PokemonListState>
        get() = _pokemonListState

    fun executeRequestToGetListOfPokemon() = viewModelScope.launch(ioDispatcher) {
        _pokemonListState.value = _pokemonListState.value.copy(isLoading = true)
        val response = pokemonListRepository.executeRequestToGetPokemonList(null)

        when (response) {
            is Resource.Success -> {
                ///The delay here has been added so the loading being more realistic
                delay(1500)
                _pokemonListState.value = _pokemonListState.value.copy(
                    pokemonList = response.data?.toList() ?: emptyList(),
                    nextUrl = response.nextUrl,
                    isLoading = false,
                    error = ""
                )
            }
            is Resource.Error -> {
                _pokemonListState.value = _pokemonListState.value.copy(
                    isLoading = false,
                    error = response.message ?: "Unknown error"
                )
            }
            else -> Unit
        }
    }

    fun executeRequestToGetNextListOfPokemon() = viewModelScope.launch(ioDispatcher) {
        val response =
            pokemonListRepository.executeRequestToGetPokemonList(_pokemonListState.value.nextUrl)

        when (response) {
            is Resource.Success -> {
                ///The delay here has been added so the loading being more realistic
                delay(1500)
                _pokemonListState.value = _pokemonListState.value.copy(
                    pokemonList = response.data?.toList() ?: emptyList(),
                    nextUrl = response.nextUrl,
                    isLoading = false
                )
            }
            is Resource.Error -> {
                _pokemonListState.value = _pokemonListState.value.copy(
                    isLoading = false,
                    error = response.message ?: "Unknown error"
                )
            }
            else -> Unit
        }
    }
}
