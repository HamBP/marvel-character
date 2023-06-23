package me.algosketch.shopliveassignment.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
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
    private var favorites: List<Int> = emptyList()
    private var searchJob: Job? = null

    private val searchEventFlow = keyword.debounce(300L)

    init {
        fetchFavoriteCharacters()
        collectEvents()
    }

    private fun fetchFavoriteCharacters() {
        viewModelScope.launch {
            favorites = favoriteCharacterRepository.getFavoriteCharacters().map { it.characterId }
            updateFavorite()
        }
    }

    private fun collectEvents() {
        viewModelScope.launch {
            searchEventFlow.collect {
                search()
            }
        }
    }

    fun search() {
        cancelPreviousSearch()

        if(keyword.value.length < 2) return

        searchJob = viewModelScope.launch {
            _state.value = SearchUiState.Loading

            val res = searchRepository.getMarvelCharacters(keyword.value)

            _state.value = when(res) {
                is ApiResponse.Success -> {
                    val list = res.data?.data!!.results.map { character ->
                        character.toEntity(favorite = favorites.contains(character.id))
                    }
                    SearchUiState.Success(list)
                }
                is ApiResponse.Error -> SearchUiState.Error(res.message)
                else -> SearchUiState.Success(emptyList())
            }
        }
    }

    private fun cancelPreviousSearch() {
        searchJob?.cancel()
        _state.value = SearchUiState.Success()
    }

    fun bookmark(character: CharacterEntity) {
        viewModelScope.launch {
            if(character.favorite) favoriteCharacterRepository.delete(characterId = character.id)
            else favoriteCharacterRepository.insert(character.toFavoriteCharacter())
            fetchFavoriteCharacters()
        }
    }

    fun updateKeyword(newKeyword: String) {
        _keyword.value = newKeyword
    }

    private fun updateFavorite() {
        if(state.value !is SearchUiState.Success) return

        _state.value = SearchUiState.Success(
            characters = (state.value as SearchUiState.Success).characters.map {
                val originFavorite = it.favorite
                val curFavorite = favorites.contains(it.id)

                if(originFavorite == curFavorite) it
                else it.copy(favorite = curFavorite)
            }
        )
    }
}