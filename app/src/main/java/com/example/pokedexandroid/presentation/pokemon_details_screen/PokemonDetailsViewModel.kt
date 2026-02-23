package com.example.pokedexandroid.presentation.pokemon_details_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexandroid.di.IoDispatcher
import com.example.pokedexandroid.domain.repository.PokemonDetailsRepository
import com.example.pokedexandroid.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    private val pokemonDetailsRepository: PokemonDetailsRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {
    private val _pokemonDetailsState = MutableStateFlow(PokemonDetailsState())
    val pokemonDetailsState: StateFlow<PokemonDetailsState>
        get() = _pokemonDetailsState


    fun executeRequestToGetPokemonDetails(pokemonDetailsUrl: String?) =
        viewModelScope.launch(ioDispatcher) {
            _pokemonDetailsState.value = _pokemonDetailsState.value.copy(isLoading = true)
            ///The delay here has been added so the loading being more realistic
            //delay(1200)

            if (pokemonDetailsUrl != null) {
                val response =
                    pokemonDetailsRepository.executeRequestToGetPokemonDetails(pokemonDetailsUrl = pokemonDetailsUrl)

                when(response) {
                    is Resource.Success -> {
                        _pokemonDetailsState.value = _pokemonDetailsState.value.copy(
                            isLoading = false,
                            pokemonDetails = response.data
                        )
                    }
                    is Resource.Error -> {
                        _pokemonDetailsState.value = _pokemonDetailsState.value.copy(
                            isLoading = false,
                            error = response.message ?: "Unknown error"
                        )
                    }
                    else -> Unit
                }
            }
        }

}