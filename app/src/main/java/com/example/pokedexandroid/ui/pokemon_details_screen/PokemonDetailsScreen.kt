package com.example.pokedexandroid.ui.pokemon_details_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.toRoute
import com.example.pokedexandroid.navigations.PokemonDetailsScreen

@Composable
fun PokemonDetailsScreen(
    navController: NavController,
    pokemonDetailsViewModel: PokemonDetailsViewModel = hiltViewModel()
) {
    val args =
        navController.getBackStackEntry<PokemonDetailsScreen>().toRoute<PokemonDetailsScreen>()
    val state = pokemonDetailsViewModel.pokemonDetailsState.collectAsStateWithLifecycle().value
    LaunchedEffect(key1 = true) {
        pokemonDetailsViewModel.executeRequestToGetPokemonDetails(args.pokemonDetailsUrl)
    }
    Column {

    }
}