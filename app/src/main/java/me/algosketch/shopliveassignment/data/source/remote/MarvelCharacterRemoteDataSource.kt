package me.algosketch.shopliveassignment.data.source.remote

import me.algosketch.shopliveassignment.data.source.ApiResponse
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MarvelCharacterRemoteDataSource @Inject constructor(
    private val service: MarvelCharacterService
) {
    private val size = 10
    private var page = 1
    private val offset: Int
        get() = size * (page - 1)

    // todo : page 증가
    suspend fun load(keyword: String) : ApiResponse<CharacterDataWrapper> {
        val res = service.getCharacters(keyword, size = 10, offset = offset)

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