package com.mirvan.moviedb.App_feature.presentation.genre

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mirvan.moviedb.App_feature.domain.repository.GetGenresRepository
import com.mirvan.moviedb.Core.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenresViewModel @Inject constructor(
    private val genresRepository: GetGenresRepository
) : ViewModel() {

    var genresJob: Job? = null

    private val _genresState = mutableStateOf(GenreState())
    val genresState: State<GenreState> = _genresState

    init {
        getGenres()
    }

    private fun getGenres() {
        _genresState.value = genresState.value.copy(
            genreData = null,
            isLoading = true
        )
        genresJob?.cancel()
        genresJob = viewModelScope.launch {
            genresRepository.getGenres(language = "en").onEach { result ->
                val resultData = result.data?.toGenreState()
                val resultMessage = result.message
                when (result) {
                    is Resource.Success -> {
                        _genresState.value = genresState.value.copy(
                            genreData = resultData?.genreData,
                            isLoading = false,
                            message = "Genres successfully loaded"
                        )
                    }
                    is Resource.Error -> {
                        _genresState.value = genresState.value.copy(
                            genreData = null,
                            isLoading = false,
                            message = resultMessage
                        )
                    }
                    is Resource.Loading -> {
                        _genresState.value = genresState.value.copy(
                            genreData = null,
                            isLoading = true
                        )
                    }
                }
            }.launchIn(this)
        }
    }
}
