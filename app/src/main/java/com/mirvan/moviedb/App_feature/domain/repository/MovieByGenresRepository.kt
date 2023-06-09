package com.mirvan.moviedb.App_feature.domain.repository

import com.mirvan.moviedb.App_feature.domain.model.MovieByGenre
import com.mirvan.moviedb.Core.util.Resource
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
