package com.mirvan.moviedb.App_feature.presentation.detailmovie // ktlint-disable package-name

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedb.Core.util.Resource
import com.mirvan.moviedb.App_feature.domain.repository.DetailMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(
    private val detailMovieRepository: DetailMovieRepository
) : ViewModel() {

    var detailMoveJob: Job? = null

    private val _movieIdState = mutableStateOf(0)

    private val _detailMovieState = mutableStateOf(DetailMovieState())
    val detailMovieState: State<DetailMovieState> = _detailMovieState

    init {
        _detailMovieState.value = detailMovieState.value.copy(
            movieDetail = null,
            isLoading = true
        )
        viewModelScope.launch {
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

    private fun getGenres(movieId: Int) {
//        _detailMovieState.value = detailMovieState.value.copy(
//            movieDetail = null,
//            isLoading = true
//        )
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
