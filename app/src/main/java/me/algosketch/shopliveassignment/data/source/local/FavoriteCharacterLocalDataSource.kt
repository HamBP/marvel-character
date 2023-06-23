package me.algosketch.shopliveassignment.data.source.local

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import me.algosketch.shopliveassignment.di.IoDispatcher
import javax.inject.Inject

class FavoriteCharacterLocalDataSource @Inject constructor(
    private val dao: FavoriteCharacterDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    private var cachedData: List<FavoriteCharacter>? = null

    suspend fun findAll(): List<FavoriteCharacter> = withContext(ioDispatcher) {
        requireCachedDate()

        cachedData!!
    }

    suspend fun insert(character: FavoriteCharacter) = withContext(ioDispatcher) {
        requireCachedDate()

        if(cachedData!!.size >= 5) deleteOldest()

        dao.insert(character)
    }

    suspend fun deleteByCharacterId(id: Int) = withContext(ioDispatcher) {
        dao.deleteByCharacterId(id)
    }

    private fun deleteOldest() {
        val target = cachedData!!.minBy { it.id }
        dao.delete(target)
    }

    private fun requireCachedDate() {
        if(cachedData == null) cachedData
        cachedData = dao.getAll()
    }
}