package me.algosketch.shopliveassignment.data.source.remote

data class CharacterDataWrapper(
    val code: Int,
    val status: String,
    val data: CharacterDataContainer,
)
