package com.example.pokedexandroid.ui.pokemon_details_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexandroid.data.repository.PokemonDetailsRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    private val pokemonDetailsRepositoryImpl: PokemonDetailsRepositoryImpl
) : ViewModel() {
    private val _pokemonDetailsState = MutableStateFlow(PokemonDetailsState())
    val pokemonDetailsState: StateFlow<PokemonDetailsState>
        get() = _pokemonDetailsState


    fun executeRequestToGetPokemonDetails(pokemonDetailsUrl: String?) =
        viewModelScope.launch(Dispatchers.IO) {
            _pokemonDetailsState.value = _pokemonDetailsState.value.copy(isLoading = true)
            ///The delay here has been added so the loading being more realistic
            //delay(1200)
            if (pokemonDetailsUrl != null) {
                val response =
                    pokemonDetailsRepositoryImpl.executeRequestToGetPokemonDetails(pokemonDetailsUrl = pokemonDetailsUrl)
                _pokemonDetailsState.value = _pokemonDetailsState.value.copy(
                    isLoading = false,
                    pokemonDetails = response.data
                )
            }
        }

}