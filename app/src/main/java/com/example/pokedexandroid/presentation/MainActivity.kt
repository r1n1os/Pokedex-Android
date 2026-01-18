@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.pokedexandroid.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.pokedexandroid.navigations.NavigationHandler
import com.example.pokedexandroid.navigations.routes.PokemonDetailsRoute
import com.example.pokedexandroid.navigations.routes.PokemonListRoute
import com.example.pokedexandroid.presentation.pokemon_details_screen.PokemonDetailsScreen
import com.example.pokedexandroid.presentation.pokemon_list_screen.PokemonListScreen
import com.example.pokedexandroid.presentation.theme.PokedexAndroidTheme
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalSharedTransitionApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokedexAndroidTheme {
                NavigationHandler.AppNavDisplay()
            }
        }
    }
}


