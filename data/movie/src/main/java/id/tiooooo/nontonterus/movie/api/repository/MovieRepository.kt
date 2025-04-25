package id.tiooooo.nontonterus.movie.api.repository

import androidx.paging.PagingData
import id.tiooooo.nontonterus.core.local.entity.SearchHistoryEntity
import id.tiooooo.nontonterus.core.network.data.States
import id.tiooooo.nontonterus.movie.api.model.detail.MovieDetail
import id.tiooooo.nontonterus.movie.api.model.list.GenreList
import id.tiooooo.nontonterus.movie.api.model.list.MovieResult
import id.tiooooo.nontonterus.movie.api.model.review.MovieReview
import id.tiooooo.nontonterus.movie.api.model.video.MovieVideo
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getGenres(): Flow<States<GenreList>>

    suspend fun getMovieByType(type: String): Flow<States<List<MovieResult>>>

    fun getAllMovieByType(type: String): Flow<PagingData<MovieResult>>

    suspend fun getDiscoverMovie(genreId: String): Flow<PagingData<MovieResult>>

    suspend fun getDetailMovie(movieId: String): Flow<States<MovieDetail>>

    suspend fun getMovieReviews(movieId: String): Flow<States<List<MovieReview>>>

    suspend fun getAllMovieReviews(movieId: String): Flow<PagingData<MovieReview>>

    suspend fun getMovieVideos(movieId: String): Flow<States<List<MovieVideo>>>

    suspend fun getMovieByQuery(query: String): Flow<PagingData<MovieResult>>

    fun getSearchHistory(): Flow<List<SearchHistoryEntity>>
    suspend fun insertSearchHistory(search: SearchHistoryEntity)
    suspend fun deleteSearchHistory(search: SearchHistoryEntity)
    suspend fun updateSearchHistory(search: SearchHistoryEntity)
}
