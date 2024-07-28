package com.example.pokedexandroid.ui.pokemon_list_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.pokedexandroid.data.local_database.pokemon_entity.PokemonEntity
import com.example.pokedexandroid.navigations.PokemonDetailsScreen

@ExperimentalGlideComposeApi
@Composable
fun PokemonListScreen(
    navController: NavController,
    pokemonListViewModel: PokemonListViewModel = hiltViewModel()
) {
    val state = pokemonListViewModel.pokemonListState.collectAsState().value
    val scrollState = rememberLazyGridState()

    var reachEndOfList by remember {
        mutableStateOf(false)
    }

    Surface {
        LazyVerticalGrid(
            state = scrollState,
            modifier = Modifier
                .background(Color(0xFF78909C))
                .padding(top = 35.dp, start = 21.dp, end = 21.dp, bottom = 21.dp),
            columns = GridCells.Adaptive(150.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.pokemonList) { pokemon ->
                reachEndOfList = false
                PokemonCell(
                    navController = navController,
                    pokemon = pokemon
                )
            }

            item {
                LaunchedEffect(true) {
                    reachEndOfList = true
                    pokemonListViewModel.executeRequestToGetNextListOfPokemon()
                }
                if (reachEndOfList)
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator()
                    }
            }
        }
    }
}

@ExperimentalGlideComposeApi
@Composable
private fun PokemonCell(
    navController: NavController,
    pokemon: PokemonEntity
) {
    Card(
        modifier =
        Modifier
            .width(150.dp)
            .height(150.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        shape = RoundedCornerShape(15),
        onClick = {
            navController.navigate(
                PokemonDetailsScreen(
                    pokemonDetailsUrl = pokemon.extraInfoUrl
                )
            )
        }) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            GlideImage(
                model = pokemon.photoUrl ?: "",
                alignment = Alignment.Center,
                contentDescription = "Network Image"
            )
        }
    }
}
