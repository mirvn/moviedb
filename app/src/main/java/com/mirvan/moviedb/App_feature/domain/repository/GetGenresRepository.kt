package com.mirvan.moviedb.App_feature.domain.repository

import com.example.moviedb.Core.util.Resource
import com.mirvan.moviedb.App_feature.domain.model.Genres
import kotlinx.coroutines.flow.Flow

interface GetGenresRepository {
    fun getGenres(
        language: String
    ): Flow<Resource<Genres>>
}