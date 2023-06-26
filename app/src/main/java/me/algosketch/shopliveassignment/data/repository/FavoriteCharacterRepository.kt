package me.algosketch.shopliveassignment.data.repository

import me.algosketch.shopliveassignment.data.source.local.CharacterEntity
import me.algosketch.shopliveassignment.data.source.local.FavoriteCharacterLocalDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteCharacterRepository @Inject constructor(
    private val favoriteDataSource: FavoriteCharacterLocalDataSource
) {
    /**
     * @param order 정렬 기준 DESC, ASC
     */
    suspend fun getFavoriteCharacters(
        order: String = "DESC",
    ): List<CharacterEntity> =
        favoriteDataSource.findAll(order)

    suspend fun insert(character: CharacterEntity) {
        favoriteDataSource.insert(character)
    }

    suspend fun delete(characterId: Int) {
        favoriteDataSource.deleteByCharacterId(characterId)
    }
}