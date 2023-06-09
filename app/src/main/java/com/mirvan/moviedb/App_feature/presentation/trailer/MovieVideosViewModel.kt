package com.mirvan.moviedb.App_feature.presentation.trailer

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedb.Core.util.Resource
import com.mirvan.moviedb.App_feature.domain.repository.MovieVideosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.* // ktlint-disable no-wildcard-imports
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MovieVideosViewModel @Inject constructor(
    private val movieVideosRepository: MovieVideosRepository
) : ViewModel() {

    var movieByVideosJob: Job? = null

    private val _movieVideosState = mutableStateOf(MovieVideosState())
    val movieVideosState: State<MovieVideosState> = _movieVideosState

    private val _movieId = mutableStateOf(0)

    init {
        viewModelScope.launch {
            _movieVideosState.value = movieVideosState.value.copy(
                moviesVideos = null,
                isLoading = true
            )
            delay(1500)
            if (_movieId.value != 0) {
                getMovieVideos(movieId = _movieId.value)
            }
        }
    }

    fun setMovieId(movieId: Int): Int {
        _movieId.value = movieId
        return _movieId.value
    }

    fun getMovieVideos(
        movieId: Int
    ) {
        movieByVideosJob?.cancel()
        movieByVideosJob = viewModelScope.launch {
            movieVideosRepository.getMovieVideos(
                movie_id = movieId
            ).onEach { result ->
                val resultData = result.data?.toMoviesVideosState()
                val resultMessage = result.message
                when (result) {
                    is Resource.Success -> {
                        _movieVideosState.value = movieVideosState.value.copy(
                            moviesVideos = resultData?.moviesVideos,
                            isLoading = false,
                            message = "Genres successfully loaded"
                        )
                    }
                    is Resource.Error -> {
                        _movieVideosState.value = movieVideosState.value.copy(
                            moviesVideos = null,
                            isLoading = false,
                            message = resultMessage
                        )
                    }
                    is Resource.Loading -> {
                        _movieVideosState.value = movieVideosState.value.copy(
                            moviesVideos = null,
                            isLoading = true
                        )
                    }
                }
            }.launchIn(this)
        }
    }
}
