package me.algosketch.shopliveassignment.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoriteCharacter::class], version = 1)
abstract class MarvelDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteCharacterDao

    companion object {
        const val databaseName = "marvel"
    }
}