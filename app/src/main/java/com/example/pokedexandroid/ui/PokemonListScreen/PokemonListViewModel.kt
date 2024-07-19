package com.example.pokedexandroid.ui.PokemonListScreen

import android.util.Log
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
        val listOfPokemon = pokemonListRepository.executeRequestToGetPokemonList()
        listOfPokemon.data?.forEach { pokemon ->
            Log.d("Pokemonss", "PokemonName: ${pokemon.name}")
        }
    }
}