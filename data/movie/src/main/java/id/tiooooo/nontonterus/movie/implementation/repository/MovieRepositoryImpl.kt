package id.tiooooo.nontonterus.movie.implementation.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import id.tiooooo.nontonterus.movie.implementation.datasource.MovieByQueryPagingSource
import id.tiooooo.nontonterus.movie.implementation.datasource.MoviePagingSource
import id.tiooooo.nontonterus.movie.implementation.datasource.MovieReviewPagingSource
import id.tiooooo.nontonterus.core.local.dao.SearchHistoryDao
import id.tiooooo.nontonterus.core.local.entity.SearchHistoryEntity
import id.tiooooo.nontonterus.core.network.data.States
import id.tiooooo.nontonterus.core.network.data.toError
import id.tiooooo.nontonterus.movie.api.model.casts.Cast
import id.tiooooo.nontonterus.movie.api.model.detail.MovieDetail
import id.tiooooo.nontonterus.movie.api.model.list.GenreList
import id.tiooooo.nontonterus.movie.api.model.list.MovieResult
import id.tiooooo.nontonterus.movie.api.model.review.MovieReview
import id.tiooooo.nontonterus.movie.api.model.video.MovieVideo
import id.tiooooo.nontonterus.movie.api.repository.MovieRepository
import id.tiooooo.nontonterus.movie.implementation.datasource.DiscoverMoviePagingSource
import id.tiooooo.nontonterus.movie.implementation.remote.api.MovieApi
import id.tiooooo.nontonterus.movie.implementation.remote.response.casts.toCast
import id.tiooooo.nontonterus.movie.implementation.remote.response.detail.toMovieDetail
import id.tiooooo.nontonterus.movie.implementation.remote.response.genre.toGenreList
import id.tiooooo.nontonterus.movie.implementation.remote.response.list.mapToMovieResult
import id.tiooooo.nontonterus.movie.implementation.remote.response.review.toMovieReview
import id.tiooooo.nontonterus.movie.implementation.remote.response.video.toMovieVideo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class MovieRepositoryImpl(
    private val movieApi: MovieApi,
    private val searchHistoryDao: SearchHistoryDao,
    private val ioDispatcher: CoroutineDispatcher,
) : MovieRepository {
    override suspend fun getGenres(): Flow<States<GenreList>> {
        return flow {
            try {
                emit(States.Loading)
                val response = movieApi.getGenres()
                emit(States.Success(data = response.toGenreList()))
            } catch (e: Exception) {
                emit(e.toError())
            }
        }.flowOn(ioDispatcher)
    }

    override suspend fun getMovieByType(type: String): Flow<States<List<MovieResult>>> {
        return flow {
            try {
                emit(States.Loading)
                val response = movieApi.getMovies(type)
                response.data?.let { list ->
                    emit(States.Success(data = list.map { it.mapToMovieResult() }))
                } ?: run {
                    emit(States.Empty)
                }
            } catch (e: Exception) {
                emit(e.toError())
            }
        }.flowOn(ioDispatcher)
    }

    override fun getAllMovieByType(type: String): Flow<PagingData<MovieResult>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 2,
                initialLoadSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MoviePagingSource(movieApi, type)
            }
        ).flow
    }

    override suspend fun getDiscoverMovie(genreId: String): Flow<PagingData<MovieResult>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20
            ),
            pagingSourceFactory = {
                DiscoverMoviePagingSource(movieApi, genreId)
            }
        ).flow
    }

    override suspend fun getDetailMovie(movieId: String): Flow<States<MovieDetail>> {
        return flow {
            try {
                emit(States.Loading)
                val response = movieApi.getDetailMovie(movieId)
                emit(States.Success(data = response.toMovieDetail()))
            } catch (e: Exception) {
                emit(e.toError())
            }
        }.flowOn(ioDispatcher)
    }

    override suspend fun getMovieReviews(movieId: String): Flow<States<List<MovieReview>>> {
        return flow {
            try {
                emit(States.Loading)
                val response = movieApi.getMovieReviews(movieId)
                response.data?.let { list ->
                    emit(States.Success(data = list.map { it.toMovieReview() }))
                } ?: run {
                    emit(States.Empty)
                }
            } catch (e: Exception) {
                emit(e.toError())
            }
        }.flowOn(ioDispatcher)
    }

    override suspend fun getAllMovieReviews(movieId: String): Flow<PagingData<MovieReview>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 2,
                initialLoadSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MovieReviewPagingSource(movieApi, movieId)
            }
        ).flow
    }

    override suspend fun getMovieCasts(movieId: String): Flow<States<List<Cast>>> {
        return flow {
            try {
                emit(States.Loading)
                val response = movieApi.getMovieCasts(movieId)
                response.cast?.let { list ->
                    emit(States.Success(data = list.map { it.toCast() }))
                } ?: run {
                    emit(States.Empty)
                }
            } catch (e: Exception) {
                emit(e.toError())
            }
        }.flowOn(ioDispatcher)
    }

    override suspend fun getMovieVideos(movieId: String): Flow<States<List<MovieVideo>>> {
        return flow {
            try {
                emit(States.Loading)
                val response = movieApi.getMovieVideo(movieId)
                response.data?.let { list ->
                    emit(States.Success(data = list.map { it.toMovieVideo() }))
                } ?: run {
                    emit(States.Empty)
                }
            } catch (e: Exception) {
                emit(e.toError())
            }
        }.flowOn(ioDispatcher)
    }

    override suspend fun getMovieByQuery(query: String): Flow<PagingData<MovieResult>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20
            ),
            pagingSourceFactory = {
                MovieByQueryPagingSource(movieApi, query)
            }
        ).flow
    }

    override fun getSearchHistory(): Flow<List<SearchHistoryEntity>> =
        searchHistoryDao.getSearchHistory()

    override suspend fun insertSearchHistory(search: SearchHistoryEntity) {
        withContext(ioDispatcher) {
            searchHistoryDao.insert(search)
        }
    }

    override suspend fun deleteSearchHistory(search: SearchHistoryEntity) {
        withContext(ioDispatcher) {
            searchHistoryDao.delete(search)
        }
    }

    override suspend fun updateSearchHistory(search: SearchHistoryEntity) {
        withContext(ioDispatcher) {
            searchHistoryDao.update(search)
        }
    }
}
