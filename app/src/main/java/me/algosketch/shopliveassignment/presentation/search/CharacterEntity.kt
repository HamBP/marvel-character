package me.algosketch.shopliveassignment.presentation.search

import me.algosketch.shopliveassignment.data.source.local.FavoriteCharacter
import me.algosketch.shopliveassignment.data.source.remote.Character

data class CharacterEntity(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnailUrl: String,
    val favorite: Boolean = false,
) {
    fun toFavoriteCharacter(): FavoriteCharacter {
        return FavoriteCharacter(
            characterId = id,
            name = name,
            description = description,
            thumbnailUrl = thumbnailUrl,
        )
    }
}

fun Character.toEntity(): CharacterEntity {
    val imageSize = "standard_xlarge"
    val prefixUrl = thumbnail.path.substring(4)

    return CharacterEntity(
        id = id,
        name = name,
        description = description,
        thumbnailUrl = "https${prefixUrl}/$imageSize.${thumbnail.extension}",
    )
}