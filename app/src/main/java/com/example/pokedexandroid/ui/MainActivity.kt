package com.example.pokedexandroid.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.example.pokedexandroid.navigations.PokemonDetailsScreen
import com.example.pokedexandroid.navigations.PokemonListScreen
import com.example.pokedexandroid.ui.pokemon_details_screen.PokemonDetailsScreen
import com.example.pokedexandroid.ui.pokemon_list_screen.PokemonListScreen
import com.example.pokedexandroid.ui.theme.PokedexAndroidTheme
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalGlideComposeApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokedexAndroidTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = PokemonListScreen
                ) {
                    composable<PokemonListScreen> {
                        PokemonListScreen(
                            navController = navController
                        )
                    }
                    composable<PokemonDetailsScreen> {
                        PokemonDetailsScreen(navController = navController)
                    }
                }
            }
        }
    }
}


