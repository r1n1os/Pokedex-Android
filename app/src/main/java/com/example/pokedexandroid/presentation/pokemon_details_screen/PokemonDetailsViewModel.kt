package com.example.pokedexandroid.presentation.pokemon_details_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexandroid.data.repository.PokemonDetailsRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
            Log.d("TAG", "Response: ${pokemonDetailsUrl} ")

            if (pokemonDetailsUrl != null) {
                val response =
                    pokemonDetailsRepositoryImpl.executeRequestToGetPokemonDetails(pokemonDetailsUrl = pokemonDetailsUrl)
                Log.d("TAG", "Response: ${response} ")

                _pokemonDetailsState.value = _pokemonDetailsState.value.copy(
                    isLoading = false,
                    pokemonDetails = response.data
                )
            }
        }

}