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
    private val size: Int = 10
    private var page: Int = 1
    private var total: Int = -1
    private var query: String = ""
        set(value) {
            if(query == value) return

            field = value
            page = 1
            total = -1
        }

    suspend fun getMarvelCharacters(keyword: String): ApiResponse<CharacterDataWrapper> {
        query = keyword
        val res = source.load(query, size, page)
        total = res.data?.data?.total ?: 0
        page++

        return res
    }
}