package me.algosketch.shopliveassignment.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.algosketch.shopliveassignment.data.repository.FavoriteCharacterRepository
import me.algosketch.shopliveassignment.presentation.search.CharacterEntity
import me.algosketch.shopliveassignment.presentation.search.toEntity
import javax.inject.Inject

sealed class FavoriteUiState {
    object Loading : FavoriteUiState()
    data class Success(
        val characters: List<CharacterEntity> = emptyList(),
    ) : FavoriteUiState()

    data class Error(val message: String) : FavoriteUiState()
}

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteCharacterRepository: FavoriteCharacterRepository
) : ViewModel() {
    private val _state = MutableStateFlow<FavoriteUiState>(FavoriteUiState.Success())
    val state = _state.asStateFlow()

    fun fetchFavoriteCharacters() {
        _state.value = FavoriteUiState.Loading

        viewModelScope.launch {
            _state.value = FavoriteUiState.Success(
                characters = favoriteCharacterRepository.getFavoriteCharacters()
                    .map { it.toEntity() })
        }
    }

    fun unbookmark(characterEntity: CharacterEntity) {
        viewModelScope.launch {
            favoriteCharacterRepository.delete(characterEntity.id)
        }
    }
}