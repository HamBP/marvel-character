package me.algosketch.shopliveassignment.data.source.local

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import me.algosketch.shopliveassignment.di.IoDispatcher
import javax.inject.Inject

class FavoriteCharacterLocalDataSource @Inject constructor(
    private val dao: FavoriteCharacterDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    suspend fun findAll(order: SortType = SortType.NEWEST): List<CharacterEntity> = withContext(ioDispatcher) {

        dao.getAll().sortedBy {
            if(order == SortType.OLDEST) it.id else -it.id
        }
    }

    suspend fun insert(character: CharacterEntity) = withContext(ioDispatcher) {
        val oldList = findAll()
        if(oldList.size >= 5) deleteOldest()

        dao.insert(character)
    }

    suspend fun deleteByCharacterId(id: Int) = withContext(ioDispatcher) {
        dao.deleteByCharacterId(id)
    }

    private suspend fun deleteOldest() = withContext(ioDispatcher) {
        val oldList = findAll()
        val target = oldList.minBy { it.id }

        dao.delete(target)
    }
}

enum class SortType {
    OLDEST,
    NEWEST,
}