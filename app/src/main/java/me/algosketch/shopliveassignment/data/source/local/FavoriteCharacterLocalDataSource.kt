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
    private var dirty: Boolean = false

    suspend fun findAll(order: String): List<FavoriteCharacter> = withContext(ioDispatcher) {
        requireCachedDate()

        cachedData!!.sortedBy {
            if(order.uppercase() == "ASC") it.id else -it.id
        }
    }

    suspend fun insert(character: FavoriteCharacter) = withContext(ioDispatcher) {
        requireCachedDate()

        if(cachedData!!.size >= 5) deleteOldest()

        dao.insert(character)
        dirty = true
    }

    suspend fun deleteByCharacterId(id: Int) = withContext(ioDispatcher) {
        dao.deleteByCharacterId(id)
        dirty = true
    }

    private fun deleteOldest() {
        val target = cachedData!!.minBy { it.id }
        dao.delete(target)
        dirty = true
    }

    private fun requireCachedDate() {
        if(cachedData == null || dirty) cachedData = dao.getAll()

        dirty = false
    }
}