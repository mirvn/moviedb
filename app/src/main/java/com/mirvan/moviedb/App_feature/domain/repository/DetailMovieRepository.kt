package com.mirvan.moviedb.App_feature.domain.repository

import com.mirvan.moviedb.App_feature.domain.model.MovieDetail
import com.mirvan.moviedb.Core.util.Resource
import kotlinx.coroutines.flow.Flow

interface DetailMovieRepository {
    fun getDetailMovie(
        movieId: Int,
        language: String
    ): Flow<Resource<MovieDetail>>
}
