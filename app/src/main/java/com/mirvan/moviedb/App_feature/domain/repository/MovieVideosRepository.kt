package com.mirvan.moviedb.App_feature.domain.repository

import com.example.moviedb.Core.util.Resource
import com.mirvan.moviedb.App_feature.domain.model.MoviesVideos
import kotlinx.coroutines.flow.Flow

interface MovieVideosRepository {
    fun getMovieVideos(
        movie_id: Int
    ): Flow<Resource<MoviesVideos>>
}
