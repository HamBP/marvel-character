package me.algosketch.shopliveassignment.data.source.remote

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MarvelCharacterRemoteDataSource @Inject constructor(
    private val service: MarvelCharacterService
) {
    suspend fun getCharacters() {
        service.getCharacters("iron")
    }
}