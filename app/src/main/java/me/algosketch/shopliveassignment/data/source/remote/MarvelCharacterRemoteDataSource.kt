package me.algosketch.shopliveassignment.data.source.remote

import me.algosketch.shopliveassignment.data.source.ApiResponse
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MarvelCharacterRemoteDataSource @Inject constructor(
    private val service: MarvelCharacterService
) {
    suspend fun load(keyword: String, size: Int, page: Int) : ApiResponse<CharacterDataWrapper> {
        val offset = size * (page - 1)

        val res = service.getCharacters(keyword, size = size, offset = offset)

        return try {
            ApiResponse.Success(
                data = res
            )
        } catch (e: HttpException) {
            ApiResponse.Error()
        } catch (e: Throwable) {
            ApiResponse.Error()
        }
    }
}