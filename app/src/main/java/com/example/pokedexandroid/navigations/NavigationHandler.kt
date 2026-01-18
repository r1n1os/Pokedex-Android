@file:OptIn(ExperimentalMaterial3AdaptiveApi::class)
package com.example.pokedexandroid.navigations

import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.pokedexandroid.navigations.routes.PokemonDetailsRoute
import com.example.pokedexandroid.navigations.routes.PokemonListRoute
import com.example.pokedexandroid.presentation.pokemon_details_screen.PokemonDetailsScreen
import com.example.pokedexandroid.presentation.pokemon_list_screen.PokemonListScreen

/**
 * This line of code is creating a special kind of "tunnel"
 * that lets data pass down through your UI tree without needing
 * to be passed as a parameter to every single function.
 *
 * Before and After examples:
 *
 * Before way: You pass data explicitly: Parent(nav) -> Child(nav) -> GrandChild(nav).
 * After way: You provide data at the top, and any child, anywhere deep down, can just "grab" it: LocalNavBackstack.current.
 *
 * There are two types of Composition: compositionLocalOf and staticCompositionLocalOf
 * The reason we have used the staticCompositionLocalOf is because the backStack it's self will not change
 * only the items inside it. So, is a bit more efficient to make it static.
 * */
val LocalNavBackstack = staticCompositionLocalOf<NavBackStack<NavKey>> {
    error("LocalNavBackstack not provided. Did you forget to wrap your app in CompositionLocalProvider?")
}

@OptIn(ExperimentalMaterial3Api::class)
object NavigationHandler {

    @Composable
    fun AppNavDisplay() {
        val backStack = rememberNavBackStack(PokemonListRoute)
        val listDetailsStrategy = rememberListDetailSceneStrategy<NavKey>()

        CompositionLocalProvider(LocalNavBackstack provides backStack) {
            SharedTransitionLayout {
                NavDisplay(
                    backStack = backStack,
                    sceneStrategy = listDetailsStrategy,
                    onBack = { backStack.removeLastOrNull() },
                    entryProvider = entryProvider {
                        entry<PokemonListRoute> {
                            PokemonListScreen()
                        }
                        entry<PokemonDetailsRoute> {
                            PokemonDetailsScreen(
                                pokemonDetailsUrl = it.pokemonDetailsUrl ?: "",
                            )
                        }
                    }
                )
            }
        }
    }
}