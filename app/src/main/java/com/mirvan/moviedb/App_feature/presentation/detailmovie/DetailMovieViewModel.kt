package com.mirvan.moviedb.App_feature.presentation.detailmovie // ktlint-disable package-name

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedb.Core.util.Resource
import com.mirvan.moviedb.App_feature.domain.model.MovieReview
import com.mirvan.moviedb.App_feature.domain.repository.DetailMovieRepository
import com.mirvan.moviedb.App_feature.domain.repository.MovieReviewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(
    private val detailMovieRepository: DetailMovieRepository,
    private val movieReviewsRepository: MovieReviewsRepository
) : ViewModel() {

    var detailMoveJob: Job? = null

    private val _movieIdState = mutableStateOf(0)

    private val _detailMovieState = mutableStateOf(DetailMovieState())
    val detailMovieState: State<DetailMovieState> = _detailMovieState

    private val _movieReviewsState = mutableStateOf(MovieReviewState())
    val movieReviewsState: State<MovieReviewState> = _movieReviewsState

    private var _reviewResultData: MutableList<MovieReview.Result> = mutableListOf()
    private lateinit var _reviewData: MutableState<MovieReview>
    init {
        viewModelScope.launch {
            _detailMovieState.value = detailMovieState.value.copy(
                movieDetail = null,
                isLoading = true
            )
            delay(1500)
            if (_movieIdState.value != 0) {
                getGenres(movieId = _movieIdState.value)
            }
        }
    }

    fun setMovieId(movieId: Int): Int {
        _movieIdState.value = movieId
        return _movieIdState.value
    }

    fun getReviews(movieId: Int?, page: String) {
        _movieReviewsState.value = movieReviewsState.value.copy(
            movieReview = null,
            isLoading = true
        )
        detailMoveJob?.cancel()
        detailMoveJob = viewModelScope.launch {
            if (movieId != null) {
                movieReviewsRepository.getMovieReview(page = page, movieId = movieId)
                    .onEach { result ->
                        val resultData = result.data?.toMovieReviewState()
                        val resultMessage = result.message
                        if (resultData?.movieReview != null) {
                            _reviewResultData.addAll(result.data.results as List)

                            _reviewData = mutableStateOf(
                                MovieReview(
                                    id = resultData.movieReview.id,
                                    page = resultData.movieReview.page,
                                    results = _reviewResultData,
                                    total_pages = resultData.movieReview.total_pages,
                                    total_results = resultData.movieReview.total_results
                                )
                            )
                            Log.e("TAG", "getReviews-_reviewData: $_reviewData")
                            Log.e("TAG", "getReviews-_reviewData.results?.size: ${_reviewData.value.results?.size}")
                            when (result) {
                                is Resource.Success -> {
                                    _movieReviewsState.value = movieReviewsState.value.copy(
                                        movieReview = _reviewData.value,
                                        isLoading = false,
                                        message = "Reviews successfully loaded"
                                    )
                                }
                                is Resource.Error -> {
                                    _movieReviewsState.value = movieReviewsState.value.copy(
                                        movieReview = null,
                                        isLoading = false,
                                        message = resultMessage
                                    )
                                }
                                is Resource.Loading -> {
                                    _movieReviewsState.value = movieReviewsState.value.copy(
                                        movieReview = null,
                                        isLoading = true
                                    )
                                }
                            }
                        }
                    }.launchIn(this)
            }
        }
    }

    fun getGenres(movieId: Int) {
        _detailMovieState.value = detailMovieState.value.copy(
            movieDetail = null,
            isLoading = true
        )
        detailMoveJob?.cancel()
        detailMoveJob = viewModelScope.launch {
            detailMovieRepository.getDetailMovie(language = "en", movieId = movieId)
                .onEach { result ->
                    val resultData = result.data?.toMovieDetailState()
                    val resultMessage = result.message
                    when (result) {
                        is Resource.Success -> {
                            _detailMovieState.value = detailMovieState.value.copy(
                                movieDetail = resultData?.movieDetail,
                                isLoading = false,
                                message = "Detail movie successfully loaded"
                            )
                        }
                        is Resource.Error -> {
                            _detailMovieState.value = detailMovieState.value.copy(
                                movieDetail = null,
                                isLoading = false,
                                message = resultMessage
                            )
                        }
                        is Resource.Loading -> {
                            _detailMovieState.value = detailMovieState.value.copy(
                                movieDetail = null,
                                isLoading = true
                            )
                        }
                    }
                }.launchIn(this)
        }
    }
}
