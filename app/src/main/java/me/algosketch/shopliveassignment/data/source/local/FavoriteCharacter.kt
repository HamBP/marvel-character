package me.algosketch.shopliveassignment.data.source.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class FavoriteCharacter(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "characterId") val characterId: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "thumbnailUrl") val thumbnailUrl: String,
)