package com.mirvan.moviedb.App_feature.domain.repository

import com.example.moviedb.Core.util.Resource
import com.mirvan.moviedb.App_feature.domain.model.MovieDetail
import kotlinx.coroutines.flow.Flow

interface DetailMovieRepository {
    fun getDetailMovie(
        movieId: Int,
        language: String
    ): Flow<Resource<MovieDetail>>
}
