@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.pokedexandroid.ui.pokemon_details_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.pokedexandroid.navigations.PokemonDetailsRoute
import com.example.pokedexandroid.ui.pokemon_details_screen.composaples.PokemonStat


@OptIn(ExperimentalGlideComposeApi::class)
@ExperimentalMaterial3Api
@Composable
fun PokemonDetailsScreen(
    navController: NavController,
    pokemonDetailsViewModel: PokemonDetailsViewModel = hiltViewModel()
) {
    val args =
        navController.getBackStackEntry<PokemonDetailsRoute>().toRoute<PokemonDetailsRoute>()
    val state = pokemonDetailsViewModel.pokemonDetailsState.collectAsStateWithLifecycle().value
    LaunchedEffect(key1 = true) {
        pokemonDetailsViewModel.executeRequestToGetPokemonDetails(args.pokemonDetailsUrl)
    }

    Scaffold(
        contentWindowInsets = WindowInsets(bottom = 0, top = 100)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color =  state.pokemonDetails?.color ?: Color.White)
                .padding(innerPadding)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
            ) {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        tint = Color.White,
                        contentDescription = "Localized description"
                    )
                }
                Text(
                    modifier = Modifier.padding(top = 10.dp),
                    text = state.pokemonDetails?.name ?: "No Pokemon found",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 200.dp)
                    .clip(
                        shape = RoundedCornerShape(
                            topStart = 15.dp,
                            topEnd = 15.dp
                        ),
                    )
                    .background(Color.White),
            ) {
                Column(
                    modifier = Modifier.padding(top = 100.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Base Stats",
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    LazyColumn {
                        items(state.pokemonDetails?.stats ?: emptyList()) { stat ->
                            PokemonStat(
                                stat = stat,
                                color = state.pokemonDetails?.color ?: Color.White
                            )
                        }
                    }
                }
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                GlideImage(
                    modifier = Modifier
                        .padding(
                            top = 130.dp
                        )
                        .width(100.dp)
                        .height(100.dp),
                    model = state.pokemonDetails?.photoUrl ?: "",
                    alignment = Alignment.TopCenter,
                    contentDescription = "Network Image"
                )
                Spacer(
                    modifier = Modifier
                        .height(5.dp)
                )
                LazyRow {
                    items(state.pokemonDetails?.types ?: emptyList()) { type ->
                        Box(
                            modifier = Modifier
                                .clip(
                                    shape = RoundedCornerShape(45),
                                )
                                .background(color = state.pokemonDetails?.color ?: Color.White)
                                .padding(top = 5.dp, bottom = 5.dp, start = 10.dp, end = 10.dp),
                        ) {
                            Text(
                                text = type.name, style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if(state.pokemonDetails?.color != null) Color.White else Color.Blue
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}