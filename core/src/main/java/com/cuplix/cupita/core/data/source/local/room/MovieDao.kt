package com.cuplix.cupita.core.data.source.local.room

import androidx.room.*
import com.cuplix.cupita.core.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM tb_movie")
    fun getMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM tb_movie WHERE isFavorite = 0 AND title LIKE '%' || :search || '%'")
    fun getSearchMovies(search: String): Flow<List<MovieEntity>>

    @Query("SELECT * FROM tb_movie WHERE isFavorite = 1")
    fun getFavoriteMovies(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie :List<MovieEntity>)

    @Update
    fun updateFavoriteMovie(movie: MovieEntity)

}
