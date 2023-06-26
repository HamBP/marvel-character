package me.algosketch.shopliveassignment.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteCharacterDao {

    @Query("SELECT * FROM favorite")
    fun getAll(): List<CharacterEntity>

    @Insert
    fun insert(character: CharacterEntity)

    @Delete
    fun delete(character: CharacterEntity)

    @Query("DELETE FROM favorite WHERE characterId = :id")
    fun deleteByCharacterId(id: Int)

    @Query("DELETE FROM favorite")
    fun clear()
}