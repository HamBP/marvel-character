package me.algosketch.shopliveassignment.presentation.favorite

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import me.algosketch.shopliveassignment.presentation.components.CharacterCards
import me.algosketch.shopliveassignment.presentation.components.LoadingProgress

@Composable
fun FavoriteScreen(viewModel: FavoriteViewModel = hiltViewModel()) {
    LaunchedEffect(key1 = Unit) {
        viewModel.fetchFavoriteCharacters()
    }

    val uiState = viewModel.state.collectAsState()

    when (uiState.value) {
        is FavoriteUiState.Loading -> LoadingProgress()
        is FavoriteUiState.Success -> CharacterCards(
            bookmark = { character -> viewModel.unbookmark(character) },
            characters = (uiState.value as FavoriteUiState.Success).characters,
        )
        is FavoriteUiState.Error -> {
            Text("Error")
        }
        else -> CharacterCards(emptyList()) {}
    }
}