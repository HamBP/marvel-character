package me.algosketch.shopliveassignment.data.repository

import me.algosketch.shopliveassignment.data.source.ApiResponse
import me.algosketch.shopliveassignment.data.source.remote.CharacterDataWrapper
import me.algosketch.shopliveassignment.data.source.remote.MarvelCharacterRemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MarvelCharacterRepository @Inject constructor(
    private val source: MarvelCharacterRemoteDataSource
) {
    suspend fun getMarvelCharacters(keyword: String): ApiResponse<CharacterDataWrapper> {
        return source.load(keyword)
    }
}