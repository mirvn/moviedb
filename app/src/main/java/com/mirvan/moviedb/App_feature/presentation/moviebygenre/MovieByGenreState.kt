package com.mirvan.moviedb.App_feature.presentation.moviebygenre

import com.mirvan.moviedb.App_feature.domain.model.MovieByGenre

data class MovieByGenreState(
    val movieByGenre: MovieByGenre? = null,
    val isLoading: Boolean = false,
    val message: String? = ""
)
