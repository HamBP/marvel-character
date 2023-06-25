package me.algosketch.shopliveassignment.presentation.favorite

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        is FavoriteUiState.Empty -> EmptyContent()
        is FavoriteUiState.Error -> {
            Text("Error")
        }
        else -> CharacterCards(emptyList(), bookmark = {})
    }
}

@Composable
fun EmptyContent() {
    Text(
        "저장해 둔 캐릭터가 없습니다",
        color = Color.Gray,
        fontSize = 24.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
            .padding(top = 24.dp),
    )
}