package com.example.pokedexandroid.presentation.pokemon_list_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexandroid.domain.repository.PokemonListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val pokemonListRepository: PokemonListRepository
) : ViewModel() {

    private val _pokemonListState = MutableStateFlow(PokemonListState())
    val pokemonListState: StateFlow<PokemonListState>
        get() = _pokemonListState

    init {
        executeRequestToGetListOfPokemon()
    }

    private fun executeRequestToGetListOfPokemon() = viewModelScope.launch(Dispatchers.IO) {
        _pokemonListState.value = _pokemonListState.value.copy(isLoading = true)
        val response = pokemonListRepository.executeRequestToGetPokemonList(null)

        ///The delay here has been added so the loading being more realistic
        delay(1500)
        withContext(Dispatchers.Main) {
            _pokemonListState.value = _pokemonListState.value.copy(
                pokemonList = response.data?.toList() ?: emptyList(),
                nextUrl = response.nextUrl,
                isLoading = false
            )
        }
    }

    fun executeRequestToGetNextListOfPokemon() = viewModelScope.launch(Dispatchers.IO) {
        val response = pokemonListRepository.executeRequestToGetPokemonList(_pokemonListState.value.nextUrl)
        ///The delay here has been added so the loading being more realistic
        delay(1500)
        _pokemonListState.value = _pokemonListState.value.copy(
            pokemonList = response.data?.toList() ?: emptyList(),
            nextUrl = response.nextUrl,
            isLoading = false)
    }
}