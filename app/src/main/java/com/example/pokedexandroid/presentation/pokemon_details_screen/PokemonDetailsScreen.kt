@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.pokedexandroid.presentation.pokemon_details_screen

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.toRoute
import coil3.compose.AsyncImage
import com.example.pokedexandroid.R
import com.example.pokedexandroid.navigations.PokemonDetailsRoute
import com.example.pokedexandroid.presentation.CustomCompose.CustomLoader
import com.example.pokedexandroid.presentation.pokemon_details_screen.composaples.PokemonStat
import com.example.pokedexandroid.utils.capitalizeTheFirstLetter


@OptIn(ExperimentalSharedTransitionApi::class)
@ExperimentalMaterial3Api
@Composable
fun SharedTransitionScope.PokemonDetailsScreen(
    navController: NavController,
    animatedVisibilityScope: AnimatedVisibilityScope,
    pokemonDetailsViewModel: PokemonDetailsViewModel = hiltViewModel()
) {
    val currentBackStackEntry = navController.currentBackStackEntryAsState().value
    val args = remember(currentBackStackEntry) { // Keyed by the NavBackStackEntry
        currentBackStackEntry?.toRoute<PokemonDetailsRoute>()
    }
    val state = pokemonDetailsViewModel.pokemonDetailsState.collectAsStateWithLifecycle().value
    val lifecycle = LocalLifecycleOwner.current
    LaunchedEffect(key1 = true) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            pokemonDetailsViewModel.executeRequestToGetPokemonDetails(args?.pokemonDetailsUrl)
        }
    }

    Scaffold(
        contentWindowInsets = WindowInsets(bottom = 0, top = 100)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = state.pokemonDetails?.color ?: Color(0xFF78909C))
                .padding(innerPadding)
        ) {
            if (state.pokemonDetails == null) {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        tint = Color.White,
                        contentDescription = "Localized description"
                    )
                }
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_empty_pokeball),
                        contentDescription = "Empty Pokeball"
                    )
                    Text(
                        modifier = Modifier.padding(top = 10.dp),
                        text = "Oups! Pokemon Escaped!",
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    )
                }
            } else {
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
                        text = state.pokemonDetails?.name.capitalizeTheFirstLetter().toString()
                            ?: "No Pokemon found",
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
                        if (state.pokemonDetails.stats.isNotEmpty() == true)
                            Text(
                                text = "Base Stats",
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        LazyColumn {
                            items(state.pokemonDetails.stats ?: emptyList()) { stat ->
                                PokemonStat(
                                    stat = stat,
                                    color = state.pokemonDetails.color ?: Color.White
                                )
                            }
                        }
                    }
                }
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .padding(
                                top = 130.dp
                            )
                            .width(100.dp)
                            .height(100.dp)
                            .sharedElement(
                                rememberSharedContentState(key = state.pokemonDetails.name),
                                animatedVisibilityScope = animatedVisibilityScope,
                                boundsTransform = { initial, target ->
                                    tween(durationMillis = 1000)
                                },
                            ),
                        model = state.pokemonDetails.photoUrl,
                        contentDescription = null
                    )
                    Spacer(
                        modifier = Modifier
                            .height(15.dp)
                    )
                    LazyRow {
                        items(state.pokemonDetails?.types ?: emptyList()) { type ->
                            Box(
                                modifier = Modifier
                                    .clip(
                                        shape = RoundedCornerShape(45),
                                    )
                                    .background(color = state.pokemonDetails.color ?: Color.White)
                                    .padding(top = 5.dp, bottom = 5.dp, start = 10.dp, end = 10.dp),
                            ) {
                                Text(
                                    text = type.name, style = TextStyle(
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (state.pokemonDetails.color != null) Color.White else Color.Blue
                                    )
                                )
                            }
                        }
                    }
                }
            }
            if (state.isLoading) {
                CustomLoader()
            }
        }
    }
}