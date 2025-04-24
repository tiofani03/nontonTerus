package id.tiooooo.nontonterus.pages.review

import androidx.paging.cachedIn
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import id.tiooooo.nontonterus.movie.api.repository.MovieRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest

class ReviewScreenModel(
    private val movieRepository: MovieRepository,
) : ScreenModel {
    private val _movieId: MutableStateFlow<Long> = MutableStateFlow(0L)

    fun setMovieId(id: Long) {
        _movieId.value = id
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val movieReview = _movieId.flatMapLatest { id ->
        movieRepository.getAllMovieReviews(id.toString())
    }.cachedIn(screenModelScope)
}