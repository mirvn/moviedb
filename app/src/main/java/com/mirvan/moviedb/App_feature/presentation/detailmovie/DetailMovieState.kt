package com.mirvan.moviedb.App_feature.presentation.detailmovie

import com.mirvan.moviedb.App_feature.domain.model.MovieDetail

data class DetailMovieState(
    val movieDetail: MovieDetail? = null,
    val isLoading: Boolean = false,
    val message: String? = ""
)
