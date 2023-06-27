package me.algosketch.shopliveassignment.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import me.algosketch.shopliveassignment.data.repository.FavoriteCharacterRepository
import me.algosketch.shopliveassignment.data.repository.MarvelCharacterRepository
import me.algosketch.shopliveassignment.data.source.ApiResponse
import me.algosketch.shopliveassignment.presentation.components.CharacterModel
import me.algosketch.shopliveassignment.presentation.components.toModels
import javax.inject.Inject

sealed class SearchUiState {
    object Loading : SearchUiState()
    object Success : SearchUiState()
    data class Error(val message: String) : SearchUiState()
}

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: MarvelCharacterRepository,
    private val favoriteCharacterRepository: FavoriteCharacterRepository,
) : ViewModel() {
    private val _keyword = MutableStateFlow("")
    val keyword = _keyword.asStateFlow()

    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Success)
    val uiState = _uiState.asStateFlow()
    private var favoriteIds: List<Int> = emptyList()
    private val _characters = MutableStateFlow<List<CharacterModel>>(emptyList())
    val characters = _characters.asStateFlow()

    private var searchJob: Job? = null

    init {
        fetchFavoriteCharacters()
        collectEvents()
    }

    private fun fetchFavoriteCharacters() {
        viewModelScope.launch {
            favoriteIds = favoriteCharacterRepository.getFavoriteCharacters().map { it.characterId }
            updateFavorite()
        }
    }

    private fun collectEvents() {
        viewModelScope.launch {
            keyword.debounce(300L)
                .map { it.trim() }
                .collect {
                    _characters.value = emptyList()
                    search()
                }
        }
    }

    fun search() {
        cancelPreviousSearch()

        if (keyword.value.length < 2) return

        searchJob = viewModelScope.launch {
            _uiState.value = SearchUiState.Loading

            val res = searchRepository.getMarvelCharacters(keyword.value)

            _uiState.value = when (res) {
                is ApiResponse.Success -> {
                    _characters.value += res.toModels(favoriteIds)
                    SearchUiState.Success
                }
                is ApiResponse.Error -> SearchUiState.Error(res.message)
                else -> SearchUiState.Success
            }
        }
    }

    private fun cancelPreviousSearch() {
        searchJob?.cancel()
        _uiState.value = SearchUiState.Success
    }

    fun bookmark(character: CharacterModel) {
        viewModelScope.launch {
            if (character.favorite) favoriteCharacterRepository.delete(characterId = character.id)
            else favoriteCharacterRepository.insert(character.toEntity())
            fetchFavoriteCharacters()
        }
    }

    fun updateKeyword(newKeyword: String) {
        _keyword.value = newKeyword
    }

    private fun updateFavorite() {
        if (uiState.value !is SearchUiState.Success) return

        _uiState.value = SearchUiState.Loading
        _uiState.value = SearchUiState.Success
        _characters.value = characters.value.map {
            val originFavorite = it.favorite
            val curFavorite = favoriteIds.contains(it.id)

            if (originFavorite == curFavorite) it
            else it.copy(favorite = curFavorite)
        }
    }
}