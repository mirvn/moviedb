package com.mirvan.moviedb.App_feature.domain.repository

import com.example.moviedb.Core.util.Resource
import com.mirvan.moviedb.App_feature.domain.model.MovieByGenre
import kotlinx.coroutines.flow.Flow

interface MovieByGenresRepository {
    fun getMovieByGenres(
        language: String,
        sort_by: String,
        include_video: String,
        page: String,
        with_genres: String
    ): Flow<Resource<MovieByGenre>>
}
