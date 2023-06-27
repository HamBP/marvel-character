package me.algosketch.shopliveassignment.presentation.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import me.algosketch.shopliveassignment.presentation.components.CharacterCards

@Composable
fun SearchScreen(viewModel: SearchViewModel = hiltViewModel()) {
    val keyword by viewModel.keyword.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    Column {

        OutlinedTextField(value = keyword,
            onValueChange = { viewModel.updateKeyword(it) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("마블 영웅 이름을 입력하시오.") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search, contentDescription = "Search"
                )
            })

        when (uiState) {
            is SearchUiState.Loading, SearchUiState.Success -> CharacterCards(
                bookmark = { character -> viewModel.bookmark(character) },
                characters = viewModel.characters,
                onBottomReached = { viewModel.search() },
                isLoading = uiState is SearchUiState.Loading,
            )
            is SearchUiState.Error -> {
                Text("Error")
            }
            else -> CharacterCards(emptyList(), bookmark = {})
        }
    }
}
