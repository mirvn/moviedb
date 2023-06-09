package com.mirvan.moviedb.App_feature.data.remote.dto

import com.mirvan.moviedb.App_feature.domain.model.MoviesVideos

data class MoviesVideosDto(
    val id: Int,
    val results: List<Result>
) {
    data class Result(
        val id: String?,
        val iso_3166_1: String?,
        val iso_639_1: String?,
        val key: String?,
        val name: String?,
        val official: Boolean?,
        val published_at: String?,
        val site: String?,
        val size: Int?,
        val type: String?
    )

    fun toMoviesVideos(): MoviesVideos {
        val videosResult = mutableListOf<MoviesVideos.Result>()
        results.map {
            videosResult.add(
                MoviesVideos.Result(
                    id = it.id,
                    iso_639_1 = it.iso_639_1,
                    iso_3166_1 = it.iso_3166_1,
                    key = it.key,
                    name = it.name,
                    official = it.official,
                    published_at = it.published_at,
                    site = it.site,
                    size = it.size,
                    type = it.type
                )
            )
        }
        return MoviesVideos(
            id = id,
            results = videosResult
        )
    }
}