package com.mirvan.moviedb.App_feature.data.remote.dto

import com.mirvan.moviedb.App_feature.domain.model.MovieByGenre

data class MovieByGenreDto(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
) {
    data class Result(
        val adult: Boolean?,
        val backdrop_path: String?,
        val genre_ids: List<Int>?,
        val id: Int?,
        val original_language: String?,
        val original_title: String?,
        val overview: String?,
        val popularity: Double?,
        val poster_path: String?,
        val release_date: String?,
        val title: String?,
        val video: Boolean?,
        val vote_average: Double?,
        val vote_count: Int?
    )

    fun toMovieByGenre(): MovieByGenre {
        val listResult = mutableListOf<MovieByGenre.Result>()
        results.map {
            listResult.add(
                MovieByGenre.Result(
                    adult = it.adult,
                    backdrop_path = it.backdrop_path,
                    genre_ids = it.genre_ids,
                    id = it.id,
                    original_language = it.original_language,
                    original_title = it.original_title,
                    overview = it.overview,
                    popularity = it.popularity,
                    poster_path = it.poster_path,
                    release_date = it.release_date,
                    title = it.title,
                    video = it.video,
                    vote_average = it.vote_average,
                    vote_count = it.vote_count
                )
            )
        }
        return MovieByGenre(
            page = page,
            results = listResult,
            total_pages = total_pages,
            total_results = total_results
        )
    }
}
