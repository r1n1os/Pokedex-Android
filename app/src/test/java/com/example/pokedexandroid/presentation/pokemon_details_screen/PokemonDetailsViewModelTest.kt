package com.example.pokedexandroid.presentation.pokemon_details_screen

import androidx.compose.ui.graphics.Color
import com.example.pokedexandroid.domain.model.PokemonDetailsModel
import com.example.pokedexandroid.domain.repository.PokemonDetailsRepository
import com.example.pokedexandroid.util.MainDispatcherRule
import com.example.pokedexandroid.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class PokemonDetailsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var repository: PokemonDetailsRepository

    private lateinit var viewModel: PokemonDetailsViewModel

    @Before
    fun setup(){
        MockitoAnnotations.openMocks(this)
        viewModel = PokemonDetailsViewModel(repository, ioDispatcher = UnconfinedTestDispatcher())
    }

    @Test
    fun `fetching pokemon details`() = runTest {
        //Give: Mock the Repository response
        val mockPokemonDetails = PokemonDetailsModel(
            name = "Bulbasaur",
            photoUrl = "",
            color = Color.Green,
            types = listOf(),
            stats = listOf(),
        )

        // Explicitly type of resource
        val successResource: Resource<PokemonDetailsModel> =
            Resource.Success(mockPokemonDetails, nextUrl = null)
        whenever(repository.executeRequestToGetPokemonDetails(any()))
            .thenReturn(successResource)

        //When: executeRequestToGetPokemonDetails() is called
        viewModel.executeRequestToGetPokemonDetails("")
        advanceUntilIdle()

        verify(repository).executeRequestToGetPokemonDetails(any())
        println("Result: ${viewModel.pokemonDetailsState.value.pokemonDetails}")

        val resultMatchingName = viewModel.pokemonDetailsState.value.pokemonDetails?.name

        assertEquals("Bulbasaur", resultMatchingName)
    }

    @Test
    fun `fetching pokemon details failing with not found`() = runTest {
        //Give
        val failingResource: Resource<PokemonDetailsModel> =
            Resource.Error("Pokemon Not Found", null)

        whenever(repository.executeRequestToGetPokemonDetails(any()))
            .thenReturn(failingResource)

        viewModel.executeRequestToGetPokemonDetails("")
        advanceUntilIdle()

        verify(repository).executeRequestToGetPokemonDetails(any())
        println("Result: ${viewModel.pokemonDetailsState.value.error}")

        val pokemonNotFoundErrorMessage = viewModel.pokemonDetailsState.value.error

        assertEquals("Pokemon Not Found", pokemonNotFoundErrorMessage)
    }
}