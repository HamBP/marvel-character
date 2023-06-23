package me.algosketch.shopliveassignment.data.source

sealed class ApiResponse<T>(
    val data: T? = null,
    val message: String = "",
) {
    class Success<T>(data: T) : ApiResponse<T>(data = data)
    class Error<T>(data: T? = null) : ApiResponse<T>(data = data)
}
