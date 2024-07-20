package com.example.pokedexandroid.ui.PokemonListScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.pokedexandroid.data.LocalDatabase.PokemonEntity.PokemonEntity

@ExperimentalGlideComposeApi
@Composable
fun PokemonListScreen(
    navController: NavController,
    pokemonListViewModel: PokemonListViewModel = hiltViewModel()
) {
    val state = pokemonListViewModel.pokemonListState.collectAsState().value
    Surface {
        LazyVerticalGrid(
            modifier = Modifier
                .background(Color.Blue)
                .padding(top = 35.dp, start = 21.dp, end = 21.dp, bottom = 21.dp),
            columns = GridCells.Adaptive(150.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.pokemonList) { pokemon ->
                PokemonCell(pokemon)
            }
        }
    }
}

@ExperimentalGlideComposeApi
@Composable
private fun PokemonCell(pokemon: PokemonEntity) {
    Log.d("fsafsadf", "dfdf: ${pokemon.photoUrl}")
    Box(
        modifier =
        Modifier
            .width(150.dp)
            .height(150.dp)
            .background(Color.Red),
        contentAlignment = Alignment.Center
    ) {
        GlideImage(
            model = pokemon.photoUrl ?: "",
            contentDescription = "Network Image"
        )
    }
}
