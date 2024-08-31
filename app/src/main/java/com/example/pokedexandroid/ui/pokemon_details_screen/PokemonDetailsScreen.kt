@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.pokedexandroid.ui.pokemon_details_screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.foundation.lazy.items
import com.example.pokedexandroid.navigations.PokemonDetailsRoute
import com.example.pokedexandroid.utils.Colors
import com.example.pokedexandroid.utils.HelperMethods

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
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                },
                title = {
                    Text(state.pokemonDetails?.name ?: "No Pokemon found")
                }
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Red)
                .padding(innerPadding)
        ) {
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
                contentAlignment = Alignment.TopCenter,
            ) {
                Column(
                    modifier = Modifier.padding(top = 50.dp)
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
                            val statValue: Float = stat.value.toFloat()
                            Row {
                                Text(
                                    text = "${HelperMethods.fullPokemonStatNameToShorten(statName = stat.name)}: ",
                                    style = TextStyle(
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Red
                                    )
                                )
                                LinearProgressIndicator(
                                    progress = {
                                        statValue / 100F
                                    },
                                    trackColor = Color.Green,
                                    color = Color.Blue,

                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .align(Alignment.CenterVertically),
                                )
                            }
                            Spacer(modifier = Modifier.height(15.dp))
                        }
                    }
                }
            }
        }
    }

}