package me.algosketch.shopliveassignment.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.algosketch.shopliveassignment.data.repository.FavoriteCharacterRepository
import me.algosketch.shopliveassignment.presentation.components.CharacterModel
import me.algosketch.shopliveassignment.presentation.components.toModel
import javax.inject.Inject

sealed class FavoriteUiState {
    object Loading : FavoriteUiState()
    object Empty : FavoriteUiState()

    data class Success(
        val characters: List<CharacterModel> = emptyList(),
    ) : FavoriteUiState()

    data class Error(val message: String) : FavoriteUiState()
}

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteCharacterRepository: FavoriteCharacterRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<FavoriteUiState>(FavoriteUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun fetchFavoriteCharacters() {
        _uiState.value = FavoriteUiState.Loading

        viewModelScope.launch {
            val res = favoriteCharacterRepository.getFavoriteCharacters().map { it.toModel() }

            _uiState.value = when {
                res.isNotEmpty() -> {
                    FavoriteUiState.Success(
                        characters = favoriteCharacterRepository.getFavoriteCharacters()
                            .map { it.toModel() })
                }
                else -> FavoriteUiState.Empty
            }
        }
    }

    fun unbookmark(characterModel: CharacterModel) {
        viewModelScope.launch {
            favoriteCharacterRepository.delete(characterModel.id)
            fetchFavoriteCharacters()
        }
    }
}