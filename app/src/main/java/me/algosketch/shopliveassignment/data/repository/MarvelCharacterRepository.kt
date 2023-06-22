package me.algosketch.shopliveassignment.data.repository

import me.algosketch.shopliveassignment.data.source.remote.MarvelCharacterRemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MarvelCharacterRepository @Inject constructor(
    private val source: MarvelCharacterRemoteDataSource
) {
    suspend fun getMarvelCharacters() {
        source.getCharacters()
    }
}