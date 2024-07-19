package com.example.pokedexandroid.ui.PokemonListScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun PokemonListScreen(
    navController: NavController,
    pokemonListViewModel: PokemonListViewModel = hiltViewModel()) {
    val state = pokemonListViewModel.pokemonListState.collectAsState().value
    Surface {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            var myListOfNumber = mutableListOf(1,2,3,4,5)
            items(myListOfNumber) {number ->
                    Text(
                        text = number.toString()
                    )
            }
        }
    }
}