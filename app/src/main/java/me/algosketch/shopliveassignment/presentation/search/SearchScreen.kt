package me.algosketch.shopliveassignment.presentation.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
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

        OutlinedTextField(
            value = keyword.value,
            onValueChange = { viewModel.updateKeyword(it) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("마블 영웅 이름을 입력하시오.") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            }
        )

        when (uiState.value) {
            is SearchUiState.Loading -> LoadingProgress()
            is SearchUiState.Success -> CharacterCards(
                bookmark = { character -> viewModel.bookmark(character) },
                characters = (uiState.value as SearchUiState.Success).characters
            )
            is SearchUiState.Error -> {
                Text("Error")
            }
            else -> CharacterCards(emptyList()) {}
        }
    }
}

@Composable
fun LoadingProgress() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun CharacterCards(characters: List<CharacterEntity>, bookmark: (CharacterEntity) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 160.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier.padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 32.dp),
    ) {
        items(characters.size) {
            CharacterCard(character = characters[it], bookmark)
        }
    }
}

@Composable
fun CharacterCard(character: CharacterEntity, onClick: (CharacterEntity) -> Unit) {
    Card(
        modifier = Modifier
            .padding(bottom = 24.dp)
            .clickable { onClick(character) }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Image(
                painter = rememberAsyncImagePainter(character.thumbnailUrl),
                contentDescription = character.name,
                modifier = Modifier.size(120.dp)
            )
            Text(character.name)
            Text(character.description, overflow = TextOverflow.Ellipsis, maxLines = 3)
        }
    }
}