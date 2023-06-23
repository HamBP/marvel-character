package me.algosketch.shopliveassignment.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteCharacterDao {

    @Query("SELECT * FROM favorite")
    fun getAll(): List<FavoriteCharacter>

    @Insert
    fun insert(character: FavoriteCharacter)

    @Delete
    fun delete(character: FavoriteCharacter)

    @Query("DELETE FROM favorite WHERE characterId = :id")
    fun deleteByCharacterId(id: Int)

    @Query("DELETE FROM favorite")
    fun clear()
}