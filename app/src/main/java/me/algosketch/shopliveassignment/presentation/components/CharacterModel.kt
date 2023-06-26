package me.algosketch.shopliveassignment.presentation.components

import me.algosketch.shopliveassignment.data.source.local.CharacterEntity
import me.algosketch.shopliveassignment.data.source.remote.Character

data class CharacterModel(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnailUrl: String,
    val favorite: Boolean = false,
) {
    fun toEntity(): CharacterEntity {
        return CharacterEntity(
            characterId = id,
            name = name,
            description = description,
            thumbnailUrl = thumbnailUrl,
        )
    }
}

fun Character.toModel(
    favorite: Boolean,
): CharacterModel {
    val imageSize = "standard_xlarge"
    val prefixUrl = thumbnail.path.substring(4)

    return CharacterModel(
        id = id,
        name = name,
        description = description,
        thumbnailUrl = "https${prefixUrl}/$imageSize.${thumbnail.extension}",
        favorite = favorite,
    )
}

fun CharacterEntity.toModel(): CharacterModel {
    return CharacterModel(
        id = characterId,
        name = name,
        description = description,
        thumbnailUrl = thumbnailUrl,
        favorite = true,
    )
}