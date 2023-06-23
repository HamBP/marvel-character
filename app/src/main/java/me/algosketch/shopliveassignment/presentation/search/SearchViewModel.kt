package me.algosketch.shopliveassignment.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.algosketch.shopliveassignment.data.repository.FavoriteCharacterRepository
import me.algosketch.shopliveassignment.data.repository.MarvelCharacterRepository
import me.algosketch.shopliveassignment.data.source.ApiResponse
import javax.inject.Inject

sealed class SearchUiState {
    object Loading : SearchUiState()
    data class Success(
        val characters: List<CharacterEntity> = emptyList(),
    ) : SearchUiState()
    data class Error(val message: String) : SearchUiState()
}

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: MarvelCharacterRepository,
    private val favoriteCharacterRepository: FavoriteCharacterRepository,
) : ViewModel() {
    private val _keyword = MutableStateFlow("")
    val keyword = _keyword.asStateFlow()
    private val _state = MutableStateFlow<SearchUiState>(SearchUiState.Success())
    val state = _state.asStateFlow()

    fun search() {
        viewModelScope.launch {
            if(keyword.value.length < 2) return@launch

            _state.value = SearchUiState.Loading

            val res = searchRepository.getMarvelCharacters(keyword.value)

            _state.value = when(res) {
                is ApiResponse.Success -> {
                    val list = res.data?.data!!.results.map { it.toEntity() }
                    SearchUiState.Success(list)
                }
                is ApiResponse.Error -> SearchUiState.Error(res.message)
                else -> SearchUiState.Success(emptyList())
            }
        }
    }

    fun bookmark(character: CharacterEntity) {
        viewModelScope.launch {
            favoriteCharacterRepository.insert(character.toFavoriteCharacter())
        }
    }

    fun updateKeyword(newKeyword: String) {
        _keyword.value = newKeyword
    }
}