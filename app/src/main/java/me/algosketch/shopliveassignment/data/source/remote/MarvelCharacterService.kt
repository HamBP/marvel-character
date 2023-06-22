package me.algosketch.shopliveassignment.data.source.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelCharacterService {

    @GET("public/characters")
    suspend fun getCharacters(
        @Query("nameStartsWith")name: String,
        @Query("limit")size: Int = 10,
        @Query("offset")offset: Int = 0,
    ): CharacterDataWrapper
}