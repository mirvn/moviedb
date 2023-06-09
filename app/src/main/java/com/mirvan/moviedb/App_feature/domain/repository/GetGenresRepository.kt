package com.mirvan.moviedb.App_feature.domain.repository

import com.mirvan.moviedb.App_feature.domain.model.Genres
import com.mirvan.moviedb.Core.util.Resource
import kotlinx.coroutines.flow.Flow

interface GetGenresRepository {
    fun getGenres(
        language: String
    ): Flow<Resource<Genres>>
}
