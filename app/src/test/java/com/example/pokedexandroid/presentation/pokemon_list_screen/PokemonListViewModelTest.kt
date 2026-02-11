package com.example.pokedexandroid.presentation.pokemon_list_screen

import com.example.pokedexandroid.domain.model.PokemonListModel
import com.example.pokedexandroid.domain.repository.PokemonListRepository
import com.example.pokedexandroid.util.MainDispatcherRule
import com.example.pokedexandroid.utils.Resource
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

/**
 * Here we will test the following scenarios
 * 1. What should happen when ViewModel is first created? (initial state)
 *      1. Should start the process to fetch the first page and set state to loading = true
 * 2. What should happen when fetchFirstPage() is called and succeeds?
 *      1. Set the state to loading = false
 *      2. Return the list with pokemon details if it exists
 * 3. What should happen when fetchFirstPage() is called and fails?
 *      1. Set the state to loading = false
 *      2. Return the error message
 * 4. Same for pagination method
 *      Follows the point 2 and 3
 * */
@OptIn(ExperimentalCoroutinesApi::class)
class PokemonListViewModelTest {


    /**
     * Declare Test Rules.
     *
     * Test Rules are annotations like the @get:Rule which automatically set up infrastructure
     * In our case since we use coroutines. We need the main dispatcher to be set up.
     *
     * MainDispatcherRule: Is a Util class we have create to replace the main dispatcher with a test
     * so, coroutine can run synchronously in tests
     * */
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    /**
     * Declare Mocks
     * */
    @Mock
    private lateinit var repository: PokemonListRepository

    /**
     * Declare viewModel
     * */
    private lateinit var viewModel: PokemonListViewModel

    @Before
    fun setup() {
        //repository = mock<PokemonListRepository>() -> This can be used if we don't want to use Mock annotation
        MockitoAnnotations.openMocks(this)
        viewModel = PokemonListViewModel(repository, ioDispatcher = UnconfinedTestDispatcher())
    }

    @Test
    fun `initial state should be loading false`() {

        // When: ViewModel is created (already done in @Before)

        // Then: Check the initial state
        val initialState: Boolean = viewModel.pokemonListState.value.isLoading

        // Assertion : Validation of the expected result
        assertEquals(false, initialState)
    }

    @Test
    fun `fetching first page of pokemon details should succeed with three items`() = runTest {
        // Given: Mock the repository response
        val mockPokemonListResponse = mutableListOf<PokemonListModel>(
            PokemonListModel(
                name = "Bulbasaur",
                photoUrl = "",
                extraInfoUrl = ""
            ),
            PokemonListModel(
                name = "Pikachu",
                photoUrl = "",
                extraInfoUrl = ""
            ),
            PokemonListModel(
                name = "Charmander",
                photoUrl = "",
                extraInfoUrl = ""
            )
        )

        // Explicitly type the Resource
        val successResource: Resource<MutableList<PokemonListModel>> =
            Resource.Success(mockPokemonListResponse, null)

        whenever(repository.executeRequestToGetPokemonList(null))
            .thenReturn(successResource)

        // When: executeRequestToGetListOfPokemon() is called
        viewModel.executeRequestToGetListOfPokemon()

        // Give coroutines time to complete
        advanceUntilIdle()

        // Debug: Verify the repository was called
        verify(repository).executeRequestToGetPokemonList(null)

        // Debug: Print the actual state
        println("State: ${viewModel.pokemonListState.value}")
        println("Pokemon list size: ${viewModel.pokemonListState.value.pokemonList.size}")


        // Then: Get the result
        val result = viewModel.pokemonListState.value.pokemonList

        // Assertion : Validation of the expected result
        assertEquals(3, result.size)
    }

    @Test
    fun `fetching first page of pokemon details should failed`() = runTest {
        // Given: Mock the repository response
        val errorResource: Resource<MutableList<PokemonListModel>> =
            Resource.Error("Server Error", null)

        whenever(repository.executeRequestToGetPokemonList(null))
            .thenReturn(errorResource)

        // When: executeRequestToGetListOfPokemon() is called
        viewModel.executeRequestToGetListOfPokemon()

        // Give coroutines time to complete
        advanceUntilIdle()

        // Then: Get the result
        val result = viewModel.pokemonListState.value.error

        // Assertion : Validation of the expected result
        assertEquals("Server Error", result)
    }

