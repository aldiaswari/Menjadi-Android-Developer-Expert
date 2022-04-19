package com.cuplix.cupita.core.data.source.remote


import com.cuplix.cupita.core.BuildConfig
import com.cuplix.cupita.core.data.source.remote.network.ApiService
import com.cuplix.cupita.core.data.source.remote.response.MovieResponse
import com.cuplix.cupita.core.data.source.remote.vo.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {


    suspend fun getMovies(): Flow<ApiResponse<List<MovieResponse>>> {
        return flow {
            try {
                val response = apiService.getMovies(BuildConfig.API_KEY)
                val movieList = response.results
                if (movieList.isNotEmpty()) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}