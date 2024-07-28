package com.example.pokedexandroid.ui.pokemon_details_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexandroid.domain.repository.PokemonDetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    private val pokemonDetailsRepository: PokemonDetailsRepository
) : ViewModel() {
    private val _pokemonDetailsState = MutableStateFlow(PokemonDetailsState())
    val pokemonDetailsState: StateFlow<PokemonDetailsState>
        get() = _pokemonDetailsState


    fun executeRequestToGetPokemonDetails(pokemonDetailsUrl: String?) =
        viewModelScope.launch(Dispatchers.IO) {
            _pokemonDetailsState.value = _pokemonDetailsState.value.copy(isLoading = true)
            if (pokemonDetailsUrl != null) {
                val response =
                    pokemonDetailsRepository.executeRequestToGetPokemonDetails(pokemonDetailsUrl = pokemonDetailsUrl)
                Log.d("RESULT", response.data?.id.toString() ?: "No response")
            }
        }
}