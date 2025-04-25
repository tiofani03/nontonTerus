package id.tiooooo.nontonterus.movie.implementation

import app.cash.turbine.test
import com.google.gson.annotations.SerializedName
import id.tiooooo.nontonterus.core.local.dao.SearchHistoryDao
import id.tiooooo.nontonterus.core.network.data.PagingResponse
import id.tiooooo.nontonterus.core.network.data.States
import id.tiooooo.nontonterus.movie.implementation.remote.api.MovieApi
import id.tiooooo.nontonterus.movie.implementation.remote.response.genre.GenreListResponse
import id.tiooooo.nontonterus.movie.implementation.remote.response.genre.GenreResponse
import id.tiooooo.nontonterus.movie.implementation.remote.response.list.MovieResultResponse
import id.tiooooo.nontonterus.movie.implementation.repository.MovieRepositoryImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class MovieRepositoryImplTest {
    private lateinit var movieApi: MovieApi
    private lateinit var searchHistoryDao: SearchHistoryDao
    private lateinit var repository: MovieRepositoryImpl


    @Before
    fun setup() {
        movieApi = mockk()
        searchHistoryDao = mockk(relaxed = true)
        repository = MovieRepositoryImpl(movieApi, searchHistoryDao, Dispatchers.Unconfined)
    }

    @Test
    fun `getGenres emits Loading then Success`() = runTest {
        val mockGenreList = GenreListResponse(listOf(GenreResponse(1, "Action")))
        coEvery { movieApi.getGenres() } returns mockGenreList

        repository.getGenres().test {
            assertEquals(States.Loading, awaitItem())
            val result = awaitItem()
            assertTrue(result is States.Success)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getMovieByType emits Loading then Success`() = runTest {
        val mockMovieList = PagingResponse(
            page = 1,
            currentPage = 1,
            nextPage = 2,
            perPage = 10,
            previousPage = 1,
            total = 10,
            data = listOf(dummyMovieResultResponse)
        )

        coEvery { movieApi.getMovies("popular") } returns mockMovieList

        repository.getMovieByType("popular").test {
            assertEquals(States.Loading, awaitItem())

            val result = awaitItem()
            assertTrue(result is States.Success)

            val data = (result as States.Success).data
            assertEquals(1, data.size)
            assertEquals(dummyMovieResultResponse.id, data.first().id)
            cancelAndIgnoreRemainingEvents()
        }
    }

}

val dummyMovieResultResponse = MovieResultResponse(
    adult = false,
    backdropPath = "/backdrop_sample.jpg",
    id = 123456L,
    originalLanguage = "en",
    originalTitle = "Original Movie Title",
    overview = "This is a sample overview for the dummy movie.",
    popularity = 123.45,
    posterPath = "/poster_sample.jpg",
    releaseDate = "2023-09-01",
    title = "Dummy Movie Title",
    video = false,
    voteAverage = 8.5,
    voteCount = 1200
)

