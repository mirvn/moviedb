package com.mirvan.moviedb.App_feature.presentation.genre

import com.mirvan.moviedb.App_feature.domain.model.Genres

data class GenreState(
    val genreData: Genres? = null,
    val isLoading: Boolean = false,
    val message: String? = ""
)
