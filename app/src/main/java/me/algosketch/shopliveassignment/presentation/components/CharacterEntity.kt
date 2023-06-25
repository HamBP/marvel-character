package me.algosketch.shopliveassignment.presentation.components

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

fun Character.toEntity(
    favorite: Boolean,
): CharacterEntity {
    val imageSize = "standard_xlarge"
    val prefixUrl = thumbnail.path.substring(4)

    return CharacterEntity(
        id = id,
        name = name,
        description = description,
        thumbnailUrl = "https${prefixUrl}/$imageSize.${thumbnail.extension}",
        favorite = favorite,
    )
}

fun FavoriteCharacter.toEntity(): CharacterEntity {
    return CharacterEntity(
        id = characterId,
        name = name,
        description = description,
        thumbnailUrl = thumbnailUrl,
        favorite = true,
    )
}