    @Test
    fun `fetching next page of pokemon details should succeed with seven items`() = runTest {
        // Given: Mock the repository response
        val mockPokemonInitialListResponse = mutableListOf<PokemonListModel>(
            PokemonListModel(
                name = "Bulbasaur",
                photoUrl = "",
                extraInfoUrl = ""
            ),
            PokemonListModel(
                name = "Pikachu",
                photoUrl = "",
                extraInfoUrl = ""
            ),
            PokemonListModel(
                name = "Charmander",
                photoUrl = "",
                extraInfoUrl = ""
            )
        )

        // Explicitly type the Resource
        val initialResource: Resource<MutableList<PokemonListModel>> =
            Resource.Success(mockPokemonInitialListResponse, nextUrl = "2nd Page")

        whenever(repository.executeRequestToGetPokemonList(null))
            .thenReturn(initialResource)

        // When: executeRequestToGetListOfPokemon() is called
        viewModel.executeRequestToGetNextListOfPokemon()

        // Give coroutines time to complete
        advanceUntilIdle()

        val mockPokemonNextPageListResponse = mutableListOf<PokemonListModel>(
            PokemonListModel(
                name = "Pokemon A",
                photoUrl = "",
                extraInfoUrl = ""
            ),
            PokemonListModel(
                name = "Pokemon B",
                photoUrl = "",
                extraInfoUrl = ""
            ),
            PokemonListModel(
                name = "Pokemon C",
                photoUrl = "",
                extraInfoUrl = ""
            ),
            PokemonListModel(
                name = "Pokemon D",
                photoUrl = "",
                extraInfoUrl = ""
            )
        )

        val nextPageResource: Resource<MutableList<PokemonListModel>> =
            Resource.Success(mockPokemonNextPageListResponse, nextUrl = "3rd Page")

        whenever(repository.executeRequestToGetPokemonList(nextUrl = "2nd Page"))
            .thenReturn(nextPageResource)

        viewModel.executeRequestToGetNextListOfPokemon()
        advanceUntilIdle()

        verify(repository).executeRequestToGetPokemonList("2nd Page")


        // Then: Get the result
        val result = viewModel.pokemonListState.value.pokemonList

        // Assertion : Validation of the expected result
        assertEquals(7, result.size)
        assertEquals("3rd Page", viewModel.pokemonListState.value.nextUrl)
    }

    @Test
    fun `fetching next page of pokemon details should failed`() = runTest {
        // Given: Mock the repository response
        val mockPokemonInitialListResponse = mutableListOf<PokemonListModel>(
            PokemonListModel(
                name = "Bulbasaur",
                photoUrl = "",
                extraInfoUrl = ""
            ),
            PokemonListModel(
                name = "Pikachu",
                photoUrl = "",
                extraInfoUrl = ""
            ),
            PokemonListModel(
                name = "Charmander",
                photoUrl = "",
                extraInfoUrl = ""
            )
        )

        val initialPageResource: Resource<MutableList<PokemonListModel>> =
            Resource.Success(mockPokemonInitialListResponse, nextUrl = "2nd Page")

        whenever(repository.executeRequestToGetPokemonList(null))
            .thenReturn(initialPageResource)

        viewModel.executeRequestToGetListOfPokemon()
        advanceUntilIdle()


        val nextPageMockResponse: Resource<MutableList<PokemonListModel>> =
            Resource.Error("Page Not Found", null)

        whenever(repository.executeRequestToGetPokemonList("2nd Page"))
            .thenReturn(nextPageMockResponse)

        // When: executeRequestToGetListOfPokemon() is called
        viewModel.executeRequestToGetNextListOfPokemon()

        // Give coroutines time to complete
        advanceUntilIdle()

        // Then: Get the result
        val result = viewModel.pokemonListState.value.error

        // Assertion : Validation of the expected result
        assertEquals("Page Not Found", result)
    }
}
