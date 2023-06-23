package me.algosketch.shopliveassignment.presentation.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter

@Composable
fun SearchScreen(viewModel: SearchViewModel = hiltViewModel()) {
    val keyword = viewModel.keyword.collectAsState()
    val uiState = viewModel.state.collectAsState()

    LaunchedEffect(key1 = keyword.value) {
        viewModel.search()
    }

    Column {
        TextField(value = keyword.value, onValueChange = { viewModel.updateKeyword(it) })

        when (uiState.value) {
            is SearchUiState.Success -> CharacterCards(characters = (uiState.value as SearchUiState.Success).characters)
            is SearchUiState.Error -> {
                Text("Error")
            }
            else -> CharacterCards(emptyList())
        }
    }
}

@Composable
fun CharacterCards(characters: List<CharacterEntity>) {
    LazyColumn {
        characters.forEach {
            item {
                CharacterCard(it)
            }
        }
    }
}

@Composable
fun CharacterCard(character: CharacterEntity) {
    println("제가 한 번 프린트 : " + character.thumbnailUrl)

    Column {
        Image(
            painter = rememberAsyncImagePainter(character.thumbnailUrl),
            contentDescription = character.name,
            modifier = Modifier.size(80.dp)
        )
        Text(character.name)
    }
}