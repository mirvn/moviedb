package com.mirvan.moviedb.App_feature.presentation.trailer

import com.mirvan.moviedb.App_feature.domain.model.MoviesVideos

data class MovieVideosState(
    val moviesVideos: MoviesVideos? = null,
    val isLoading: Boolean = false,
    val message: String? = ""
)
