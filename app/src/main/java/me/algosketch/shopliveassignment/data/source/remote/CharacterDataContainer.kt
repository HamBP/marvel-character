package me.algosketch.shopliveassignment.data.source.remote

data class CharacterDataContainer(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: List<Character>,
)
