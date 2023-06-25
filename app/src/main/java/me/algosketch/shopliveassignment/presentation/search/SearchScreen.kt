package me.algosketch.shopliveassignment.presentation.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import me.algosketch.shopliveassignment.presentation.components.CharacterCards
import me.algosketch.shopliveassignment.presentation.components.LoadingProgress

@Composable
fun SearchScreen(viewModel: SearchViewModel = hiltViewModel()) {
    val keyword = viewModel.keyword.collectAsState()
    val uiState = viewModel.state.collectAsState()

    Column {

        OutlinedTextField(value = keyword.value,
            onValueChange = { viewModel.updateKeyword(it) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("마블 영웅 이름을 입력하시오.") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search, contentDescription = "Search"
                )
            })

        when (uiState.value) {
            is SearchUiState.Loading -> LoadingProgress()
            is SearchUiState.Success -> CharacterCards(
                bookmark = { character -> viewModel.bookmark(character) },
                characters = (uiState.value as SearchUiState.Success).characters,
                onBottomReached = { viewModel.search() },
            )
            is SearchUiState.Error -> {
                Text("Error")
            }
            else -> CharacterCards(emptyList(), bookmark = {})
        }
    }
}