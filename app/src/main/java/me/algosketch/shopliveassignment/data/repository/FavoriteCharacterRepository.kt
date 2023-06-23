package me.algosketch.shopliveassignment.data.repository

import me.algosketch.shopliveassignment.data.source.local.FavoriteCharacter
import me.algosketch.shopliveassignment.data.source.local.FavoriteCharacterLocalDataSource
import javax.inject.Inject

class FavoriteCharacterRepository @Inject constructor(
    private val favoriteDataSource: FavoriteCharacterLocalDataSource
) {
    suspend fun getFavoriteCharacters(): List<FavoriteCharacter> =
        favoriteDataSource.findAll()

    suspend fun insert(character: FavoriteCharacter) {
        favoriteDataSource.insert(character)
    }

    suspend fun delete(characterId: Int) {
        favoriteDataSource.deleteByCharacterId(characterId)
    }
}