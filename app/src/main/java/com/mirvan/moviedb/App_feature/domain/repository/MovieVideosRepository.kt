package com.mirvan.moviedb.App_feature.domain.repository

import com.mirvan.moviedb.App_feature.domain.model.MoviesVideos
import com.mirvan.moviedb.Core.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieVideosRepository {
    fun getMovieVideos(
        movie_id: Int
    ): Flow<Resource<MoviesVideos>>
}
