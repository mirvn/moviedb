package com.mirvan.moviedb.App_feature.presentation.moviebygenre

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedb.Core.util.Resource
import com.mirvan.moviedb.App_feature.domain.model.MovieByGenre
import com.mirvan.moviedb.App_feature.domain.repository.MovieByGenresRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieByGenresViewModel @Inject constructor(
    private val movieByGenresRepository: MovieByGenresRepository
) : ViewModel() {

    var movieByGenresJob: Job? = null

    private val _movieByGenresState = mutableStateOf(MovieByGenreState())
    val movieByGenresState: State<MovieByGenreState> = _movieByGenresState

    private val _pageState = mutableStateOf("1")
    private lateinit var _movieByGenre: MutableState<MovieByGenre>
    private var _movieByGenreResult: MutableList<MovieByGenre.Result> = mutableListOf()

    init {
        _movieByGenresState.value = movieByGenresState.value.copy(
            movieByGenre = null,
            isLoading = true
        )
    }

    fun setPage(page: String) {
        _pageState.value = page
    }

    fun getMovieByGenres(
        language: String,
        sort_by: String,
        include_video: String,
        page: String,
        with_genres: String
    ) {
        movieByGenresJob?.cancel()
        movieByGenresJob = viewModelScope.launch {
            movieByGenresRepository.getMovieByGenres(
                language = language,
                sort_by = sort_by,
                include_video = include_video,
                page = page,
                with_genres = with_genres
            ).onEach { result ->
                val resultData = result.data?.toMovieByGenreState()
                val resultMessage = result.message
                if (resultData?.movieByGenre != null) {
                    _movieByGenreResult.addAll(result.data.results)
                    _movieByGenre = mutableStateOf(
                        value = MovieByGenre(
                            page = resultData.movieByGenre.page,
                            results = _movieByGenreResult,
                            total_pages = resultData.movieByGenre.total_pages,
                            total_results = resultData.movieByGenre.total_results
                        )
                    )
                    when (result) {
                        is Resource.Success -> {
                            _movieByGenresState.value = movieByGenresState.value.copy(
                                movieByGenre = _movieByGenre.value,
                                isLoading = false,
                                message = "Genres successfully loaded"
                            )
                        }
                        is Resource.Error -> {
                            _movieByGenresState.value = movieByGenresState.value.copy(
                                movieByGenre = null,
                                isLoading = false,
                                message = resultMessage
                            )
                        }
                        is Resource.Loading -> {
                            _movieByGenresState.value = movieByGenresState.value.copy(
                                movieByGenre = null,
                                isLoading = true
                            )
                        }
                    }
                }
            }.launchIn(this)
        }
    }
}
