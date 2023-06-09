package com.mirvan.moviedb.App_feature.domain.repository

import com.example.moviedb.Core.util.Resource
import com.mirvan.moviedb.App_feature.domain.model.MovieReview
import kotlinx.coroutines.flow.Flow

interface MovieReviewsRepository {
    fun getMovieReview(
        movieId: Int,
        page: String
    ): Flow<Resource<MovieReview>>
}
