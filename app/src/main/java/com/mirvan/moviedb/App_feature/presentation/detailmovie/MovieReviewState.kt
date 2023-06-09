package com.mirvan.moviedb.App_feature.presentation.detailmovie

import com.mirvan.moviedb.App_feature.domain.model.MovieReview

data class MovieReviewState(
    val movieReview: MovieReview? = null,
    val isLoading: Boolean = false,
    val message: String? = ""
)
