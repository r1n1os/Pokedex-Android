package com.example.pokedexandroid.ui.pokemon_list_screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun PokemonListScreen(
    navController: NavController,
    viewModel: PokemonListViewModel = hiltViewModel()) {
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