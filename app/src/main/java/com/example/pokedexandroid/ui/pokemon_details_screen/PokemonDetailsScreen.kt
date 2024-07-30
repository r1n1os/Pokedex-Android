package com.example.pokedexandroid.ui.pokemon_details_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.toRoute
import com.example.pokedexandroid.R
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
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Red)
    ) {
        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(
                    topStart = 15.dp,
                    topEnd = 15.dp
                ),)
                .padding(top = 50.dp)
            ) {
            Column {
                Text(
                    text = "Base Stats",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}