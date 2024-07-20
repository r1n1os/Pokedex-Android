package com.example.pokedexandroid.ui.PokemonListScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexandroid.domain.repository.PokemonListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
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
        _pokemonListState.value = _pokemonListState.value.copy(pokemonList = response.data?.toList() ?: emptyList(), nextUrl = response.nextUrl)
    }

    fun executeRequestToGetNextListOfPokemon() = viewModelScope.launch(Dispatchers.IO) {
        _pokemonListState.value = _pokemonListState.value.copy(isLoading = true)
        val response = pokemonListRepository.executeRequestToGetPokemonList(_pokemonListState.value.nextUrl)
        _pokemonListState.value = _pokemonListState.value.copy(pokemonList = response.data?.toList() ?: emptyList(), nextUrl = response.nextUrl)
    }
}