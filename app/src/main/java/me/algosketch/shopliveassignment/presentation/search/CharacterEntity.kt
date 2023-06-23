package me.algosketch.shopliveassignment.presentation.search

import me.algosketch.shopliveassignment.data.source.remote.Character

data class CharacterEntity(
    val name: String,
    val description: String,
    val thumbnailUrl: String,
    val favorite: Boolean = false,
)

fun Character.toEntity(): CharacterEntity {
    val imageSize = "standard_xlarge"
    val prefixUrl = thumbnail.path.substring(4)

    return CharacterEntity(
        name = name,
        description = description,
        thumbnailUrl = "https${prefixUrl}/$imageSize.${thumbnail.extension}",
    )
}