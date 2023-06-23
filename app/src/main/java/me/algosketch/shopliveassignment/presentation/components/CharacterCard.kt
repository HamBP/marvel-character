package me.algosketch.shopliveassignment.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import me.algosketch.shopliveassignment.presentation.search.CharacterEntity

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
            modifier = Modifier
                .fillMaxWidth()
                .background(color = if (character.favorite) Color.Gray else Color.White),
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